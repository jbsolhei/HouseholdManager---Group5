package database;

import java.sql.*;

public class DBConnector {
    private Connection conn;

    /**
     * Laster JDBC-klassene og åpner databaseforbindelsen.
     */
    public DBConnector () throws Exception {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://juicebuster.wada.world:3306/juicebuster?user=*********&password=**********&useSSL=true&verifyServerCertificate=false");
        } catch (Exception e) {
            CleanUp.writeMessage(e, "DBConnector constructor");
            throw e;
        }
    }
    public DBConnector(String url, String username, String password) throws Exception {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            CleanUp.writeMessage(e, "DBConnector constructor");
            throw e;
        }
    }
    public DBConnector(String DBPath) throws Exception {
        try {
            conn = DriverManager.getConnection(DBPath);
        } catch (Exception e) {
            CleanUp.writeMessage(e, "DBConnector constructor");
            throw e;
        }
    }

    public ResultSet getResultSetFromStatement(String statement) {
        if(statement==null||statement.length()==0)return null;
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            return preparedStatement.executeQuery();
        }catch(SQLException sqle){
            CleanUp.writeMessage(sqle, "getResultFromStatement");
        }
        return null;
    }


    public static void main(String[] args) {

    }

}