package Test;


import java.util.Scanner;

import Robot.Sensor;
import Robot.WheelMotor;
import lejos.hardware.Button;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class TestWheelMotor {

	public static void main(String[] args) {
			WheelMotor m = new WheelMotor();
			System.out.print("Hello ta mere");
			m.rotate(400);
			
			
		
			/**
		
			Scanner sc = new Scanner(System.in);
			System.out.println("1, 2 ou 3");
			int num = sc.nextInt();
			sc.close();
			System.out.println(num);
			WheelMotor m = new WheelMotor();
		
			int i = 0;
			while(i<5000){//4000 40sec
				i++;
				System.out.print(Button.getButtons());
			}
		   	Delay.msDelay(10);
		    System.out.println("Salut");
		    
		    */
		
	}
}