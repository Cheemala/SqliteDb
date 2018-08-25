package asqlitedb.cheemala.com.utils;

public class Constants {

    public final static String DB_TABLE_NAME = "APP_DATA";
    public final static String DB_ID_NAME = "APP_DATA_ID";
    public final static String DB_USER_NAME = "APP_DATA_UN";
    public final static String DB_NAME = "Sql_Ex";
    public final static int DB_VERSION =1;

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getDbTableName() {
        return DB_TABLE_NAME;
    }

    public static String getDbIdName() {
        return DB_ID_NAME;
    }

    public static String getDbUserName() {
        return DB_USER_NAME;
    }

}
