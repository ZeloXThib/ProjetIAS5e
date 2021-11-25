package Robot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.utility.Delay;

public class FileTools {
	
	
	public static void deleteFile(File file, boolean printing) {
		try  
		{
			if(file.delete())                      //returns Boolean value  
			{  
				if(printing)
					System.out.println("\n"+file.getName() + " deleted..");   //getting and printing the file name  
			}  
			else  
			{  if(printing)
					System.out.println("\nfailed");  
			}  
		}  
		catch(Exception e)  
		{  
			e.printStackTrace();  
		}  
	}
	
	
	
	public static String chargeFile(File file) {
		String resultat ="";
		try  
		{  
			//constructor of file class having file as argument 
			FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file 
			int r=0;  
			
			while((r=fis.read())!=-1)  
			{  
				resultat +=((char)r);      //prints the content of the file  
			}  
			//System.out.println("/n"+resultat);
			
			fis.close();
		}  
		catch(Exception e)  
		{  
			e.printStackTrace();
			resultat ="123456";
		}  
		
		return resultat;
		
	}
	
	
	public static boolean replaceContent(File file, String content) {
		deleteFile(file, false);
		createAndWriteFile(file, content, false);
		return true;
		
	}
	
	
	public static void createAndWriteFile(File file, String content, boolean printing) {
		boolean result;
		try   
		{  
			result = file.createNewFile();  //creates a new file  
			if(result)      // test if successfully created a new file  
			{  
				if(printing)
					System.out.println("\nCreate file: "+file.getCanonicalPath()); //returns the path string
				
				//write in file
				try  
				{  
					FileOutputStream fos=new FileOutputStream(file, true);  // true for append mode
					String str=content;      //str stores the string which we have entered  
					byte[] b= str.getBytes();       //converts string into bytes  
					fos.write(b);           //writes bytes into file  
					fos.close();            //close the file  
					if(printing)
						System.out.println("\nfile saved/init with: "+content);
				} 
				catch(Exception e)  
				{  
					e.printStackTrace();          
				}
				
				
				
				
				
			}  
			else  
			{  
				if(printing)
					System.out.println("\nFile already exist: "+file.getCanonicalPath());  
				
			}  
		}   
		catch (IOException e)   
		{  
			e.printStackTrace();    //prints exception if any  
		} 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File("FileSavePointScan.txt");
		//deleteFile(file, true);
		//createAndWriteFile(file, "123456", true);
		
		String res = chargeFile(file);
		System.out.println("\nRes: "+res);
		
		replaceContent(file, "456");
		res = chargeFile(file);
		System.out.println("\nRes after change:"+res);
		int check=0;
		while(check==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				check=1;
				Delay.msDelay(10);
			}
		}
		Delay.msDelay(1000);

	}

}
