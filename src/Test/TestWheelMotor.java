package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import Moteur.Pinces;
import Moteur.WheelMotor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.internal.io.NativeDevice;
import lejos.robotics.Color;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import perception.Sensor;

/**
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MovePilot.*;
*/
public class TestWheelMotor {
	
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
	
	//---------------------------
	
	private static NativeDevice dev = new NativeDevice("/dev/lms_sound");
	
	static void playFreq(int aFrequency, int aDuration, int aVolume)
    {
        //System.out.println("Volume " + aVolume);
        byte[] cmd = new byte[6];
        cmd[0] = 1;
        cmd[1] = (byte) aVolume;
        cmd[2] = (byte) aFrequency;
        cmd[3] = (byte) (aFrequency >> 8);
        cmd[4] = (byte) aDuration;
        cmd[5] = (byte) (aDuration >> 8);
        dev.write(cmd, cmd.length);        
    }
	
	
	public static void playTone(int aFrequency, int aDuration, int aVolume)
    {
        if (aVolume >= 0)
            aVolume = (aVolume*100)/100;
        else
            aVolume = -aVolume;
        playFreq(aFrequency, aDuration, aVolume);
    }

	public static void main(String[] args) {
		
		
		//WheelMotor m = new WheelMotor(1);


		//m.rotate(338);
		//m.setLargeur(1000);
		//m.setLongueur(300);
		
	//	m.goTo(500,2700);
	//	Delay.msDelay(5000);
		//Pinces p = new Pinces();
		//p.fermer();
		
		
		/**
		m.afficheLargeur();
		m.afficheLongueur();
		m.afficheBoussole();
		Delay.msDelay(3000);
		m.rotate(45);
		m.forward(500);
		m.afficheLargeur();
		m.afficheLongueur();
		m.afficheBoussole();
		Delay.msDelay(10000);
		*/
		
		
		
		/*
		File file = new File("test.txt");
		deleteFile(file, true);
		
		createAndWriteFile(file, "123456", true);
		
		String res = chargeFile(file);
		System.out.println("\nRes: "+res);
		
		replaceContent(file, "3456");
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
		*/
		
		/*
		playTone(100, 100, 100);
		Delay.msDelay(100);
		playTone(2000, 100, 100);
		Delay.msDelay(100);
		playTone(10000, 100, 100);
		Delay.msDelay(100);
		*/
		/*
		for(int i=100;i<1500;i=i+100) {
			playTone(i, 10, 100);
			Delay.msDelay(10);
		}
		
		for(int i=1500;i>0;i=i-100) {
			playTone(i, 10, 100);
			Delay.msDelay(10);
		}
		
		for(int i=100;i<2000;i=i+100) {
			playTone(i, 10, 100);
			Delay.msDelay(10);
		}
		*/
		
		
		playTone(800, 20, 100);
		Delay.msDelay(100);
		playTone(1200, 10, 100);
		Delay.msDelay(100);
		playTone(800, 20, 100);
		Delay.msDelay(100);
		playTone(800, 20, 100);
		Delay.msDelay(100);
		playTone(800, 20, 100);
		Delay.msDelay(100);
		playTone(5200, 10, 100);
		Delay.msDelay(100);
		
		
		
		}

	}