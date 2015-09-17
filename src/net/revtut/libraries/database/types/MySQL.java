package net.revtut.libraries.database.types;

import net.revtut.libraries.database.Database;

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
    public MySQL(String hostname, int port, String database, String username, String password) {
        super("com.mysql.jdbc.Driver", "jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
    }
}
