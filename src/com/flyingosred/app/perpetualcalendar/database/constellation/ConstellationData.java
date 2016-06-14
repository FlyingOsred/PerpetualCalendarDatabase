/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.constellation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class ConstellationData extends Data {

    private final List<ConstellationDataItem> mDataList = new ArrayList<>();

    public ConstellationData(XSSFSheet sheet) {
        super(sheet);
        parse(sheet);
    }

    @Override
    public int getId(Calendar calendar) {
        for (ConstellationDataItem item : mDataList) {
            if (item.match(calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))) {
                return item.getId();
            }
        }
        throw new RuntimeException("Not be able to find constellation for " + calendar.getTime());
    }

    private void parse(XSSFSheet sheet) {
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = EXCEL_ROW_DATA_START; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_ID);
            if (id < 0) {
                continue;
            }
            int col = EXCEL_COL_DATA_START_WITH_SOURCE;
            Date startDate = ExcelHelper.getDateCellValue(row, col);
            Date endDate = ExcelHelper.getDateCellValue(row, col + 1);
            if (startDate != null && endDate != null) {
                ConstellationDataItem item = new ConstellationDataItem(id, startDate, endDate);
                mDataList.add(item);
            }
        }
    }

}
