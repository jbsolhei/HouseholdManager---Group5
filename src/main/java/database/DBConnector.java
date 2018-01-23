package database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;

public class DBConnector implements AutoCloseable {
    static String DB_URL = "jdbc:mysql://mysql.stud.iie.ntnu.no/g_tdat2003_t5?user=g_tdat2003_t5&password=DPiNHSqD&useSSL=true&verifyServerCertificate=false";
    static boolean USE_CONNECTION_POOLING = true;
    private static ComboPooledDataSource pool = null;

    private Connection conn;

    /**
     * Laster JDBC-klassene og åpner databaseforbindelsen.
     */
    public DBConnector () {
        try {
            if (USE_CONNECTION_POOLING) {
                if (pool == null) {
                    setUpPool();
                }

                conn = pool.getConnection();
            }
            else {
                conn = DriverManager.getConnection(DB_URL);
            }
        }
        catch (Exception e) {
            CleanUp.writeMessage(e, "DBConnector constructor");
        }
    }

    private static void setUpPool() throws SQLException {
        try {
            System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
            System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF");

            pool = new ComboPooledDataSource();
            pool.setDriverClass("com.mysql.jdbc.Driver");
            pool.setJdbcUrl(DB_URL);

            pool.setMinPoolSize(0);
            pool.setMaxPoolSize(4); // Set this to 30+ when deploying
            pool.setAcquireIncrement(1);
            pool.setInitialPoolSize(1);
            pool.setMaxStatementsPerConnection(15);
            pool.setIdleConnectionTestPeriod(240);

            // TODO Fjern disse i prod!
            //pool.setUnreturnedConnectionTimeout(30);
            //pool.setDebugUnreturnedConnectionStackTraces(true);
        }
        catch (PropertyVetoException e) {
            CleanUp.writeMessage(e, "DBConnector.setUpPool()");
            throw new SQLException("Can't set up ComboPooledDataSource", e);
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
