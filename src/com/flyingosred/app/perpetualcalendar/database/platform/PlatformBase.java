package com.flyingosred.app.perpetualcalendar.database.platform;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.DatabaseItem;
import com.flyingosred.app.perpetualcalendar.database.sql.SqlBatchData;
import com.flyingosred.app.perpetualcalendar.database.sql.SqlHelper;

public abstract class PlatformBase {

    public static final int PLATFORM_ANDROID = 1;

    public static final String DATABASE_NAME = "database.db";

    private static final String TABLE_NAME = "calendar";

    private final int mType;

    private final String mGeneratedPath;

    private final String mGeneratedDbPath;

    private final SqlHelper mSqlHelper;

    public PlatformBase(int type, String path) {
        mType = type;
        mGeneratedPath = getGeneratedPath(path);
        mGeneratedDbPath = new File(getGeneratedDbPath(), DATABASE_NAME).getPath();
        mSqlHelper = new SqlHelper(mGeneratedDbPath);
    }

    public int getType() {
        return mType;
    }

    public void generate(final List<DatabaseItem> itemList) {
        generateDatabase();
        String sqlCreateTable = "create table " + TABLE_NAME + " " + "(id int primary key       not null,"
                + " year                  int     not null, " + " month                 int     not null,"
                + " day                   int     not null, " + " lunar_year            int     not null,"
                + " lunar_month           int     not null, " + " lunar_day             int     not null,"
                + " lunar_is_leap_month   int,              " + " lunar_days_in_month   int     not null,"
                + " solar_term_id         int,              " + " constellation_id      int     not null,"
                + " holiday               text);";
        mSqlHelper.excute(sqlCreateTable);

        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("insert into ");
        sqlInsert.append(TABLE_NAME);
        sqlInsert.append(" ");
        sqlInsert.append(
                "(id, year, month, day, lunar_year, lunar_month, lunar_day, lunar_is_leap_month, lunar_days_in_month, solar_term_id, constellation_id, holiday) ");
        sqlInsert.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        mSqlHelper.executeBatch(sqlInsert.toString(), new SqlBatchData() {

            @Override
            public boolean setValues(PreparedStatement ps, int i) {
                DatabaseItem item = itemList.get(i);
                try {
                    ps.setInt(1, i + 1);
                    ps.setInt(2, item.get().get(Calendar.YEAR));
                    ps.setInt(3, item.get().get(Calendar.MONTH));
                    ps.setInt(4, item.get().get(Calendar.DATE));
                    ps.setInt(5, item.getLunar().getYear());
                    ps.setInt(6, item.getLunar().getMonth());
                    ps.setInt(7, item.getLunar().getDay());
                    if (item.getLunar().isLeapMonth()) {
                        ps.setInt(8, 1);
                    } else {
                        ps.setNull(8, Types.INTEGER);
                    }
                    ps.setInt(9, item.getLunar().getDaysInMonth());
                    if (item.getSolarTermId() >= 0) {
                        ps.setInt(10, item.getSolarTermId());
                    } else {
                        ps.setNull(10, Types.INTEGER);
                    }
                    ps.setInt(11, item.getConstellationId());
                    if (item.getHolidayList().size() > 0) {
                        Integer[] holidays = item.getHolidayList().toArray(new Integer[item.getHolidayList().size()]);
                        ps.setString(12, Arrays.toString(holidays));
                    } else {
                        ps.setNull(12, Types.CHAR);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public int getSize() {
                return itemList.size();
            }
        });
    }

    protected void generateDatabase() {
        mSqlHelper.removeDatabase();
        mSqlHelper.createDatabase();
    }

    protected SqlHelper getSqlHelper() {
        return mSqlHelper;
    }

    private String getGeneratedPath(String path) {
        File currentDirFile = new File(".");
        File generatedDir = new File(currentDirFile, "generated");
        File generatedPlatformDir = new File(generatedDir, path);
        if (!generatedPlatformDir.exists()) {
            generatedPlatformDir.mkdirs();
        }
        return generatedPlatformDir.getPath();
    }

    private File getGeneratedDbPath() {
        File generatedDbDir = new File(mGeneratedPath, "db");
        if (!generatedDbDir.exists()) {
            generatedDbDir.mkdirs();
        }
        return generatedDbDir;
    }
}
