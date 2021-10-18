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
		/**
		WheelMotor m = new WheelMotor(3);
		m.afficheLargeur();
		m.afficheLongueur();
		m.afficheBoussole();
		Delay.msDelay(3000);
		m.rotate(45);
		m.forward(1000);
		m.afficheLargeur();
		m.afficheLongueur();
		m.afficheBoussole();
		Delay.msDelay(10000);
		*/
		int distance = 1000;
		int b = 45;
		int boussole = 45;
		int longueur = 300;
		int largeur = 1500;
		double lon = distance*Math.cos(b);
		double larg = distance*Math.sin(b);
		System.out.println(" 1"+lon);
		System.out.println(" 1"+larg);
		if(boussole <= -90) {
			longueur -= lon;
			largeur += larg;
			System.out.println(" 2"+lon);
			System.out.println(" 2"+larg);
		}else if (boussole >= 90) {
			longueur -= lon;
			largeur -= larg;
			System.out.println(" 2"+lon);
			System.out.println(" 2"+larg);
		}else if (boussole <= 0) {
			longueur += lon;
			largeur += larg;
			System.out.println(" 2"+lon);
			System.out.println(" 2"+larg);
		}else {
			longueur += lon;
			largeur -= larg;
			System.out.println(" 2"+lon);
			System.out.println(" 2"+larg);
		}

			
			
		
			
		

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