package com.vhc.service.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.vhc.core.model.Giftcard;
import com.vhc.core.model.User;


public class ExcelProcessor {


	public List<Giftcard> read(String filename, User user) throws IOException, InvalidFormatException {
		return read(new File(filename), user);
	}


	public List<Giftcard> read(File file, User user) throws IOException, InvalidFormatException {
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
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }

        // 2. Or you can use a for-each loop
        System.out.println("Retrieving Sheets using for-each loop");
        for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
        }

        // 3. Or you can use a Java 8 forEach with lambda
        System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
        workbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });

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

			System.out.println("counter: "+counter+"minColIx: "+minColIx+", maxColIx: "+maxColIx);
			for(short colIx=minColIx; colIx<maxColIx; colIx++) {
				Cell cell = row.getCell(colIx);
				if(cell == null) {
					continue;
				}
				Giftcard card = new Giftcard();
				card.setCode(dataFormatter.formatCellValue(row.getCell(0)));
				card.setPin(dataFormatter.formatCellValue(row.getCell(1)));
				card.setAmount(new BigDecimal(row.getCell(2).getNumericCellValue()));
				card.setBalance(new BigDecimal(row.getCell(2).getNumericCellValue()));
				card.setLoaddate(Calendar.getInstance());
				card.setLoadedby(user);
				cards.add(card);

				//... do something with cell
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(cellValue + "\t" + card.getGiftcardid());
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
