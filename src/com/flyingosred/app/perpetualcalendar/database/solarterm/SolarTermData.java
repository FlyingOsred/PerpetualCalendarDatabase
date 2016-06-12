package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class SolarTermData extends Data {

    Map<Integer, List<Date>> mDataMap = new HashMap<>();

    public SolarTermData(String sheetName, int colId, int colDataStart, int rowDataStart) {
        ExcelHelper excelHelper = new ExcelHelper();
        XSSFSheet sheet = excelHelper.getSheet(sheetName);
        load(sheet, colId, colDataStart, rowDataStart);
        excelHelper.destroy();
    }

    public int get(Calendar calendar) {
        for (Map.Entry<Integer, List<Date>> entry : mDataMap.entrySet()) {
            int tempId = entry.getKey();
            List<Date> dateList = entry.getValue();
            for (Date date : dateList) {
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.setTime(date);
                if (Utils.isSameDay(calendar, tempCalendar)) {
                    return tempId;
                }
            }
        }
        return -1;
    }

    @Override
    protected String getSheetName() {
        return SolarTermDatabase.EXCEL_SHEET_NAME;
    }

    private void load(XSSFSheet sheet, int colId, int colDataStart, int rowDataStart) {
        int i;
        int j;
        int rows = sheet.getPhysicalNumberOfRows();
        for (i = rowDataStart; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            int cols = row.getPhysicalNumberOfCells();
            int id = ExcelHelper.getIntCellValue(row, colId);
            if (id < 0) {
                continue;
            }
            for (j = colDataStart; j < cols; j++) {
                Date date = ExcelHelper.getDateCellValue(row, j);
                List<Date> dateList;
                if (mDataMap.containsKey(id)) {
                    dateList = mDataMap.get(id);
                } else {
                    dateList = new ArrayList<>();
                    mDataMap.put(id, dateList);
                }
                dateList.add(date);
            }
        }
    }
}
