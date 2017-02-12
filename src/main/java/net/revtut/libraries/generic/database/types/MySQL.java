package org.assis.api.libs.database.types;

import org.assis.api.libs.database.Database;

/**
 * MySQL Database
 */
public class MySQL extends Database {

    /**
     * Constructor of MySQL
     * @param hostname hostname of the MySQL
     * @param port     port of the MySQL
     * @param database database of the MySQL
     * @param username username of the MySQL
     * @param password password of the MySQL
     */
    public MySQL(final String hostname, final int port, final String database, final String username, final String password) {
        super("com.mysql.jdbc.Driver", "jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
    }
}
