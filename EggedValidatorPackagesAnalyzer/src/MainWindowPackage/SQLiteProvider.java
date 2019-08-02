package MainWindowPackage;

import java.sql.*;
import javax.swing.*;



public class SQLiteProvider {
	
	private Connection conn;
	
	public Connection DbConnect (String CurrentFolder) {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println ("SQLiteProvider: DbConnect: " + CurrentFolder + "\\sqlite.db");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + CurrentFolder + "\\sqlite.db");
			return conn;
		}catch (Exception e) {
			System.out.println("SQLiteProvider: DbConnect: " + e);
			return null;
		}
	} //DbConnect

}





//  TODO
//
//  1. Create tables according to FileNames, ColumnNames and Column Types;
//  2. Populate tables from Extracted files;
//  3. Create Stored Procedures
//  4. 