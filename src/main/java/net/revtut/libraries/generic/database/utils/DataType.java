package org.assis.api.libs.database.utils;

/**
 * Data Type for MySQL
 */
public enum DataType {

    /*
        TEXT TYPES
     */

    /**
     * Holds a fixed length string (can contain letters, numbers, and special characters).
     * Can store up to 255 characters
     */
    CHAR,

    /**
     * Holds a variable length string (can contain letters, numbers, and special characters).
     * Can store up to 255 characters.
     * Note: If you put a greater value than 255 it will be converted to a TEXT type
     */
    VARCHAR,

    /**
     * Holds a string with a maximum length of 255 characters.
     */
    TINYTEXT,

    /**
     * Holds a string with a maximum length of 65,535 characters.
     */
    TEXT,

    /**
     * For BLOBs (Binary Large OBjects). Holds up to 65,535 bytes of data.
     */
    BLOB,

    /**
     * Holds a string with a maximum length of 16,777,215 characters.
     */
    MEDIUMTEXT,

    /**
     * For BLOBs (Binary Large OBjects). Holds up to 16,777,215 bytes of data.
     */
    MEDIUMBLOB,

    /**
     * Holds a string with a maximum length of 4,294,967,295 characters.
     */
    LONGTEXT,

    /**
     * For BLOBs (Binary Large OBjects). Holds up to 4,294,967,295 bytes of data.
     */
    LONGBLOB,

    /**
     * Let you enter a list of possible values. You can list up to 65535 values in an ENUM list.
     * If a value is inserted that is not in the list, a blank value will be inserted.
     * Note: The values are sorted in the order you enter them.
     * You enter the possible values in this format: ENUM('X','Y','Z')
     */
    ENUM,

    /**
     * Similar to ENUM except that SET may contain up to 64 list
     * items and can store more than one choice.
     */
    SET,

    /*
        NUMBER TYPES
     */

    /**
     * -128 to 127 normal. 0 to 255 UNSIGNED*.
     */
    TINYINT,

    /**
     * -32768 to 32767 normal. 0 to 65535 UNSIGNED*.
     */
    SMALLINT,

    /**
     * -8388608 to 8388607 normal. 0 to 16777215 UNSIGNED*.
     */
    MEDIUMINT,

    /**
     * -2147483648 to 2147483647 normal. 0 to 4294967295 UNSIGNED*.
     */
    INT,

    /**
     * -9223372036854775808 to 9223372036854775807 normal. 0 to 18446744073709551615 UNSIGNED*.
     */
    BIGINT,

    /**
     * A small number with a floating decimal point.
     */
    FLOAT,

    /**
     * A large number with a floating decimal point.
     */
    DOUBLE,

    /**
     * A DOUBLE stored as a string , allowing for a fixed decimal point.
     */
    DECIMAL
}
