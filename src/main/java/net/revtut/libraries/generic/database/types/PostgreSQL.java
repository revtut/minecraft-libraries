package net.revtut.libraries.generic.database.types;

import net.revtut.libraries.generic.database.Database;

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
    public PostgreSQL(final String hostname, final int port, final String database, final String username, final String password) {
        super("org.postgresql.Driver", "jdbc:postgresql://" + hostname + ":" + port + "/" + database, username, password);
    }
}
