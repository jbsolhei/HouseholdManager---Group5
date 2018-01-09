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
    public static void closeResSet(ResultSet res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeResSet()");
        }
    }

    public static void closeStatement(Statement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeStatement()");
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeConnection()");
        }
    }

    public static void rollback(Connection forbindelse) {
        try {
            if (forbindelse != null && !forbindelse.getAutoCommit()) {
                forbindelse.rollback();
            }
        } catch (SQLException e) {
            writeMessage(e, "rollback()");
        }
    }

    public static void setAutoCommit(Connection forbindelse) {
        try {
            if (forbindelse != null && !forbindelse.getAutoCommit()) {
                forbindelse.setAutoCommit(true);
            }
        } catch (SQLException e) {
            writeMessage(e, "setAutoCommit()");
        }
    }

    public static void writeMessage(Exception e, String melding) {
        System.err.println("*** Error: " + melding + ". ***");
        e.printStackTrace(System.err);
    }

    public static void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            writeMessage(e, "closeBufferedReader()");
        }
    }

    public static void closeFileReader(FileReader fileReader) {
        try {
            if (fileReader != null) {
                fileReader.close();
            }
        } catch (IOException e) {
            writeMessage(e, "closeFileReader()");
        }
    }

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