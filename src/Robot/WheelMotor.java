package Robot;
import java.util.Scanner;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MovePilot.*;


public class WheelMotor extends MovePilot{
	
	
	
	public WheelMotor() {	
		super(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
		
	}
	
	//oui
	/**
	 * 
	 * methode permettant d'avancer
	 * 
	 * @param i temps durant lequel le robot va avancer
	 * 
	 */
	public void forward(double i) {
		super.forward();
		boolean t = true;
		if(t == true) {
			Pinces p = new Pinces();
			p.fermer();
		}
		Delay.msDelay((int)i);
		super.stop();
	}
	
	/**
	 * methode permettant de reculer
	 */
	public void backward() {
		super.backward();
		Delay.msDelay(3000);
		super.stop();
	}
	
	public void rotate(double angle) {
		super.rotate(angle);
	}
	

	
	/**
	 * methode permettant de realiser un arc de cercle
	 */
	public void arcDeCercle(double longueur,boolean droite) {
		if(droite == true) {
			this.rotate(-45);
			this.forward(longueur);
			this.rotate(45);
		}else {
			this.rotate(45);
			this.forward(longueur);
			this.rotate(-45);
		}
		
	}
	

	
	
		
}

