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
		frame.getContentPane().setName("");
		frame.setBounds(100, 100, 450, 405);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		PackagesUnziper MyUnziper = new PackagesUnziper();
		PackageFileReader MyPackageFileReader = new PackageFileReader();
		SQLiteProvider MySQL = new SQLiteProvider();
		JButton btnNewButton_1 = new JButton("Select Packages Structure File");
		
		JLabel lblNewLabel_1 = new JLabel("Packages Structure File:");
		lblNewLabel_1.setBounds(10, 309, 175, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 334, 175, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		btnNewButton_1.setBounds(196, 333, 228, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 287);
		frame.getContentPane().add(tabbedPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		tabbedPane.addTab("New tab", null, desktopPane, null);
		
		JLabel lblNewLabel = new JLabel("Folder With Edded Validator Zipped Packages:");
		lblNewLabel.setBounds(10, 11, 226, 14);
		desktopPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 389, 20);
		desktopPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Select Folder with Egged Validator Zipped Packages");
		btnNewButton.setBounds(10, 67, 389, 23);
		desktopPane.add(btnNewButton);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 170, 389, 14);
		desktopPane.add(progressBar);
		
		JLabel lblProgressStaus = new JLabel("New label");
		lblProgressStaus.setBounds(10, 145, 389, 14);
		desktopPane.add(lblProgressStaus);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Import to Exel");
		chckbxNewCheckBox.setBounds(10, 191, 153, 23);
		desktopPane.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Import to SQLite DB");
		chckbxNewCheckBox_1.setBounds(10, 217, 153, 23);
		desktopPane.add(chckbxNewCheckBox_1);
		chckbxNewCheckBox_1.setSelected(true);
		JButton btnNewButton_2 = new JButton("Import Data");
		btnNewButton_2.setBounds(229, 195, 115, 43);
		desktopPane.add(btnNewButton_2);
		btnNewButton_2.setEnabled(false);
		
		JDesktopPane desktopPane_1 = new JDesktopPane();
		tabbedPane.addTab("New tab", null, desktopPane_1, null);
		
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
		
	}
}
