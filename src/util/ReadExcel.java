package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) {
		try {
			ReadExcel obj = new ReadExcel();
			/*String st = "7875.05";
			String arr[] = st.split("\\.");
			if(Integer.parseInt(arr[1])==0){
				System.out.println("st :"+st+" 1st : "+arr[0]);
				System.out.println("INR " + obj.convert(Integer.parseInt(arr[0])) + " Rupess Only");
			
			}else {
				System.out.println("st :"+st+" 1st : "+arr[0]+" 2nd : "+arr[1]);
				System.out.println("INR " + obj.convert(Integer.parseInt(arr[0])) + " Rupess And "+obj.convert(Integer.parseInt(arr[1]))+" Paise Only");
				
			}*/
			
			obj.readXLSXFile("C:\\Users\\precise1\\Desktop\\uploadlog.xlsx");
			} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final String[] specialNames = { "", " Thousand", " Million",
			" Billion", " Trillion", " Quadrillion", " Quintillion" };

	private static final String[] tensNames = { "", " Ten", " Twenty",
			" Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty",
			" Ninety" };

	private static final String[] numNames = { "", " One", " Two", " Three",
			" Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten",
			" Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen",
			" Sixteen", " Seventeen", " Eighteen", " Nineteen" };

	private String convertLessThanOneThousand(int number) {
		String current;

		if (number % 100 < 20) {
			current = numNames[number % 100];
			number /= 100;
		} else {
			current = numNames[number % 10];
			number /= 10;

			current = tensNames[number % 10] + current;
			number /= 10;
		}
		if (number == 0)
			return current;
		return numNames[number] + " Hundred" + current;
	}

	public String convert(int number) {
		if (number == 0) {
			return "zero";
		}

		String prefix = "";

		if (number < 0) {
			number = -number;
			prefix = "Negative";
		}

		String current = "";
		int place = 0;

		do {
			int n = number % 1000;
			if (n != 0) {
				String s = convertLessThanOneThousand(n);
				current = s + specialNames[place] + current;
			}
			place++;
			number /= 1000;
		} while (number > 0);

		return (prefix + current).trim();
	}

	public void readXLSXFile(String inputFile){
		try 
		{
	        // Get the workbook instance for XLS file
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(inputFile));
	        // Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
	        Cell cell;
	        Row row;

	        // Iterate through each rows from first sheet
	        Iterator<Row> rowIterator = sheet.iterator();
        	while (rowIterator.hasNext()) {
                row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
	                
                while (cellIterator.hasNext()) {
	                cell = cellIterator.next();
	                System.out.println(cell);
	                switch (cell.getCellType())  {/*
		                case Cell.CELL_TYPE_BOOLEAN:
		                        System.out.println(cell.getBooleanCellValue());
		                        break;
		                case Cell.CELL_TYPE_NUMERIC:
		                        System.out.println(cell.getNumericCellValue());
		                        break;
		                
		                case Cell.CELL_TYPE_STRING:
		                        System.out.println(cell.getStringCellValue());
		                        break;
	
		                case Cell.CELL_TYPE_BLANK:
		                        System.out.println(" ");
		                        break;
		                default:
		                        System.out.println(cell);
	                */}
                }
	        }
		} 
	
		catch (FileNotFoundException e) 
		{
		        System.err.println("Exception" + e.getMessage());
		}
		catch (IOException e) 
		{
		        System.err.println("Exception" + e.getMessage());
		}
	}
	
}


