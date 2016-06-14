/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.library.PerpetualCalendarContract;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class HolidayData extends Data {

    private static final int EXCEL_COL_NAME = 1;

    private final List<HolidayDataItem> mDataList = new ArrayList<>();

    private final Map<Date, List<HolidayDataItem>> mDataMap = new LinkedHashMap<>();

    public HolidayData(XSSFSheet sheet) {
        super(sheet);
        parse(sheet);
    }

    @Override
    public int getId(Calendar calendar) {
        return PerpetualCalendarContract.INVALID;
    }

    public String getString(Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        int offWork = PerpetualCalendarContract.INVALID;
        for (int i = 0; i < mDataList.size(); i++) {
            HolidayDataItem item = mDataList.get(i);
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(item.getDate());
            if (Utils.isSameDay(calendar, tempCalendar)) {
                if (item.getType() != null) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(item.formatString());
                }
                if (item.getOffWork() != PerpetualCalendarContract.INVALID) {
                    offWork = item.getOffWork();
                }
            }
        }

        if (offWork != PerpetualCalendarContract.INVALID) {
            sb.append("#");
            sb.append(offWork);
        }

        if (sb.length() > 0) {
            return sb.toString();
        }

        return null;
    }

    public String getRegion() {
        return mDataMap.get(mDataMap.keySet().toArray()[0]).get(0).getRegion();
    }

    private void parse(XSSFSheet sheet) {
        int rows = sheet.getPhysicalNumberOfRows();
        String sheetName = sheet.getSheetName();
        String regionName = sheetName.replace(HolidayDatabase.EXCEL_HOLIDAY_SHEET_NAME_SUFFIX, "").toLowerCase();
        for (int i = EXCEL_ROW_DATA_START; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            String nameFormula = ExcelHelper.getCellFormula(row, EXCEL_COL_NAME);
            String refType = null;
            int id = PerpetualCalendarContract.INVALID;
            if (nameFormula != null) {
                String[] nameFormulaParts = nameFormula.split("!");
                String refSheetName = nameFormulaParts[0];
                String refCellName = nameFormulaParts[1];
                refType = getNamePrefix(refSheetName).toLowerCase();
                String refCellIndex = refCellName.replaceAll("[^\\d.]", "");
                int index = Integer.parseInt(refCellIndex);
                id = index - EXCEL_ROW_DATA_START;
            }
            int cols = row.getPhysicalNumberOfCells();
            for (int j = EXCEL_COL_DATA_START; j < cols; j = j + 2) {
                Date date = ExcelHelper.getDateCellValue(row, j);
                int offWork = ExcelHelper.getIntCellValue(row, j + 1);
                HolidayDataItem item = new HolidayDataItem(regionName, refType, id, date, offWork);
                System.out.println("Creating holiday data " + item.toString());
                mDataList.add(item);
                List<HolidayDataItem> dateList;
                if (mDataMap.containsKey(date)) {
                    dateList = mDataMap.get(date);
                } else {
                    dateList = new ArrayList<>();
                    mDataMap.put(date, dateList);
                }
                dateList.add(item);
            }
        }
    }
}
