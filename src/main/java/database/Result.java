package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p>Result class.</p>
 *
 */
public class Result {
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    /**
     * <p>Constructor for Result.</p>
     *
     * @param resultSet a {@link java.sql.ResultSet} object.
     * @param preparedStatement a {@link java.sql.PreparedStatement} object.
     */
    public Result(ResultSet resultSet, PreparedStatement preparedStatement) {
        this.resultSet = resultSet;
        this.preparedStatement = preparedStatement;
    }

    /**
     * <p>Getter for the field <code>resultSet</code>.</p>
     *
     * @return a {@link java.sql.ResultSet} object.
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * <p>Setter for the field <code>resultSet</code>.</p>
     *
     * @param resultSet a {@link java.sql.ResultSet} object.
     */
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * <p>Getter for the field <code>preparedStatement</code>.</p>
     *
     * @return a {@link java.sql.PreparedStatement} object.
     */
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    /**
     * <p>Setter for the field <code>preparedStatement</code>.</p>
     *
     * @param preparedStatement a {@link java.sql.PreparedStatement} object.
     */
    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
}
