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
    public OracleSQL(String hostname, String port, String database, String username, String password) {
        super("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + database, username, password);
    }
}
