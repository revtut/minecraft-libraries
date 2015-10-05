package net.revtut.libraries.database.types;

import net.revtut.libraries.database.Database;

/**
 * OracleSQL Database
 */
public class OracleSQL extends Database {

    /**
     * Constructor of OracleSQL
     * @param hostname hostname of the OracleSQL
     * @param port     port of the OracleSQL
     * @param database database of the OracleSQL
     * @param username username of the OracleSQL
     * @param password password of the OracleSQL
     */
    public OracleSQL(final String hostname, final int port, final String database, final String username, final String password) {
        super("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + database, username, password);
    }
}
