package MainWindowPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;


//import java.util.regex.Pattern;

public class PackageFileReader {
	
	private String strLineStopPackageFoler, strLineStopVersionFile, strPricesPackageFoler, strPricesVersionFile, CurrentFolder;
	private int LineStopPackageDataVer = 0;
	private int LineStopPackageDataStructure = 0;
	private int PricesPackageDataVer = 0;
	private int PricesPackageDataStructure = 0;
	public JLabel lblProgressStaus = new JLabel();

	
	List<List<String>> VersionFileRecords = new ArrayList<>();
	List<List<String>> TextFileRecords = new ArrayList<>();
	
	PackageFileReader() {
		this.strLineStopPackageFoler = null;
		this.strLineStopVersionFile = null;
		this.strPricesPackageFoler = null;
		this.strPricesVersionFile = null;
		
	}
	
	public void SetCurrentFolder(String CurrentFolder) {
		this.CurrentFolder = CurrentFolder;
	}
	
	public void SetPackageFolder(String strPackageFoler) {
		this.strLineStopPackageFoler = strPackageFoler + "\\LineStop";
		this.strLineStopVersionFile = this.strLineStopPackageFoler + "\\Version.txt";
		this.strPricesPackageFoler = strPackageFoler + "\\Prices";
		this.strPricesVersionFile = this.strPricesPackageFoler + "\\Version.txt";
	}
	
	public void SetlblProgressStaus(JLabel lblProgressStaus) {
		this.lblProgressStaus = lblProgressStaus;
	}
	
