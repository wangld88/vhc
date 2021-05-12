package com.vhc.service.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vhc.core.model.Countlog;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;


public class ExcelProcessor {

	private Logger logger = LoggerFactory.getLogger(ExcelProcessor.class);

	public List<Giftcard> read(String filename, User user)
		throws IOException, InvalidFormatException {
		return read(new File(filename), user, null);
	}


	public List<Giftcard> read(String filename, User user, List<Store> stores)
		throws IOException, InvalidFormatException {
		return read(new File(filename), user, stores);
	}


	public List<Giftcard> read(File file, User user, List<Store> stores)
		throws IOException, InvalidFormatException {
		// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(file);

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        // 1. You can obtain a sheetIterator and iterate over it
        /*Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }*/

        // 2. Or you can use a for-each loop
        /*System.out.println("Retrieving Sheets using for-each loop");
        for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
        }*/

        // 3. Or you can use a Java 8 forEach with lambda
        /*System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
        workbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });*/

        /*
           ==================================================================
           Iterating over all the rows and columns in a Sheet (Multiple ways)
           ==================================================================
        */

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        /*System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Now let's iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();
        }*/

        // 2. Or you can use a for-each loop to iterate over the rows and columns
        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
        int counter = 0;

        List<Giftcard> cards = new ArrayList<>();

        for (Row row: sheet) {
        	counter++; //Skip header

			if(counter == 1) {
				continue;
			}

        	short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();

			System.out.println("counter: "+counter+", minColIx: "+minColIx+", maxColIx: "+maxColIx);
			for(short colIx=minColIx; colIx<maxColIx; colIx++) {
				Cell cell = row.getCell(colIx);
				if(cell == null) {
					continue;
				}

				//... do something with cell
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(colIx+": "+cellValue + "\t" );
			}

			if(maxColIx == 4 && row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
				System.out.println("The rowIndex " + row.getRowNum()
					+ " does not have the right value: "
					+ ", 0: " + row.getCell(0).getCellTypeEnum()
					+ ", 1: " + row.getCell(1).getCellTypeEnum()
					+ ", 2: " + row.getCell(2).getCellTypeEnum()
					+ ", 3: " + row.getCell(3).getCellTypeEnum());
			//if(row.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
				Giftcard card = new Giftcard();
				card.setCode(dataFormatter.formatCellValue(row.getCell(0)));
				card.setPin(dataFormatter.formatCellValue(row.getCell(1)));
				card.setAmount(new BigDecimal(row.getCell(2).getNumericCellValue()));
				card.setBalance(new BigDecimal(row.getCell(2).getNumericCellValue()));
				if(stores != null && row.getCell(3) != null) {
					Store store = stores.stream()
							.filter(s -> s.getStoreid() == Long.parseLong(dataFormatter.formatCellValue(row.getCell(3))))
							.findFirst()
							.orElse(null);
					if(store != null) {
						card.setStore(store);
					} else {
						logger.error("Given store can not be found.");
					}
				} else {
					logger.error("The store list is empty");
				}
				card.setLoaddate(Calendar.getInstance());
				card.setLoadedby(user);
				cards.add(card);
				//System.out.println(card.getGiftcardid());
			} else {
				System.out.println("The rowIndex " + row.getRowNum() + " does not have the right value: ");
			}
            /*for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();*/
        }

        // 3. Or you can use Java 8 forEach loop with lambda
        /*System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
        sheet.forEach(row -> {
            row.forEach(cell -> {
            	printCellValue(cell);
            });
            System.out.println();
        });*/

        // Closing the workbook
        workbook.close();

        return cards;
	}


	public List<String> readInventory(String filename)
			throws IOException, InvalidFormatException {

		File file = new File(filename);
		List<String> logs = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file);

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter dataFormatter = new DataFormatter();
        int counter = 0;

        for (Row row: sheet) {
        	counter++; //Skip header

			if(counter == 1) {
				continue;
			}

        	short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();

			System.out.println("counter: "+counter+", minColIx: "+minColIx+", maxColIx: "+maxColIx);
			for(short colIx=minColIx; colIx<maxColIx; colIx++) {

				Cell cell = row.getCell(colIx);
				if(cell == null) {
					continue;
				}

				//... do something with cell
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(colIx+": "+cellValue + "\t" );
			}

			if(maxColIx == 1 && row.getCell(0) != null) {

				logs.add(dataFormatter.formatCellValue(row.getCell(0)));

			} else {
				System.out.println("The rowIndex " + row.getRowNum() + " does not have the right value: ");
			}
        }

        workbook.close();

		return logs;

	}

	private static void printCellValue(Cell cell) {
	    switch (cell.getCellTypeEnum()) {
	        case BOOLEAN:
	            System.out.print(cell.getBooleanCellValue());
	            break;
	        case STRING:
	            System.out.print(cell.getRichStringCellValue().getString());
	            break;
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                System.out.print(cell.getDateCellValue());
	            } else {
	                System.out.print(cell.getNumericCellValue());
	            }
	            break;
	        case FORMULA:
	            System.out.print(cell.getCellFormula());
	            break;
	        case BLANK:
	            System.out.print("");
	            break;
	        default:
	            System.out.print("");
	    }

	    System.out.print("\t");
	}
}
