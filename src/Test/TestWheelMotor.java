package Test;


import java.util.Scanner;
import Moteur.WheelMotor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import perception.Sensor;

/**
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MovePilot.*;
*/
public class TestWheelMotor {

	public static void main(String[] args) {
		
		/**
		
		EV3LargeRegulatedMotor B = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor C = new EV3LargeRegulatedMotor(MotorPort.C);
		
		B.rotate(720,true);
		C.rotate(720,false);
			
*/
		
		WheelMotor m1 = new WheelMotor();
		m1.backward(200);

			
			
		
			
		

		/*

			Scanner sc = new Scanner(System.in);
			System.out.println("1, 2 ou 3");
			int num = sc.nextInt();
			sc.close();
			WheelMotor m = new WheelMotor();
		*/
			/**
			int i = 0;
			while(i<4000 && Button.ENTER.isDown()==false){//4000 40sec
				Delay.msDelay(10);
				i++;
			}
		   	Delay.msDelay(10);
		    System.out.println("Salut");

		    
		    

		    Delay.msDelay(10000);
		    */

	}
		

	
}