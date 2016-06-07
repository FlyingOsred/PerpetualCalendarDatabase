package com.flyingosred.app.perpetualcalendar.database.platform;

import java.io.File;

public abstract class PlatformBase {

    public static final int PLATFORM_ANDROID = 1;

    private final int mType;

    private final String mGeneratedPath;

    public PlatformBase(int type, String path) {
        mType = type;
        mGeneratedPath = combineGeneratedPath(path);
    }

    public int getType() {
        return mType;
    }

    public abstract void generate();

    protected String getGeneratedPath() {
        return mGeneratedPath;
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
}
