package Moteur;
import java.util.Scanner;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.utility.Delay;
import perception.Sensor;
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
	
	public void goTo(double largeurF, double longueurF) {
		if (longueurF >= this.longueur && largeurF <= this.largeur) {
			//rotate(90);
			rotateEnFonctionBoussole(90);
			double a = Math.toDegrees(Math.atan((longueurF-this.longueur)/(this.largeur-largeurF)));
			rotate(-a);
			Delay.msDelay(2000);
			forward(Math.sqrt((Math.pow(longueurF-this.longueur, 2)) + (Math.pow(this.largeur-largeurF, 2)) ),false);
		}else if (longueurF >= this.longueur && largeurF >= this.largeur) {
			rotateEnFonctionBoussole(-90);
			double a = Math.toDegrees(Math.atan((longueurF-this.longueur)/(largeurF-this.largeur)));
			rotate(a);
			forward(Math.sqrt((Math.pow(longueurF-this.longueur, 2)) + (Math.pow(largeurF-this.largeur, 2)) ),false);
		}else if (longueurF <= this.longueur && largeurF <= this.largeur) {
			rotateEnFonctionBoussole(180);
			double a = Math.toDegrees(Math.atan((this.longueur-longueurF)/(this.largeur-largeurF)));
			rotate(-a);
			forward(Math.sqrt((Math.pow(this.longueur - longueurF, 2)) + (Math.pow(this.largeur-largeurF, 2)) ),false);
		}else {
			rotateEnFonctionBoussole(180);
			double a = Math.toDegrees(Math.atan((this.longueur-longueurF)/(largeurF-this.largeur)));
			rotate(a);
			forward(Math.sqrt((Math.pow(this.longueur - longueurF, 2)) + (Math.pow(largeurF-this.largeur, 2)) ),false);
		}
		
	}
	
	public void forwardUntil(String couleur) {
		super.forward();  
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != couleur) {
	    	rgb = Sensor.getColorOnGround();
	    }
	    super.stop();
	}
	
	public void backwardUntil(String couleur) {
		super.backward();  
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != couleur) {
	    	rgb = Sensor.getColorOnGround();
	    }
	    super.stop();
	}
	
	
	
	
	public void forward() {
		super.forward();
	}
	
	public void forward(double distance,boolean immediateReturn) {
		super.travel(distance,immediateReturn);
		double b = Math.toRadians(Math.abs(this.boussole));
		double lon = distance*Math.cos(b);
		double larg = distance*Math.sin(b);
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
		super.rotate(angle*0.9388888889,immediateReturn);
		this.mettreAJourBoussole(angle);
		//this.afficheBoussole();
	}
	
	public void rotate(double angle) {
		this.rotate(angle, false);
	}
	
	public void rotateEnFonctionBoussole(double angleArrivee) { 
		
		/**
		if(angleArrivee >= 0) {
			if(this.boussole < 0) {
				if(angleArrivee-180 >= boussole) {
					System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole
							+ " on tourne donc à gauche");
					this.rotate(Math.abs(this.boussole)+ angleArrivee);
					System.out.println("C'est bon " + (Math.abs(this.boussole)+ angleArrivee));
				}
				else {
					System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole
							+ " on tourne donc à droite");
					Delay.msDelay(5000);
					this.rotate(-((180+this.boussole)+(180-angleArrivee)));
					System.out.println("C'est bon " + -((180+this.boussole)+(180-angleArrivee)));
				}
			}
			if(this.boussole >= 0) {
				System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole);
				this.rotate(angleArrivee-this.boussole);
				System.out.println("C'est bon " + (angleArrivee-this.boussole));
			}
			
		}
		if(angleArrivee < 0) {
			if(this.boussole <= 0) {
				if(angleArrivee >= boussole) { 
					System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole
							+ " on tourne donc à gauche");
					this.rotate(Math.abs(angleArrivee - this.boussole));
					System.out.println("C'est bon " + Math.abs(this.boussole)+ angleArrivee);
				}
				else {
					System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole
							+ " on tourne donc à droite");
					this.rotate(angleArrivee - this.boussole);
					System.out.println("C'est bon " + (angleArrivee - this.boussole));
				}
			}
			if(this.boussole > 0) {
				if(angleArrivee+180 >= boussole) {
					System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole
							+ " on tourne donc à gauche");
					this.rotate(angleArrivee+180-boussole);
					System.out.println("C'est bon " + (angleArrivee+180-boussole));
				}
				else {
					System.out.println("l'angle d'arrivé est " + angleArrivee + " , nous sommes en " + this.boussole
							+ " on tourne donc à droite");
					this.rotate(boussole - (angleArrivee+180));
					System.out.println("C'est bon " + (boussole - (angleArrivee+180)));
				}
			
		}
		}
		*/
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
	
	public void setLongueur(double d) {
		this.longueur = d;
	}
	
	public void setLargeur(double d) {
		this.largeur = d;
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
	

	
	
		


