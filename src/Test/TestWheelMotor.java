package Test;


import java.util.Scanner;

import Moteur.Pinces;
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
		
		
		WheelMotor m = new WheelMotor(1);
		m.setLargeur(1500);
		m.setLongueur(1500);
		m.goTo(500,900);
		Pinces p = new Pinces();
		p.fermer();
		
		
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
		}

	}