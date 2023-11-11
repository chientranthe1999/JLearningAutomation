package com.sat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static XSSFWorkbook work_book;
    private static XSSFSheet sheet;
    private static File s;
    private static FileInputStream stream;

    public ExcelUtils(String excelfilePath, String excelfileName) {
        try {
            String importFilePath = excelfilePath + excelfileName;
            String fileNameWithOutExt = FilenameUtils.removeExtension(excelfileName);
            String exportFilePath = excelfilePath + fileNameWithOutExt + "_Export.xlsx";
            copyExcel(importFilePath, exportFilePath);
            s = new File(exportFilePath);
            stream = new FileInputStream(s);
            work_book = new XSSFWorkbook(stream);
        }
            catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String getData(int sheetnumber, int row, int column){
        sheet = work_book.getSheetAt(sheetnumber);
        String data = sheet.getRow(row).getCell(column).getStringCellValue();
        return data;
    } 

    public int getRowCount(int sheetIndex){
        int row = work_book.getSheetAt(sheetIndex).getLastRowNum();
        row = row + 1;
        return row;
    }

    public int getRowByTCID(int sheetIndex, String tcID){
        Boolean found = false;
        sheet = work_book.getSheetAt(sheetIndex);
        int totalRows = sheet.getLastRowNum();
        Row row = null;
        int testRowNo = 0;
        for(int rowNo =1; rowNo<=totalRows; rowNo++)
        {
            row = sheet.getRow(rowNo);
            testRowNo = testRowNo +1;
            if(row.getCell(0).getStringCellValue().equalsIgnoreCase(tcID))
            {
                found = true;
                break;
            }
        }

        if (found){
            return testRowNo;
        } else {
            return 0;
        }
        
    }
    
    public void setCellData(String result, int sheetIndex, String tcID, int colNum)  {

        try { 

            //Read excel sheet by sheet index    
            sheet = work_book.getSheetAt(sheetIndex);

            //Get test row number
            int rowNum = this.getRowByTCID(sheetIndex, tcID);

            if (rowNum == 0){
                return;
            } 

            //Get the cell from the sheet
            Row xRow = sheet.getRow(rowNum);
            Cell xCell = xRow.getCell(colNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            if (xCell == null) {
                xCell = xRow.createCell(colNum);
                xCell.setCellValue(result);
            } else {
                xCell.setCellValue(result);
            }

            //Close input stream
            stream.close();

            //Create an object of FileOutputStream class to create write data in excel file
            FileOutputStream outputStream = new FileOutputStream(s);

            //write data in the excel file
            work_book.write(outputStream);

            //close output stream
            outputStream.close();
            
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }

    }

    public void copyExcel(String inputFilePath, String outputFilePath){
        try { 
            //Provide the Path of excel file which we want to copy
            File inputFile=new File(inputFilePath);
            FileInputStream fis=new FileInputStream(inputFile);
            XSSFWorkbook inputWorkbook=new XSSFWorkbook(fis);
            int inputSheetCount=inputWorkbook.getNumberOfSheets();
            System.out.println("Input sheetCount: "+inputSheetCount);
            
            // Provide the path of excel file in which we wanted to copy the data
            //String exportFilePath = "target/report/TestData_Export.xlsx";
            String exportFilePath = outputFilePath;
            File outputFile=new File(exportFilePath);
            if(outputFile.exists() && !outputFile.isDirectory()) {
                // exist -> skip
                return;
            }

            FileOutputStream fos=new FileOutputStream(outputFile); 
            // Creating workbook for output
            XSSFWorkbook outputWorkbook=new XSSFWorkbook();
            
        
            for(int i=0;i<inputSheetCount;i++) 
            { 
                XSSFSheet inputSheet=inputWorkbook.getSheetAt(i); 
                String inputSheetName=inputWorkbook.getSheetName(i); 
                XSSFSheet outputSheet=outputWorkbook.createSheet(inputSheetName); 

                // Create and call method to copy the sheet and content in new workbook. 
                copySheet(inputSheet,outputSheet); 
            }

            // Write all the sheets in the new Workbook(testData_Copy.xlsx) using FileOutStream Object
            outputWorkbook.write(fos); 
            // At the end of the Program close the FileOutputStream object. 
            fos.close(); 
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void copySheet(XSSFSheet inputSheet,XSSFSheet outputSheet) 
    { 
        int rowCount=inputSheet.getLastRowNum(); 
        System.out.println(rowCount+" rows in inputsheet "+inputSheet.getSheetName()); 
               
        int currentRowIndex=0; if(rowCount>0)
		{
			Iterator rowIterator=inputSheet.iterator();
			while(rowIterator.hasNext())
			{
				int currentCellIndex=0;
				Iterator cellIterator=((Row) rowIterator.next()).cellIterator();
				while(cellIterator.hasNext())
				{
				//  Creating new Row, Cell and Input value in the newly created sheet. 
					String cellData=cellIterator.next().toString();
					if(currentCellIndex==0)
						outputSheet.createRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
					else
						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
					
					currentCellIndex++;
				}
				currentRowIndex++;
			}
			System.out.println((currentRowIndex-1)+" rows added to outputsheet "+outputSheet.getSheetName());
			System.out.println();
		}
	}


}

