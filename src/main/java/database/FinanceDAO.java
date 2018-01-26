package database;

import classes.Debt;
import classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FinanceDAO handles transactions between users, debt and income.
 *
 */
public class FinanceDAO {

    /**
     * Gets the debt of a user
     *
     * @param userId the id of the user to get debt for.
     * @return an {@link java.util.ArrayList} of {@link Debt} objects.
     */
    public static ArrayList<Debt> getDebt(int userId) {
        ArrayList<Debt> debts = new ArrayList<>();
        int toPerson = 0;
        double value = 0;
        User theOtherUser;
        String query = "SELECT * FROM Finance WHERE fromPerson = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    toPerson = rs.getInt("toPerson");
                    value = rs.getDouble("value");

                    theOtherUser = new User();
                    theOtherUser.setUserId(toPerson);
                    debts.add(new Debt(value, theOtherUser));

                }
            }
            return debts;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets what other people owe a given user
     *
     * @param userId the user to get income for.
     * @return an {@link java.util.ArrayList} of {@link Debt} objects.
     */
    public static ArrayList<Debt> getIncome(int userId){
        ArrayList<Debt> income = new ArrayList<>();
        int toPerson = 0;
        double value = 0;
        User theOtherUser;

        String query = "SELECT * FROM Finance WHERE toPerson = ?";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    userId = rs.getInt("fromPerson");
                    value = rs.getDouble("value");

                    theOtherUser = new User();
                    theOtherUser.setUserId(userId);
                    income.add(new Debt(value, theOtherUser));

                }
            }

            return income;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deletes a debt from the database
     *
     * @param fromUser the 'from' user on the debt.
     * @param toUser the 'to' user on the debt.
     */
    public static void deleteDebt(int fromUser, int toUser) {
        String query = "DELETE FROM Finance WHERE fromPerson = ? AND toPerson = ?;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, fromUser);
            st.setInt(2, toUser);

            st.executeUpdate();

        } catch (SQLException e) {

        }
    }

    /**
     * Updates the users debt according to what they owed each other from before.
     *
     * @param userId id of the user who buys the items
     * @param sum the total sum
     * @param contributors users that will split the sum
     * @return true or false, true if the update goes well, else false
     */
    public static boolean updateFinance(int userId, double sum, List<User> contributors) {
        if(contributors.size() == 0) {
            return false;
        }
        if(contributors.size() == 1 && contributors.get(0).getUserId() == userId) {
            return true;
        }
        List<Debt> debts = new ArrayList<>();
        String query = "SELECT * FROM Finance WHERE fromPerson = ?";
        String query2 = "SELECT * FROM Finance WHERE toPerson = ?";

        try (DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            PreparedStatement st2 = conn.prepareStatement(query2)) {

            st.setInt(1, userId);
            st2.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    double value = rs.getDouble("value");
                    int toUser = rs.getInt("toPerson");
                    User user = new User();
                    user.setUserId(toUser);
                    value*=-1; //Multiply with minus, so java knows who own who
                    Debt debt = new Debt(value, user);
                    debts.add(debt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (ResultSet rs2 = st2.executeQuery()) {
                while (rs2.next()) {
                    double value = rs2.getDouble("value");
                    int toUser = rs2.getInt("fromPerson");
                    User user = new User();
                    user.setUserId(toUser);
                    Debt debt = new Debt(value, user);
                    debts.add(debt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //How much each person should pay
            double valForEach = sum/contributors.size();
            valForEach = valForEach*100;
            valForEach = Math.round(valForEach);
            valForEach = valForEach /100;

            String query3;
            String query4;
            for(User user : contributors) {
                if (user.getUserId() != userId) {
                    boolean found = false;
                    for (Debt debt : debts) {
                        if (user.getUserId() == debt.getToUser().getUserId()) {
                            found = true;
                            if (debt.getAmount() < 0) { //The user who added the shopping trip ows someone from before.
                                double newValue = debt.getAmount() + valForEach;
                                if(newValue < 0) {
                                    newValue *= -1;
                                    query3 = "UPDATE Finance SET value=? WHERE fromPerson=? AND toPerson=?;";
                                    try(PreparedStatement preparedStatement = conn.prepareStatement(query3)) {
                                        preparedStatement.setDouble(1, newValue);
                                        preparedStatement.setInt(2, userId);
                                        preparedStatement.setInt(3, user.getUserId());
                                        preparedStatement.executeUpdate();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (newValue > 0){
                                    query3 = "DELETE FROM Finance WHERE fromPerson=? AND toPerson=?";
                                    query4 = "INSERT INTO Finance(fromPerson, toPerson, value) VALUES (?,?,?)";
                                    try(PreparedStatement preparedStatement = conn.prepareStatement(query3);
                                        PreparedStatement preparedStatement2 = conn.prepareStatement(query4)) {
                                        preparedStatement.setInt(1, userId);
                                        preparedStatement.setInt(2, user.getUserId());
                                        preparedStatement.executeUpdate();
                                        preparedStatement2.setInt(1, user.getUserId());
                                        preparedStatement2.setInt(2, userId);
                                        preparedStatement2.setDouble(3, newValue);
                                        preparedStatement2.executeUpdate();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    query3 = "DELETE FROM Finance WHERE fromPerson=? AND toPerson=?";
                                    try(PreparedStatement preparedStatement = conn.prepareStatement(query3)) {
                                        preparedStatement.setInt(1, userId);
                                        preparedStatement.setInt(2, user.getUserId());
                                        preparedStatement.executeUpdate();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else { //Someone ows the user who added the shopping trip from before.
                                double value = debt.getAmount() + valForEach;
                                String updateDebt = "UPDATE Finance SET value=? WHERE fromPerson =? AND toPerson=?";
                                try(PreparedStatement preparedStatement = conn.prepareStatement(updateDebt)) {
                                    preparedStatement.setDouble(1, value);
                                    preparedStatement.setInt(2, user.getUserId());
                                    preparedStatement.setInt(3, userId);
                                    preparedStatement.executeUpdate();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (!found) { //The two people do not owe each other from before.
                        String insertDebt = "INSERT INTO Finance(fromPerson, toPerson, value) VALUES (?,?,?);";
                        try(PreparedStatement preparedStatement = conn.prepareStatement(insertDebt)) {
                            preparedStatement.setInt(1, user.getUserId());
                            preparedStatement.setInt(2, userId);
                            preparedStatement.setDouble(3, valForEach);
                            preparedStatement.executeUpdate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
