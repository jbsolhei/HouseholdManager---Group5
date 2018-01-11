package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Result {
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public Result(ResultSet resultSet, PreparedStatement preparedStatement) {
        this.resultSet = resultSet;
        this.preparedStatement = preparedStatement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
}
