package MainWindowPackage;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
//import java.awt.*;
//import java.util.*;
import java.io.File;


public class MainWindowClass {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	JFileChooser PackagesFolderChooser;
	JFileChooser StructureFileChooser;
	File fPricesPackage;
	File fLineStopsPackage;
	File PackageStructure;
	boolean isPackageFolderSelected = false;
	boolean isPackageStructureFileSelected = false;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindowClass window = new MainWindowClass();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindowClass() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 405);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 40, 414, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		PackagesUnziper MyUnziper = new PackagesUnziper();
		PackageFileReader MyPackageFileReader = new PackageFileReader();
		SQLiteProvider MySQL = new SQLiteProvider();
		
		JButton btnNewButton = new JButton("Select Folder with Egged Validator Zipped Packages");
		JButton btnNewButton_1 = new JButton("Select Packages Structure File");
		JButton btnNewButton_2 = new JButton("Import Data");
		btnNewButton_2.setEnabled(false);

		btnNewButton_2.setBounds(153, 313, 115, 43);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Folder With Edded Validator Zipped Packages:");
		lblNewLabel.setBounds(10, 15, 265, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Packages Structure File:");
		lblNewLabel_1.setBounds(10, 127, 175, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnNewButton.setBounds(10, 71, 414, 23);
		frame.getContentPane().add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 152, 414, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		btnNewButton_1.setBounds(10, 183, 414, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Import to Exel");
		chckbxNewCheckBox.setBounds(62, 283, 123, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Import to SQLite DB");
		chckbxNewCheckBox_1.setSelected(true);
		chckbxNewCheckBox_1.setBounds(239, 283, 153, 23);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 217, 414, 14);
		frame.getContentPane().add(progressBar);
		
		JLabel lblProgressStaus = new JLabel("New label");
		lblProgressStaus.setBounds(10, 242, 414, 14);
		frame.getContentPane().add(lblProgressStaus);

		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String FolderChooserTitle = "Select the Folder with the Egged Validator Zipped Packages...";
				PackagesFolderChooser = new JFileChooser();
				PackagesFolderChooser.setCurrentDirectory(new java.io.File("."));
				PackagesFolderChooser.setDialogTitle(FolderChooserTitle);
				PackagesFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				PackagesFolderChooser.setAcceptAllFileFilterUsed(false);
				
				if (PackagesFolderChooser.showOpenDialog(PackagesFolderChooser) == JFileChooser.APPROVE_OPTION) {
					//System.out.println("getCurrentDirectory(): " +  PackagesFolderChooser.getCurrentDirectory());
					//System.out.println("getSelectedFile() : " +  PackagesFolderChooser.getSelectedFile());
					
					String SelectedFolder = PackagesFolderChooser.getSelectedFile().toString();
					File fPricesPackage = new File(PackagesFolderChooser.getSelectedFile().toString()+"\\Prices.zip");
					File fLineStopsPackage = new File(PackagesFolderChooser.getSelectedFile().toString()+"\\LineStop.zip");
					
					//Prices & LineStop Packages exist in the Chosen Folder....
					if ((fPricesPackage.exists() && fPricesPackage.isFile()) && (fLineStopsPackage.exists() && fLineStopsPackage.isFile()))
					{
						textField.setText(PackagesFolderChooser.getSelectedFile().toString());
						MyUnziper.PricePackageSetter(fPricesPackage);
						MyUnziper.LineStopsPackageSetter(fLineStopsPackage);
						MyUnziper.PackagesFolderSetter(SelectedFolder);
						
						MyPackageFileReader.SetPackageFolder(SelectedFolder);
						MyPackageFileReader.SetCurrentFolder(SelectedFolder);
						MyPackageFileReader.SetlblProgressStaus(lblProgressStaus);
						
						MySQL.DbConnect(SelectedFolder);
						
						
						isPackageFolderSelected = true;
						//if (isPackageStructureFileSelected == true) {
							btnNewButton_2.setEnabled(true);
						//}
					}
						//Packages do not exist in the Chosen Folder...
						else {
							isPackageFolderSelected = false;
							btnNewButton_2.setEnabled(false);
							textField.setText("");
							JOptionPane.showMessageDialog(null, "The Selected Folder Doesn't Contain Prices.zip And LineStop.zip...\nChoose Another Folder!");
						}
					}
					//No Folder was Selected...
					else {
						isPackageFolderSelected = false;
						textField.setText("");
						btnNewButton_2.setEnabled(false);
						System.out.println("No Packages Folder Selection ");
					}
			}
		});
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String StructureFileChooserTitle = "Select the File WIth the Packages Structure...";
				StructureFileChooser = new JFileChooser();
				StructureFileChooser.setCurrentDirectory(new java.io.File("."));
				StructureFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("json File", "json"));
				StructureFileChooser.setDialogTitle(StructureFileChooserTitle);
				StructureFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				StructureFileChooser.setAcceptAllFileFilterUsed(false);
				
				if (StructureFileChooser.showOpenDialog(StructureFileChooser) == JFileChooser.APPROVE_OPTION) {
					//System.out.println("getCurrentDirectory(): " +  StructureFileChooser.getCurrentDirectory());
					//System.out.println("getSelectedFile() : " +  StructureFileChooser.getSelectedFile());
					
					//File fPackageStructure = new File(StructureFileChooser.getSelectedFile().toString());
					//System.out.println("File is: " + fPackageStructure.toString());
					textField_1.setText(StructureFileChooser.getSelectedFile().toString());
					isPackageStructureFileSelected = true;
					if (isPackageFolderSelected == true) {
							btnNewButton_2.setEnabled(true);
						}
					}
					//The Packages Structure File was Selected...
					else {
						//isPackageStructureFileSelected = false;
						//textField_1.setText("");
						//btnNewButton_2.setEnabled(false);
						//System.out.println("No Packages File Selection");
					}
			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (MyUnziper.UnzipPricesPackage() == true) {
					//System.out.println("MyUnziper.UnzipPricesPackage Unzip Process Succeeded...");
				}
				else {
					System.out.println("MyUnziper.UnzipPricesPackage Unzip Process Failed...");
				}
			
				if (MyUnziper.UnzipLineStopsPackage() == true) {
					//System.out.println("MyUnziper.UnzipLineStopsPackage Unzip Process Succeeded...");
				}
				else {
					System.out.println("MyUnziper.UnzipLineStopPackage Process Failed...");
				}
				
				
				
				//ExcelImporter MyExcelImporter = new ExcelImporter();
				//MyExcelImporter.WriteIntoExcelExample();
				if (chckbxNewCheckBox.isSelected())
					if (MyPackageFileReader.ReadAllExtractedFiles() != 0) {
						JOptionPane.showMessageDialog(null, "Error creating Excel file...");
					}
				
				if (chckbxNewCheckBox_1.isSelected())
				{
					System.out.println ("MainWindowClass: btnNewButton_2: SQLite executed...");
				}
					
				
				
				
			} //ActionEvent
		}); //btnNewButton_2.addActionListener
		
	}
}
