package database;

/**
 *
 * Opprydder.java  - "Programmering i Java", 4.utgave - 2009-07-01
 * Metoder for å rydde opp etter databasebruk
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
public class CleanUp {
    /**
     * <p>closeResSet.</p>
     *
     * @param res a {@link java.sql.ResultSet} object.
     */
    public static void closeResSet(ResultSet res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeResSet()");
        }
    }

    /**
     * <p>closeStatement.</p>
     *
     * @param stm a {@link java.sql.Statement} object.
     */
    public static void closeStatement(Statement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeStatement()");
        }
    }

    /**
     * <p>closeConnection.</p>
     *
     * @param connection a {@link java.sql.Connection} object.
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeConnection()");
        }
    }

    /**
     * <p>rollback.</p>
     *
     * @param forbindelse a {@link java.sql.Connection} object.
     */
    public static void rollback(Connection forbindelse) {
        try {
            if (forbindelse != null && !forbindelse.getAutoCommit()) {
                forbindelse.rollback();
            }
        } catch (SQLException e) {
            writeMessage(e, "rollback()");
        }
    }

    /**
     * <p>setAutoCommit.</p>
     *
     * @param forbindelse a {@link java.sql.Connection} object.
     */
    public static void setAutoCommit(Connection forbindelse) {
        try {
            if (forbindelse != null && !forbindelse.getAutoCommit()) {
                forbindelse.setAutoCommit(true);
            }
        } catch (SQLException e) {
            writeMessage(e, "setAutoCommit()");
        }
    }

    /**
     * <p>writeMessage.</p>
     *
     * @param e a {@link java.lang.Exception} object.
     * @param melding a {@link java.lang.String} object.
     */
    public static void writeMessage(Exception e, String melding) {
        System.err.println("*** Error: " + melding + ". ***");
        e.printStackTrace(System.err);
    }

    /**
     * <p>closeBufferedReader.</p>
     *
     * @param bufferedReader a {@link java.io.BufferedReader} object.
     */
    public static void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            writeMessage(e, "closeBufferedReader()");
        }
    }

    /**
     * <p>closeFileReader.</p>
     *
     * @param fileReader a {@link java.io.FileReader} object.
     */
    public static void closeFileReader(FileReader fileReader) {
        try {
            if (fileReader != null) {
                fileReader.close();
            }
        } catch (IOException e) {
            writeMessage(e, "closeFileReader()");
        }
    }

    /**
     * <p>closeFileWriter.</p>
     *
     * @param fileWriter a {@link java.io.FileWriter} object.
     */
    public static void closeFileWriter(FileWriter fileWriter) {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            writeMessage(e, "closeFileWriter()");
        }
    }
}
