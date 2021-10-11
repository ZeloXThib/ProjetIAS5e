package Test;


import java.util.Scanner;

import Robot.Sensor;
import Robot.WheelMotor;
import lejos.hardware.Button;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class TestWheelMotor {

	public static void main(String[] args) {
		/*
			Scanner sc = new Scanner(System.in);
			System.out.println("1, 2 ou 3");
			int num = sc.nextInt();
			sc.close();
			WheelMotor m = new WheelMotor();
		*/
			int i = 0;
			while(i<4000 && Button.ENTER.isDown()==false){//4000 40sec
				Delay.msDelay(10);
				i++;
			}
		   	Delay.msDelay(10);
		    System.out.println("Salut");
		    Delay.msDelay(10000);
		    
	}
}