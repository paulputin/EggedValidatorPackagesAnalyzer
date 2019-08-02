package MainWindowPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class PackagesUnziper {
	
	private File fPricesPackage, fLineStopPackage;
	String PackagesFolder;
	

	PackagesUnziper() {
		this.fPricesPackage = null;
		this.fLineStopPackage = null;
		this.PackagesFolder = null;
	}
	
	public void PricePackageSetter (File fPricesPackage) {
		this.fPricesPackage = fPricesPackage;
	}
	
	public void LineStopsPackageSetter (File fLineStopsPackage) {
		this.fLineStopPackage = fLineStopsPackage;
	}

	public void PackagesFolderSetter (String PackagesFolder) {
		this.PackagesFolder = PackagesFolder;
	}

	
	
	public boolean UnzipPricesPackage() {
		
		if (fPricesPackage != null) {
			String PricesFolder = PackagesFolder  + "\\Prices";
			new File(PricesFolder).mkdirs();
			try {
				Unzip(fPricesPackage.toString(), PricesFolder);
			} catch (IOException e) {
				System.out.println("PackagesUnziper class: Unzipping Prices Exception: \nPricesFolder: " + PricesFolder + "\nPricesPackage: " + fPricesPackage.toString() + "\n");
				//e.printStackTrace();
			}
			//System.out.println("PackagesUnziper class: Unzipping file: " + fPricesPackage.toString());
			return true;
		}
		else {
			System.out.println("PackagesUnziper class: fPricesPackage is NULL, cannot proceed...");
			return false;
		}
		
	}
	
	public boolean UnzipLineStopsPackage() {
		
		if (fLineStopPackage != null) {
			String LineStopFolder = PackagesFolder  + "\\LineStop";
			new File(LineStopFolder).mkdirs();
			try {
				Unzip(fLineStopPackage.toString(), LineStopFolder);
			} catch (IOException e) {
				System.out.println("PackagesUnziper class: Unzipping LineStops Exception: \nPricesFolder: " + LineStopFolder + "\nLineStopPackage: " + fLineStopPackage.toString() + "\n");
				//e.printStackTrace();
			}
			//System.out.println("PackagesUnziper class: Unzipping file: " + fLineStopPackage.toString());
			return true;
		}
		else {
			System.out.println("PackagesUnziper class: PackagesUnziper class: fLineStopsPackage is NULL, cannot proceed...");
			return false;
		}
	}
	
	
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("PackagesUnziper class: Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
	
    public static void Unzip (String strZipFile, String strDestDir) throws IOException {
	    String fileZip = strZipFile;
	    File destDir = new File(strDestDir);
	    byte[] buffer = new byte[1024];
	    ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
	    ZipEntry zipEntry = zis.getNextEntry();
	    while (zipEntry != null) {
	        File newFile = newFile(destDir, zipEntry);
	        FileOutputStream fos = new FileOutputStream(newFile);
	        int len;
	        while ((len = zis.read(buffer)) > 0) {
	            fos.write(buffer, 0, len);
	        }
	        fos.close();
	        zipEntry = zis.getNextEntry();
	    }
	    zis.closeEntry();
	    zis.close();
    } //Unzip
    
} //class
