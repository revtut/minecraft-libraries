package net.revtut.libraries.database.types;

/**
 * Types of Databases
 */
public enum DatabaseType {
    /**
     * MySQL
     */
    MYSQL,

    /**
     * Oracle
     */
    ORACLE,

    /**
     * Postgre
     */
    POSTGRE;

    /**
     * Get the database type by its name
     * @param name name of the type
     * @return database type
     */
    public DatabaseType getByName(String name) {
        for(DatabaseType type : values())
            if(type.toString().equalsIgnoreCase(name))
                return type;
        return null;
    }
}
