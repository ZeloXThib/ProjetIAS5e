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
	

	
	/**
	 * methode permettant de realiser un arc de cercle
	 */
	public static void arcDeCercle(WheelMotor m,double longueur,boolean droite) {
		if(droite == true) {
			m.rotate(-45);
			m.forward(longueur);
			m.rotate(45);
		}else {
			m.rotate(45);
			m.forward(longueur);
			m.rotate(-45);
		}
		
	}
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("1, 2 ou 3");
		int num = sc.nextInt();
		sc.close();
		System.out.println(num);
		//WheelMotor m = new WheelMotor();
		//m.forward(10000);
		//arcDeCercle(m,1000,true);	
		//m.forward(3000);
	}
	
		
}

