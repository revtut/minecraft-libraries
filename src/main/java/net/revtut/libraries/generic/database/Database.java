package org.assis.api.libs.database;


import org.assis.api.libs.database.types.DatabaseType;
import org.assis.api.libs.database.types.MySQL;
import org.assis.api.libs.database.types.OracleSQL;
import org.assis.api.libs.database.types.PostgreSQL;

import java.sql.*;
import java.util.List;

/**
 * Database Object.
 */
public abstract class Database {

    /**
     * Database driver
     */
    private final String driver;

    /**
     * URL connection
     */
    private final String url;

    /**
     * Username of the Database
     */
    private final String username;

    /**
     * Password of the Database
     */
    private final String password;

    /**
     * Database connection
     */
    private Connection connection;

    /**
     * Constructor of Database
     *
     * @param driver   driver of the database
     * @param url      url for connecting to the database
     * @param username username of the database
     * @param password password of the database
     */
    public Database(final String driver, final String url, final String username, final String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Create a database
     *
     * @param type type of the database
     * @return created database
     */
    public static Database createDatabase(final DatabaseType type, final String hostname, final int port, final String database, final String username, final String password) {
        switch (type) {
            case MYSQL:
                return new MySQL(hostname, port, database, username, password);
            case ORACLE:
                return new OracleSQL(hostname, port, database, username, password);
            case POSTGRE:
                return new PostgreSQL(hostname, port, database, username, password);
            default:
                return null;
        }
    }

    /**
     * Get the connection to a database
     *
     * @return connection to database
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Connect to the database
     */
    public void connect() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed())
            return;

        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Close the connection to the database
     */
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback any active transaction on the database
     */
    public void rollback() {
        try {
            if (connection != null)
                connection.rollback();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the connection to the database
     *
     * @return true if closed, false otherwise
     */
    public boolean isClosed() {
        try {
            if (connection != null)
                return connection.isClosed();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Close a statement
     *
     * @param statement statement to be closed
     */
    public void close(final Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close a ResultSet
     *
     * @param resultSet result set to be closed
     */
    public void close(final ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query Database
     *
     * @param sql        sql query
     * @param parameters parameters of the prepared statement
     * @return result of the given query
     */
    public ResultSet executeQuery(final String sql, final List<Object> parameters) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);

            int index = 0;
            for (final Object parameter : parameters)
                preparedStatement.setObject(++index, parameter);

            resultSet = preparedStatement.executeQuery();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Update Database
     *
     * @param sql        sql statement
     * @param parameters parameters of the prepared statement
     * @return number of rows updated
     */
    public int executeUpdate(final String sql, final List<Object> parameters) {
        int numberRowsUpdated = -1;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            int index = 0;
            for (final Object parameter : parameters)
                preparedStatement.setObject(++index, parameter);

            numberRowsUpdated = preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
        }

        return numberRowsUpdated;
    }
}
