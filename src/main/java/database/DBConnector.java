package database;

import java.sql.*;


public class DBConnector {
    private Connection conn;

    /**
     * Laster JDBC-klassene og åpner databaseforbindelsen.
     */
    public DBConnector () {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no/g_tdat2003_t5?user=g_tdat2003_t5&password=DPiNHSqD&useSSL=true&verifyServerCertificate=false");
            System.out.println("Connection created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void disconnect () {
        try {
            conn.close();
            System.out.println("Connection disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSetFromStatement(PreparedStatement statement){
        return null;
    }

    public static void main(String[] args) throws SQLException {
        DBConnector dbc = new DBConnector();
        Connection conn = dbc.getConn();
        Statement statement = conn.createStatement();
        String testStatement = "INSERT INTO Person (email, name, password, telephone) VALUES ('olaNormann@gmail.com','Ola Normann','123456', 42112312)";
        statement.executeUpdate(testStatement);
        dbc.disconnect();
    }
}
