package database;

import java.sql.*;

public class DBConnector implements AutoCloseable {
    static String url = "jdbc:mysql://mysql.stud.iie.ntnu.no/g_tdat2003_t5?user=g_tdat2003_t5&password=DPiNHSqD&useSSL=true&verifyServerCertificate=false";
    private Connection conn;

    /**
     * Laster JDBC-klassene og åpner databaseforbindelsen.
     */
    public DBConnector () {
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            CleanUp.writeMessage(e, "DBConnector constructor");
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void disconnect () {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSetFromStatement(String statement) {
        if (statement == null || statement.length() == 0) return null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            return preparedStatement.executeQuery();
        } catch (SQLException sqle) {
            CleanUp.writeMessage(sqle, "getResultFromStatement");
        }
        return null;
    }

    public boolean updateDatabase(String statement){
        if (statement == null || statement.length() == 0) return false;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            return preparedStatement.executeUpdate()!=0;
        }catch(SQLException sqle){
            CleanUp.writeMessage(sqle, "updateDatabase");
            return false;
        }
    }

    @Override
    public void close() throws SQLException {
        disconnect();
    }
}
