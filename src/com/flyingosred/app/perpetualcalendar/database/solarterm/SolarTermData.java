package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.library.PerpetualCalendarContract;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public final class SolarTermData extends Data {

    private final LinkedHashMap<Date, Integer> mDataMap = new LinkedHashMap<>();

    public SolarTermData(XSSFSheet sheet) {
        super(sheet);
        parse(sheet);
    }

    @Override
    public int getId(Calendar calendar) {
        for (Map.Entry<Date, Integer> entry : mDataMap.entrySet()) {
            Date date = entry.getKey();
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(date);
            if (Utils.isSameDay(calendar, tempCalendar)) {
                return entry.getValue();
            }
        }
        return PerpetualCalendarContract.INVALID;
    }

    private void parse(XSSFSheet sheet) {
        int i;
        int j;
        int rows = sheet.getPhysicalNumberOfRows();
        for (i = EXCEL_ROW_DATA_START; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            int cols = row.getPhysicalNumberOfCells();
            int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_ID);
            if (id < 0) {
                continue;
            }
            for (j = EXCEL_COL_DATA_START_WITH_SOURCE; j < cols; j++) {
                Date date = ExcelHelper.getDateCellValue(row, j);
                mDataMap.put(date, id);
            }
        }
    }

}
