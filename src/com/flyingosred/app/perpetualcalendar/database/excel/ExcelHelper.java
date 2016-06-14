/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelHelper {

    private Workbook mWorkbook = null;

    public ExcelHelper() {
        init();
    }

    public void destroy() {
        if (mWorkbook != null) {
            try {
                mWorkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public XSSFSheet getSheet(String name) {
        XSSFSheet sheet = null;
        if (mWorkbook != null) {
            sheet = (XSSFSheet) mWorkbook.getSheet(name);
        }
        return sheet;
    }

    public List<XSSFSheet> getSheets(String pattern) {
        List<XSSFSheet> list = new ArrayList<>();
        Iterator<Sheet> iterator = mWorkbook.sheetIterator();
        while (iterator.hasNext()) {
            Sheet sheet = iterator.next();
            if (sheet.getSheetName().contains(pattern)) {
                list.add((XSSFSheet) sheet);
            }
        }
        return list;
    }

    public static int getIntCellValue(XSSFRow row, int cellNumber) {
        XSSFCell cell = row.getCell(cellNumber);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return -1;
    }

    public static String getStringCellValue(XSSFRow row, int cellNumber) {
        XSSFCell cell = row.getCell(cellNumber);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        }
        return null;
    }
    
    public static String getCellFormula(XSSFRow row, int cellNumber) {
        XSSFCell cell = row.getCell(cellNumber);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        }
        return null;
    }

    public static Date getDateCellValue(XSSFRow row, int cellNumber) {
        XSSFCell cell = row.getCell(cellNumber);
        if (cell != null && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        }
        return null;
    }

    private void init() {
        File currentDirFile = new File(".");
        File dbFile = new File(currentDirFile, "database/database.xlsx");
        try {
            mWorkbook = new XSSFWorkbook(dbFile);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
