package Moteur;
import java.util.Scanner;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.utility.Delay;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MovePilot.*;


public class WheelMotor extends MovePilot{
	
	private double boussole;
	private double distance;
	private double longueur;
	private double largeur;
	
	
	
	public WheelMotor(int i) {	
		super(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
		boussole = 0;
		distance = 0;
		this.longueur = 300; 
		if ( i == 1) {
			this.largeur = 500;
		}else if (i == 2){
			this.largeur = 1000;
		}else if( i == 3) {
			this.largeur = 1500;
		}
	}
	
	
	
	//oui
	/**
	 * 
	 * methode permettant d'avancer
	 * 
	 * @param i temps durant lequel le robot va avancer
	 * 
	 */	
	
	public double getBousssole() {
		return boussole;
	}
	
	
	public void forward(double distance) {
		super.travel(distance);
		double b = Math.toRadians(Math.abs(this.boussole));
		double lon = distance*Math.cos(b);
		double larg = distance*Math.sin(b);
		System.out.println(lon);
		System.out.println(larg);
		if(this.boussole <= -90) {
			longueur -= lon;
			largeur += larg;
		}else if (this.boussole >= 90) {
			longueur -= lon;
			largeur -= larg;
		}else if (this.boussole <= 0) {
			longueur += lon;
			largeur += larg;
		}else {
			longueur += lon;
			largeur -= larg;
		}
		
	}
	
	/**
	 * methode permettant de reculer
	 */
	public void backward(double distance) {
		super.travel(-distance);
		this.distance -= distance;
	}
	
	public void rotate(double angle,boolean immediateReturn) {
		//C.rotate((int) angle);
		super.rotate(angle, immediateReturn);
		this.mettreAJourBoussole(angle);
		//this.afficheBoussole();
	}
	

	
	public void mettreAJourBoussole(double i) {
		this.boussole += i;
		if(this.boussole < -180 || this.boussole > 180) {
			this.boussole = -this.boussole % 180;
		}
	}
	
	public void afficheBoussole() {
		System.out.println(this.boussole);
	
	}
	
	public void afficheLongueur() {
		System.out.println(this.longueur);
	}
	
	public void afficheLargeur() {
		System.out.println(this.largeur);
	}
	
	/**
	 * methode permettant de realiser un arc de cercle
	 */
	
	/**
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
		*/
	}
	

	
	
		


