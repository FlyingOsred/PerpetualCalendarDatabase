package com.flyingosred.app.perpetualcalendar.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {

    private static final int BATCH_PACKAGE_SIZE = 1000;

    private String mDBName = null;

    public SqlHelper(String dbName) {
        mDBName = dbName;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:db/" + mDBName);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE 'android_metadata' ('locale' TEXT DEFAULT 'en_US');");
            statement.executeUpdate("INSERT INTO 'android_metadata' VALUES ('en_US');");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
