package database;

import classes.Debt;
import classes.ShoppingTrip;
import classes.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class ShoppingTripDAO {

    public static List<ShoppingTrip> getShoppingTrips(int houseId) {
        String query = "SELECT * FROM Shopping_trip WHERE houseId = ?;";

        List<ShoppingTrip> shoppingTripList = new ArrayList<>();

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    ShoppingTrip shoppingTrip = new ShoppingTrip();
                    shoppingTrip.setShoppingTripId(rs.getInt("shopping_tripId"));
                    shoppingTrip.setExpence(rs.getDouble("expence"));
                    shoppingTrip.setShoppingDate(rs.getDate("shopping_tripDate").toLocalDate());
                    shoppingTrip.setName(rs.getString("shopping_tripName"));
                    shoppingTrip.setComment(rs.getString("comment"));
                    shoppingTrip.setUserId(rs.getInt("userId"));
                    shoppingTripList.add(shoppingTrip);
                }
            }

            return shoppingTripList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean createShoppingTrip(ShoppingTrip shoppingTrip) {
        String query = "INSERT INTO Shopping_trip (expence, shopping_tripName, shopping_tripDate, " +
                "comment, userId, houseId, shopping_listId) VALUES (?,?,?,?,?,?,?)";


        if(shoppingTrip.getContributors() == null) {
            return false;
        }

        int id = 0;
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            st.setDouble(1, shoppingTrip.getExpence());
            st.setString(2, shoppingTrip.getName());
            Date date = Date.valueOf(shoppingTrip.getShoppingDate());
            st.setDate(3, date);
            st.setString(4, shoppingTrip.getComment());
            st.setInt(5, shoppingTrip.getUserId());
            st.setInt(6, shoppingTrip.getHouseId());
            if (shoppingTrip.getShopping_listId()>0) {
                st.setInt(7, shoppingTrip.getShopping_listId());
            } else {
                st.setNull(7,NULL);
            }
            st.executeUpdate();

            try (ResultSet resultSet = st.getGeneratedKeys()) {
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }

            }

            String query2 = "INSERT INTO User_Shopping_trip (userId, shopping_tripId) VALUES (?,?)";
            for (User user : shoppingTrip.getContributors()) {
                try (PreparedStatement preparedStatement = conn.prepareStatement(query2)) {
                    preparedStatement.setInt(1, user.getUserId());
                    preparedStatement.setInt(2, id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

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

        try(DBConnector dbc = new DBConnector();
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
    


    public static ShoppingTrip getShoppingTrip(int shopping_tripId) {
        String query = "SELECT p.* FROM Person p, User_Shopping_trip us WHERE p.userId=us.userId AND us.shopping_tripId=?";
        String query2 = "SELECT st.*, p.name FROM Shopping_trip st, Person p WHERE " +
                "shopping_tripId=? AND p.userId = st.userId";
        String query3 = "SELECT name FROM Shopping_list WHERE shopping_listId = ?;";
        List<User> users = new ArrayList<>();
        ShoppingTrip shoppingTrip = new ShoppingTrip();

        try (DBConnector dbConnector = new DBConnector();
             Connection connection = dbConnector.getConn()) {

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, shopping_tripId);

                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setUserId(resultSet.getInt("userId"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setTelephone(resultSet.getString("telephone"));
                        users.add(user);
                    }
                }
            }

            try (PreparedStatement ps2 = connection.prepareStatement(query2)) {
                ps2.setInt(1, shopping_tripId);

                try (ResultSet rs = ps2.executeQuery()) {
                    while (rs.next()) {
                        shoppingTrip.setName(rs.getString("shopping_tripName"));
                        shoppingTrip.setExpence(rs.getDouble("expence"));
                        shoppingTrip.setComment(rs.getString("comment"));
                        shoppingTrip.setUserId(rs.getInt("userId"));
                        shoppingTrip.setUserName(rs.getString("p.name"));
                        shoppingTrip.setShoppingDate(rs.getDate("shopping_tripDate").toLocalDate());
                        shoppingTrip.setShopping_listId(rs.getInt("shopping_listId"));
                    }
                    shoppingTrip.setContributors(users);
                }
            }

            if (shoppingTrip.getShopping_listId() != 0) {
                try (PreparedStatement ps3 = connection.prepareStatement(query3)) {
                    ps3.setInt(1, shoppingTrip.getShopping_listId());

                    try (ResultSet rs1 = ps3.executeQuery()) {
                        while (rs1.next()) {
                            shoppingTrip.setShopping_listName(rs1.getString("name"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingTrip;
    }

    public static void deleteShoppingTrip(int shoppingTripId) {
        String query = "DELETE FROM Shopping_trip WHERE shopping_tripId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shoppingTripId);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
