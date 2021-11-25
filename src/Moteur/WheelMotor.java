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
//	private double distance;
	private double longueur;
	private double largeur;
	private Sensor sensor;
	
	
	
	public WheelMotor(int i, Sensor sensor) {	
		super(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
		this.sensor = sensor;
		boussole = 0;
		this.longueur = 300; 
		if ( i == 1) {
			this.largeur = 500;
		}else if (i == 2){
			this.largeur = 1000;
		}else if( i == 3) {
			this.largeur = 1500;
		}
	}
	
	public void mettre_a_jour_longueur_largeur(double distance) {
		System.out.println("la distance est "+distance);
		if(boussole > 0 && boussole < 90) {
			largeur += Math.sin(boussole)*distance;
			longueur += Math.cos(boussole)*distance; 
		}else if (boussole < 0 && boussole > -90) {
			largeur -= Math.sin(Math.abs(boussole))*distance;
			longueur += Math.cos(Math.abs(boussole))*distance; 
		}else if (boussole == 0) {
			largeur += 0;
			System.out.println("premiere" + longueur);
			longueur += distance; 
			System.out.println("deuxieme" + longueur);
			//System.out.println("longeur: "+longueur);
		}else if (boussole == 90) {
			largeur -= distance;
			longueur += 0; 
		}else if (boussole == -90) {
			largeur += distance;
			longueur += 0;
		}else if (boussole < -180 && boussole > -90) {
			double angle = 180+boussole;
			largeur += Math.cos(angle)*distance;
			longueur -= Math.sin(angle)*distance;
		}else if (boussole > 90 && boussole < 180) {
			double angle = 180-boussole;
			largeur -= Math.cos(angle)*distance;
			longueur -= Math.sin(angle)*distance;
		}else if (boussole == 180 || boussole == -180) {
			largeur += 0;
			longueur -= distance; 
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
			System.out.println("pass 1");
			rotateEnFonctionBoussole(0);
			double a;
			if(this.largeur-largeurF == 0) {
				a = 0;
			}else if (longueurF-this.longueur == 0) {
				a = 90;
			}else {
				a = Math.toDegrees(Math.atan((this.largeur-largeurF)/(longueurF-this.longueur)));
			}
			rotate(a,false);
			forward(Math.sqrt((Math.pow(this.longueur-longueurF, 2)) + (Math.pow(this.largeur-largeurF, 2)) ),false);
//			this.largeur = largeurF;
//			this.longueur = longueurF;
		}else if (longueurF >= this.longueur && largeurF >= this.largeur) {
			System.out.println("pass 2");
			rotateEnFonctionBoussole(-90);
			double a;
			if(longueurF-this.longueur == 0) {
				a = 0;
			}else if( largeurF - this.largeur == 0){
				a = 90;
			}else {	
				a = Math.toDegrees(Math.atan((largeurF-this.largeur)/(longueurF-this.longueur)));
			}
			System.out.println(a);
			Delay.msDelay(3000);
			rotate(a,false);
			forward(Math.sqrt((Math.pow(this.longueur-longueurF, 2)) + (Math.pow(largeurF-this.largeur, 2)) ),false);
//			this.largeur = largeurF;
//			this.longueur = longueurF;
		}else if (longueurF <= this.longueur && largeurF <= this.largeur) {
			System.out.println("pass 3");
			Delay.msDelay(3000);
			rotateEnFonctionBoussole(90);
			System.out.println("Largeur " + this.largeur);
			System.out.println("LargeurF " + largeurF);
			System.out.println("Longueur " + this.longueur);
			System.out.println("LonguueurF " + longueurF);
			Delay.msDelay(8000);
			double a;
			if(longueurF - this.longueur == 0) {
				a = 0;
			}else if(this.largeur-largeurF == 0){
				a = 90;
			}else {
				a = Math.toDegrees(Math.atan((this.largeur-largeurF)/(this.longueur-longueurF)));
			}
			
			//System.out.println("Angle " + a);
			//Delay.msDelay(3000);
			rotate(a,false);
			System.out.println("Forward " + Math.sqrt((Math.pow(longueurF-this.longueur, 2)) + (Math.pow(this.largeur-largeurF, 2)) ));
			Delay.msDelay(5000);
			forward(Math.sqrt((Math.pow(longueurF-this.longueur, 2)) + (Math.pow(this.largeur-largeurF, 2)) ),false);
//			this.largeur = largeurF;
//			this.longueur = longueurF;
		}else {
			System.out.println("pass 4");
			rotateEnFonctionBoussole(180);
			double a;
			if(largeurF - this.largeur == 0) {
				a = 0;
			}else if(this.longueur-longueurF == 0) {
				a = 90;
			}else {
				a = Math.toDegrees(Math.atan((largeurF-this.largeur)/(this.longueur-longueurF)));
			}			
			rotate(a,false);
			System.out.println(Math.sqrt((Math.pow(longueurF-this.longueur, 2)) + (Math.pow(largeurF-this.largeur, 2))));
			forward(Math.sqrt((Math.pow(longueurF-this.longueur, 2)) + (Math.pow(largeurF-this.largeur, 2)) ),false);
//			this.largeur = largeurF;
//			this.longueur = longueurF;
		}
		
	}
	
	public void forwardUntil(String couleur) {
		super.forward();  
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != couleur) {
	    	rgb = Sensor.getColorOnGround();
	   // 	System.out.println(this.boussole);
	    	
	   
	    }

	}
	
	public void backwardUntil(String couleur) {
		super.backward();  
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != couleur) {
	    	rgb = Sensor.getColorOnGround();
	    }
	    super.stop();
	}
	
	
	public void stop() {
		super.stop();
//		double distance = this.getMovement().getDistanceTraveled();
//		System.out.println(distance);
	}
	
	public void forward() {
		super.forward();
		mettre_a_jour_longueur_largeur(getMovement().getDistanceTraveled());
		//System.out.println(this.getMovement().getDistanceTraveled());
		//Delay.msDelay(3000);
		
	}
	
	public void forward(double distance,boolean immediateReturn) {
		super.travel(distance,immediateReturn);
		mettre_a_jour_longueur_largeur(distance);
	}
	
	/**
	 * methode permettant de reculer
	 */
	public void backward(double distance) {
		super.travel(-distance);
		
	}
	
	public void rotate(double angle,boolean immediateReturn) {
		//C.rotate((int) angle);
		this.setAngularSpeed(80);
		super.rotate(angle*0.908,immediateReturn);
		this.mettreAJourBoussole(angle);
		this.setAngularSpeed(100000);
		//this.afficheBoussole();
	}
	
	public void rotate(double angle) {
		this.rotate(angle, false);
	}
	
	public void rotateEnFonctionBoussole(double angleArrivee) { 
		rotate(angleArrivee-this.boussole,false);
		
		
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
		
		if(this.boussole >= 0) {
			if(i <= 0) {
				//System.out.println("maj 1");
				this.boussole += i;
			}else if(i > 0 && this.boussole+i >= 180) {
				//System.out.println("maj 2");
				double a = 180 - this.boussole;
				double b = i - a;
				this.boussole = (180 - b)*-1;
			}else {
				this.boussole += i; 
			}
		}else {
			if(i > 0) {
				//System.out.println("maj 3");
				this.boussole +=i;
			}else if(i < 0 && this.boussole - i <= -180) {
				//System.out.println("maj 4");
				double a = 180 - -1*this.boussole;
				double b = i - a;
				this.boussole = 180 - b;
			}else {
				this.boussole +=i;
			}
		}
		System.out.println(this.boussole);
//		if(this.boussole < -180 || this.boussole > 180) {
//			this.boussole = -this.boussole % 180;
//		}
	}
	
	public void boussole_a_0() {
		this.rotate(20,false);
		this.rotate(-40,true);
		double min = 100;
		double angle_trouver = 0;
		while(this.isMoving()) {
			double valeur_en_cours = sensor.getDistance();
			if(valeur_en_cours<min) {
				min=valeur_en_cours;
				angle_trouver = this.getMovement().getAngleTurned();
			}
			Delay.msDelay(3);
		}
		//motor.rotate(indice_angle);
		//System.out.print("Voila l'angle : " + angle_trouver);
		//Delay.msDelay(3000);
		this.rotate(40+angle_trouver, false);
		this.setBoussole(0);
	}
	
	public void mettre_a_jour_largeur() {
		
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

	public void setBoussole(double boussole) {
		this.boussole = boussole;
	}
	
	public double getLongueur() {
		return longueur;
	}
	
	public double getLargeur() {
		return largeur;
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
	

	
	
		


