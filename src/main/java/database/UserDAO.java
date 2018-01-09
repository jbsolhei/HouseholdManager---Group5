package database;

import classes.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    public static void addNewUser(User newUser) throws SQLException{

        String email = newUser.getEmail();
        String name = newUser.getName();
        String password = newUser.getPassword();
        String telephone = newUser.getPhone();

        /*
        Fiks passordhasing til passordet
        Fjern throws fra metodekallet
         */

        DBConnector dbc = new DBConnector();
        Connection conn = dbc.getConn();
        Statement statement = conn.createStatement();
        String testStatement = "INSERT INTO Person (email, name, password, telephone) VALUES ('olaNormann@gmail.com','Ola Normann','123456', 42112312)";
        statement.executeUpdate(testStatement);
        dbc.disconnect();
    }
}