	private void PrintRecords(List<List<String>> records, int PrintMaxLines) {
		
		for (int i = 0; i < records.size(); i++) {
			if (i <= PrintMaxLines) {
				System.out.print("Line[" + i + "]: ");
				for (int j = 0; j < records.get(i).size(); j++)
				{
					System.out.print(records.get(i).get(j));
					if (j !=records.get(i).size()-1) {
						System.out.print(", ");
					}
	    		}
				System.out.println("");
			}
		}
	}

	
	private void ReadExtractedFile(String dataFile, String FileType) {
		
		try (
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataFile)), "UTF-16LE"))
			)
		{
		    String line;
		    int LineNumber = 1;
		    while ((line = br.readLine()) != null) {
		    	switch (FileType) {
		    	case "LineStopVersionFile" :
	    			String[] values = line.split(",",-1);
		    		if (LineNumber == 2) {
		    		this.LineStopPackageDataVer = Integer.parseInt(values[1].toString());	
		    		}
		    		if (LineNumber == 3) {
		    			this.LineStopPackageDataStructure = Integer.parseInt(values[1].toString());
		    		}
		    		if (LineNumber >= 4)
		    		{
		    			VersionFileRecords.add(Arrays.asList(values));
		    		}
		    		LineNumber++;
		    		break;
		    		
		    	case "LineStopTextFile":
		    		String[] values2 = line.split(",",-1);
	    			//if ((LineNumber >= 3) && (LineNumber < 65534))
	    			if (LineNumber >= 3)
		    		{
	    				TextFileRecords.add(Arrays.asList(values2));
		    		}
	    			LineNumber++;
		    		break;
		    		
		    	case "PricesVersionFile":
	    			String[] values3 = line.split(",",-1);
		    		if (LineNumber == 2) {
		    		this.PricesPackageDataVer = Integer.parseInt(values3[1].toString());
		    		}
		    		if (LineNumber == 3) {
		    			this.PricesPackageDataStructure = Integer.parseInt(values3[1].toString());
		    		}
		    		if (LineNumber >= 4)
		    		{
		    			VersionFileRecords.add(Arrays.asList(values3));
		    		}
		    		LineNumber++;
		    		break;
		    		
		    	case "PricesTextFileWithoutFooter":
		    		String[] values4 = line.split(",",-1);
	    			if (LineNumber >= 3)
		    		{
	    				TextFileRecords.add(Arrays.asList(values4));
		    		}
	    			LineNumber++;

		    		break;
		    		
		    	case "PricesTextFileWithFooter":
		    		String[] values5 = line.split(",",-1);
	    			if (LineNumber >= 3)
		    		{
	    				TextFileRecords.add(Arrays.asList(values5));
		    		}
	    			LineNumber++;
		    		break;

		    	case "PricesTxtOrNewFileWithFooter":
		    		String[] values6 = line.split(",",-1);
	    			if (LineNumber >= 3)
		    		{
	    				TextFileRecords.add(Arrays.asList(values6));
		    		}
	    			LineNumber++;
		    		break;
		    		
		    	}

		    	// Remove 2 last lines for files with footer
		    	
		    	
		    } // while ((line = br.readLine()) != null)
		    
		    
		    if ((FileType.equals("PricesTextFileWithFooter"))  || (FileType.equals("PricesTxtOrNewFileWithFooter")))
		    {
		    	boolean isProbablyTheLastJunkLine = false;
		    
		    	while (!isProbablyTheLastJunkLine) {
		    		if (TextFileRecords.size() > 0)
		    		{
		    		if (TextFileRecords.get(TextFileRecords.size()-1).size() != TextFileRecords.get(0).size())
		    			{
		    				TextFileRecords.remove(TextFileRecords.size() - 1);
		    			}
		    			else 
		    				{
		    					isProbablyTheLastJunkLine = true;
		    				}
		    		}
		    		else {
		    				isProbablyTheLastJunkLine = true;
		    			}
		    	}//while isisProbablyNotLastJunkLine 
		    } 
		} //try
		
    	catch (UnsupportedEncodingException e) 
    	{
    		System.out.println(e.getMessage());
    	} 
		catch (FileNotFoundException e) {
			System.out.println("PackageFileReader Class: FileNotFoundException: " + dataFile);
		} catch (IOException e) {
			System.out.println("PackageFileReader Class: IOException: " + dataFile);
		}
	} //ReadExtractedFile
	

	
	public int ReadAllExtractedFiles() {
		
		//LineStopVersionFile
		//LineStopTextFile
		//PricesVersionFile
		//PricesTextFileWithoutFooter 	Profiles.txt
		//PricesTextFileWithFooter 		AzmashMx.txt 	SContrac.txt
		//PricesTxtOrNewFileWithFooter 
		
		
		ExcelImporter MyExcelImporter = new ExcelImporter();

		
		lblProgressStaus.setText("Processing " + strLineStopVersionFile + "...");
		ReadExtractedFile(strLineStopVersionFile, "LineStopVersionFile");
		//System.out.println("LineStopPackageDataVer: " + LineStopPackageDataVer);
		//System.out.println("LineStopPackageDataStructure: " + LineStopPackageDataStructure);
		//PrintRecords(VersionFileRecords);
		
		//iterate through LineStop files from VersionFileRecords List<List<String>>
		for (int i = 0; i < VersionFileRecords.size(); i++) {
			
			String ExcelSheetName = VersionFileRecords.get(i).get(0);
			String FileName = strLineStopPackageFoler + "\\" + ExcelSheetName;
			
			ReadExtractedFile(FileName, "LineStopTextFile");
			
			//
			MyExcelImporter.ImportSheet(ExcelSheetName, TextFileRecords);
			
			//System.out.println(FileName);
			//System.out.println("Printing first 4 lines (or less)...");
			//PrintRecords(LineStopTextFileRecords, 4);
			//System.out.println("");
			TextFileRecords.clear();
			
		} // Iterate through LineStop files
		
		
		VersionFileRecords.clear();
		
		lblProgressStaus.setText("Processing " + strPricesVersionFile + "...");
		ReadExtractedFile(strPricesVersionFile, "PricesVersionFile");
//		System.out.println("PricesPackageDataVer: " + PricesPackageDataVer);
//		System.out.println("PricesPackageDataStructure: " + PricesPackageDataStructure);
		
		//PrintRecords(VersionFileRecords, 13);

		for (int i = 0; i < VersionFileRecords.size(); i++) {
			
			String ExcelSheetName = VersionFileRecords.get(i).get(0);
			String FileName = strPricesPackageFoler + "\\" + ExcelSheetName;
			
			if (ExcelSheetName.equals("Profiles.txt")) {
				//System.out.println("Currently working on ... " + ExcelSheetName + "This is PricesTextFileWithoutFooter");
				ReadExtractedFile(FileName, "PricesTextFileWithoutFooter");
			}
			else if ((ExcelSheetName.equals("AzmashMx.txt")) || (ExcelSheetName.equals("SContrac.txt"))) {
				//System.out.println("Currently working on ... " + ExcelSheetName + "This is PricesTextFileWithFooter");
				ReadExtractedFile(FileName, "PricesTextFileWithFooter");
			}
			else
			{
				//System.out.println("");
				//System.out.println("Currently working on ... " + ExcelSheetName);
				ReadExtractedFile(FileName, "PricesTxtOrNewFileWithFooter");
			}
			
			//
			MyExcelImporter.ImportSheet(ExcelSheetName, TextFileRecords);
			
			//System.out.println(FileName);
			//System.out.println("Printing first 800 lines (or less)...");
			//PrintRecords(TextFileRecords, 800);
			//System.out.println("");
			TextFileRecords.clear();
			
		} // Iterate through LineStop files
		
		
		
		
		
		//
		if (MyExcelImporter.WriteExcelFile(CurrentFolder) == false)
			return 2;
			else return 0;
		
	}// ReadAllExtractedFiles
	
	

	
} //Class
