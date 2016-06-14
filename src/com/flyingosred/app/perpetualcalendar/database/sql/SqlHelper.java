/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {

    private static final int BATCH_PACKAGE_SIZE = 1000;

    private final String mUrl;

    private final String mDbPath;

    public SqlHelper(String dbName) {
        mUrl = "jdbc:sqlite:" + dbName;
        mDbPath = dbName;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeDatabase() {
        File dbFile = new File(mDbPath);
        if (dbFile.exists()) {
            System.out.println("dbFile " + dbFile.toString() + " is removed.");
            dbFile.delete();
        }
    }

    public void createDatabase() {
        try {
            System.out.println("Creating database " + mUrl);
            Connection conn = DriverManager.getConnection(mUrl);
            Statement statement = conn.createStatement();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excute(String sql) {
        try {
            Connection conn = DriverManager.getConnection(mUrl);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeBatch(String sql, SqlBatchData data) {
        try {
            Connection conn = DriverManager.getConnection(mUrl);
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);
            long startTime = System.currentTimeMillis();
            int i;
            for (i = 0; i < data.getSize(); i++) {
                if (data.setValues(statement, i)) {
                    statement.addBatch();
                }
                if (i % BATCH_PACKAGE_SIZE == 0) {
                    statement.executeBatch();
                }
            }
            System.out.println("Running executeBatch with " + i + " records.");
            statement.executeBatch();
            System.out.println("executeBatch done");
            conn.commit();
            statement.close();
            conn.close();
            long endTime = System.currentTimeMillis();
            long elapsedTime = (endTime - startTime) / 1000;
            System.out.println(
                    "Total time required to execute data.getSize() queries using PreparedStatement with JDBC batch insert is :"
                            + elapsedTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
