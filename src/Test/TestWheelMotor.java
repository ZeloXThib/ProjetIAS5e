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
		
		
		
		
			

		
		WheelMotor m = new WheelMotor(3);
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

		}

	}