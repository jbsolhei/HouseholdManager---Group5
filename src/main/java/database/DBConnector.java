package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnector {
    private Connection conn;

    /**
     * Laster JDBC-klassene og åpner databaseforbindelsen.
     */
    public DBConnector () throws Exception {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://juicebuster.wada.world:3306/juicebuster?user=*********&password=**********&useSSL=true&verifyServerCertificate=false");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public DBConnector(String url, String username, String password) throws Exception {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public DBConnector(String DBPath) throws Exception {
        try {
            conn = DriverManager.getConnection(DBPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResultSet getResultSetFromStatement(PreparedStatement statement){
        return null;
    }

}
