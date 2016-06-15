/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.flyingosred.app.perpetualcalendar.database.DatabaseItem;
import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;
import com.flyingosred.app.perpetualcalendar.database.sql.SqlBatchData;
import com.flyingosred.app.perpetualcalendar.database.sql.SqlHelper;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public abstract class PlatformBase {

    public static final int PLATFORM_ANDROID = 1;

    public static final String DATABASE_NAME = "database.db";

    public static final String COMPRESSED_DATABASE_NAME = "database.zip";

    private static final String TABLE_NAME = "calendar";

    private final int mType;

    private final String mGeneratedPath;

    private final String mGeneratedDbPath;

    private final String mCompressedDbPath;

    private final SqlHelper mSqlHelper;

    public PlatformBase(int type, String path) {
        mType = type;
        mGeneratedPath = combineGeneratedPath(path);
        Utils.delete(new File(mGeneratedPath));
        mGeneratedDbPath = new File(getGeneratedDbPath(), DATABASE_NAME).getPath();
        mCompressedDbPath = new File(getGeneratedDbPath(), COMPRESSED_DATABASE_NAME).getPath();
        mSqlHelper = new SqlHelper(mGeneratedDbPath);
    }

    public int getType() {
        return mType;
    }

    public abstract void generate();

    public abstract void generateResources(List<Resource> resourceList);

    public void generateDatabase(List<DatabaseItem> databaseList) {
        createDatabase();
        List<String> regionList = getRegions(databaseList);
        createTable(regionList);
        insertRecorders(databaseList, regionList);
        compressDatabase();
    }

    protected String getGeneratedPath() {
        return mGeneratedPath;
    }

    protected void createDatabase() {
        mSqlHelper.removeDatabase();
        mSqlHelper.createDatabase();
    }

    protected SqlHelper getSqlHelper() {
        return mSqlHelper;
    }

    private List<String> getRegions(List<DatabaseItem> databaseList) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (DatabaseItem item : databaseList) {
            set.addAll(item.getHolidayMap().keySet());
        }
        List<String> regionList = new ArrayList<String>(set);
        return regionList;
    }

    private void createTable(List<String> regionList) {
        String sql = "create table " + TABLE_NAME + " " + "(_id int primary key                 not null,"
                + " solar                 TEXT    not null, " + " lunar_year            int     not null,"
                + " lunar_month           int     not null, " + " lunar_day             int     not null,"
                + " lunar_is_leap_month   int,              " + " lunar_days_in_month   int     not null,"
                + " solar_term_id         int,              " + " constellation_id      int     not null,";

        for (int i = 0; i < regionList.size(); i++) {
            sql += "holiday_" + regionList.get(i) + "         TEXT";
            if (i != regionList.size() - 1) {
                sql += ",";
            }
        }
        sql += ");";
        System.out.println("Create table with sql " + sql);
        mSqlHelper.excute(sql);
    }

    private void insertRecorders(final List<DatabaseItem> databaseList, final List<String> regionList) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(TABLE_NAME);
        sql.append(" ");
        sql.append(
                "(_id, solar, lunar_year, lunar_month, lunar_day, lunar_is_leap_month, lunar_days_in_month, solar_term_id, constellation_id ");
        for (int i = 0; i < regionList.size(); i++) {
            sql.append(", holiday_");
            sql.append(regionList.get(i));
        }
        sql.append(") ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?");
        for (int i = 0; i < regionList.size(); i++) {
            sql.append(", ?");
        }
        sql.append(")");
        System.out.println("Create table with sql " + sql);

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        mSqlHelper.executeBatch(sql.toString(), new SqlBatchData() {

            @Override
            public boolean setValues(PreparedStatement ps, int i) {
                DatabaseItem item = databaseList.get(i);
                Lunar lunar = item.getLunar();
                int solarTermId = item.getSolarTermId();
                Map<String, String> holidayMap = item.getHolidayMap();
                try {

                    ps.setInt(1, i + 1);
                    ps.setString(2, formatter.format(item.get().getTime()));
                    ps.setInt(3, lunar.getYear());
                    ps.setInt(4, lunar.getMonth());
                    ps.setInt(5, lunar.getDay());
                    ps.setBoolean(6, lunar.isLeapMonth());
                    ps.setInt(7, lunar.getDaysInMonth());
                    if (solarTermId > 0) {
                        ps.setInt(8, solarTermId);
                    } else {
                        ps.setNull(8, Types.INTEGER);
                    }
                    ps.setInt(9, item.getConstellationId());
                    for (int j = 0; j < regionList.size(); j++) {
                        if (holidayMap == null || !holidayMap.containsKey(regionList.get(j))) {
                            ps.setNull(10 + j, Types.CHAR);
                        } else {
                            ps.setString(10 + j, holidayMap.get(regionList.get(j)));

                        }
                    }
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public int getSize() {
                return databaseList.size();
            }
        });
    }

    private void compressDatabase() {
        byte[] buffer = new byte[1024];

        try {
            FileOutputStream fos = new FileOutputStream(mCompressedDbPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(DATABASE_NAME);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(mGeneratedDbPath);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            zos.close();
            System.out.println("Database compressed.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String combineGeneratedPath(String path) {
        File currentDirFile = new File(".");
        File generatedDir = new File(currentDirFile, "generated");
        File generatedPlatformDir = new File(generatedDir, path);
        if (!generatedPlatformDir.exists()) {
            generatedPlatformDir.mkdirs();
        }
        return generatedPlatformDir.getAbsolutePath();
    }

    private File getGeneratedDbPath() {
        File generatedDbDir = new File(mGeneratedPath, "db");
        if (!generatedDbDir.exists()) {
            generatedDbDir.mkdirs();
        }
        return generatedDbDir;
    }
}
