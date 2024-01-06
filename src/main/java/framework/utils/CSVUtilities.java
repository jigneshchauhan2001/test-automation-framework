package framework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import net.bytebuddy.asm.Advice.Return;

public class CSVUtilities {

	/**
	 * @description : this method returns the row count of given csv file
	 * @param csvFileName
	 * @throws FileNotFoundException, IOException
	 * @return int row count size
	 */
	public static int getRowCountOfCSV(String csvFileName) {
		int rowCount=0;
		CSVReader csvReader;
		try {
			csvReader=new CSVReader(new FileReader(new File(csvFileName)));
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found at given location: "+csvFileName);
		}
		try {
			rowCount=csvReader.readAll().size();
		} catch (CsvException | IOException e) {
			throw new RuntimeException("Error occured during CSV reading: "+e.getMessage());
		}
		try {
			csvReader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error occured during closing CSV file: "+e.getMessage());
		}
		return rowCount;
	}

	
	/**
	 *@desc this method reads data from given CSVfile of given column column number and returns String[]
	 * @param columnNumber
	 * @param csvFileName
	 * @return String[]
	 */
	public static String[] readDataFromCSVFileForGivenColumnNumber(int columnNumber, String csvFileName) {
		File inputFile=new File(csvFileName);
		//Read exisitng file
		CSVReader reader;
		String[] columnData = null;
		
		try {
			reader = new CSVReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found at given location: "+csvFileName);
		}
		List<String[]> csvBody;
		try {
			csvBody=reader.readAll();
			columnData=new String[csvBody.size()];
			for (int i = 0; i < columnData.length; i++) {
				columnData[i]=csvBody.get(i)[columnNumber];
			}
		} catch (IOException|CsvException e){
			throw new RuntimeException("Error occured during CSV reading: "+e.getMessage());
		}
		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error occured during closing CSV file: "+e.getMessage());
		}
		return columnData;
	}
	
	/**
	 * @desc this method reads data from given CSVfile and returns List<String[]> where each String[] represents row values 
	 * @param csvFileName
	 * @return List<String[]>
	 */
	public static List<String[]> readDataFromCSVFile(String csvFileName){
		File inputFile=new File(csvFileName);
		//Read existing file
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found at given location: "+csvFileName);
		}
		List<String[]> csvBody;
		try {
			csvBody=reader.readAll();
		}
		catch (IOException |CsvException e) {
			throw new RuntimeException("Error occured during CSV reading: "+e.getMessage());
		}
		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error occured during closing CSV file: "+e.getMessage());
		}
		return csvBody;	
	}
	
	
	/**
	 * @desc this method writes String[] data array in given column number in given CSV file
	 * @param int columnNumber
	 * @param String[] array
	 * @param String csvFileName
	 */
	public static void writeStringArrayDataToCSVColumn(int columnNumber,String[] array, String csvFileName) {
		File inputFile=new File(csvFileName);
		// read existing file
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found at given location: "+csvFileName);
		}
		List<String[]> csvBody;
		try {
			csvBody=reader.readAll();
		}
		catch (IOException |CsvException e) {
			throw new RuntimeException("Error occured during CSV reading: "+e.getMessage());
		}
		for (int i = 0; i < csvBody.size(); i++) {
			csvBody.get(i)[1]=array[i];
		}
		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error occured during closing CSV file: "+e.getMessage());
		}
		CSVWriter writer=null;
		try {
			writer=new CSVWriter(new FileWriter(inputFile));
			writer.writeAll(csvBody);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Error occured during CSV writing: "+e.getMessage());
		}
	}
	
	
	
}
