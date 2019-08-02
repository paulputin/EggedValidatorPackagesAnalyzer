package MainWindowPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelImporter {
	
	private static final int WIDTH_ARROW_BUTTON = 340;

    Workbook workbook = new XSSFWorkbook();
    
	public void ImportSheet (String SheetName, List<List<String>> records) {
		System.out.println("ExcelImporter: ImportSheet: " + SheetName);
		XSSFSheet sheet = (XSSFSheet) workbook.createSheet(SheetName);
        Map<String, ArrayList<Object>> data = new LinkedHashMap<String, ArrayList<Object>>();
		
		//Iterate through records
		for (Integer i = 0; i < records.size(); i++) {
			ArrayList<Object> arrList = new ArrayList<Object>();
			for (int j = 0; j < records.get(i).size(); j++) {
				arrList.add(records.get(i).get(j));
			}
			data.put(i.toString(), arrList);
		} //Iterate through records
		

		XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("0"));

		
        Set<String> keyset = data.keySet();
        int rownum = 0;

        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            ArrayList<Object> ListArr2 = data.get(key);
            int cellnum = 0;

            for (Object obj : ListArr2) {
            	Cell cell = row.createCell(cellnum++);
            	try {
            			Integer i = Integer.parseInt((String) obj);
            			cell.setCellValue(i);
            	}
            	catch (Exception e) {
            		try {
            			Double d = Double.parseDouble((String) obj);
            			cell.setCellValue(d);
            			//if StartDate or EndDate cell
            			if ((d > 200000000000.0) && (d < 290000000000.0)) {
            				cell.setCellStyle(style);
            			}
            		}
            		catch (Exception e1) {
	                    if (obj instanceof Integer) {
	                        cell.setCellValue((Integer) obj);
	                    } else if (obj instanceof Boolean) {
	                        cell.setCellValue((Boolean) obj);
	                    } else if (obj instanceof String) {
	                        cell.setCellValue((String) obj);
	                    } else if (obj instanceof Double) {
	                        cell.setCellValue((Double) obj);
	                    } else if (obj instanceof Date) {
	                        cell.setCellValue((Date) obj);
	                    }
            		} //catch 2
        		} //catch 1
            } //for
        }
        

        // Add auto filter and freeze panes to the first row 
        if (sheet.getPhysicalNumberOfRows() > 0) {
        	int numColumns = sheet.getRow(0).getPhysicalNumberOfCells();
        	sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, numColumns-1));
        	sheet.createFreezePane(0, 1);
        }

	} // ImportSheet
	
	public boolean WriteExcelFile(String ExcelFolder) {
		
		autoSizeColumns(workbook);
		
        try {
            FileOutputStream out
                    = new FileOutputStream(new File(ExcelFolder + "\\ValidatorPackages.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        } catch (FileNotFoundException e) {
        	return false;
            //e.printStackTrace();
        } catch (IOException e) {
        	return false;
            //e.printStackTrace();
        }
        
        try {
			workbook.close();
		} catch (IOException e) {
			return false;
			//e.printStackTrace();
		}
        return true;
	}
	
	
	private void autoSizeColumns(Workbook workbook) {
	    int numberOfSheets = workbook.getNumberOfSheets();
	    for (int i = 0; i < numberOfSheets; i++) {
	    	XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(i);
	    	System.out.println ("ExcelImporter: autoSizeColumns: Working on Sheet [" + sheet.getSheetName() + "]");
	        if (sheet.getPhysicalNumberOfRows() > 0) {
	            Row row = sheet.getRow(sheet.getFirstRowNum());
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                int columnIndex = cell.getColumnIndex();
	                
	                DataFormatter formatter = new DataFormatter();
	                String val = formatter.formatCellValue(sheet.getRow(row.getRowNum()).getCell(columnIndex));
	                
	                System.out.println ("ExcelImporter: autoSizeColumns: Working on Sheet [" + sheet.getSheetName() 
	                					+ "], Row [" + row.getRowNum() + "], Cell [" + val + "]");
	                sheet.autoSizeColumn(columnIndex);
	                sheet.setColumnWidth(columnIndex, sheet.getColumnWidth(columnIndex) + WIDTH_ARROW_BUTTON);
	            }
	        }
	    }
	} //autoSizeColumns
	
} //Class


//	TODO
// 	Show progress in the main windows: Labels or progress bar or both.
