package com.flyingosred.app.perpetualcalendar.database.platform;

import java.io.File;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.resource.Resource;
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
        mGeneratedPath = combineGeneratedPath(path);
        mGeneratedDbPath = new File(getGeneratedDbPath(), DATABASE_NAME).getPath();
        mSqlHelper = new SqlHelper(mGeneratedDbPath);
    }

    public int getType() {
        return mType;
    }

    public abstract void generate();

    public abstract void generateResources(List<Resource> resourceList);

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

    private String combineGeneratedPath(String path) {
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
