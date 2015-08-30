package net.revtut.libraries.database.types;

import net.revtut.libraries.database.Database;

/**
 * PostgreSQL Database
 */
public class PostgreSQL extends Database {

    /**
     * Constructor of PostgreSQL
     * @param hostname hostname of the PostgreSQL
     * @param port     port of the PostgreSQL
     * @param database database of the PostgreSQL
     * @param username username of the PostgreSQL
     * @param password password of the PostgreSQL
     */
    public PostgreSQL(String hostname, String port, String database, String username, String password) {
        super("org.postgresql.Driver", "jdbc:postgresql://" + hostname + ":" + port + "/" + database, username, password);
    }
}
