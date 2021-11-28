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
//	public void mettre_a_jour_longueur_largeur_backward(double distance) {
//		//System.out.println("la distance est "+distance);
//		if(boussole > 0 && boussole < 90) {
//			double angle = 180+boussole;
//			largeur += Math.cos(angle)*distance;
//			longueur -= Math.sin(angle)*distance;
//		}else if (boussole < 0 && boussole > -90) { 
//			double angle = 180-boussole;
//			largeur -= Math.cos(angle)*distance;
//			longueur -= Math.sin(angle)*distance;
//		}else if (boussole == 0) {
//			largeur += 0;
//			longueur -= distance; 
//		}else if (boussole == 90) {
//			largeur += distance;
//			longueur += 0; 
//		}else if (boussole == -90) {
//			largeur -= distance;
//			longueur += 0;
//		}else if (boussole < -180 && boussole > -90) {	
//			largeur += Math.sin(boussole)*distance;
//			longueur += Math.cos(boussole)*distance;
//		}else if (boussole > 90 && boussole < 180) {
//			largeur -= Math.sin(Math.abs(boussole))*distance;
//			longueur += Math.cos(Math.abs(boussole))*distance;
//		}else if (boussole == 180 || boussole == -180) {
//			largeur += 0;
//			longueur += distance; 
//		}
//		
//	}
	
	public void mettre_a_jour_longueur_largeur(double distance) {
		if(boussole > 0 && boussole < 90) {
			largeur -= Math.sin(boussole)*distance;
			longueur += Math.cos(boussole)*distance; 
		}else if (boussole < 0 && boussole > -90) {
			largeur += Math.sin(Math.abs(boussole))*distance;
			longueur += Math.cos(Math.abs(boussole))*distance; 
		}else if (boussole == 0) {
			largeur += 0;
			longueur += distance; 
		}else if (boussole == 90) {
			largeur -= distance;
			longueur += 0; 
		}else if (boussole == -90) {
			largeur += distance;
			longueur += 0;
		}else if (boussole > -180 && boussole < -90) {
			double angle = -boussole-90; //code d'avant c'était 180-boussole, c'est faux
			largeur += Math.cos(angle)*distance;
			longueur -= Math.sin(angle)*distance;
		}else if (boussole > 90 && boussole < 180) {
			double angle = boussole-90;
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
	 * System.out.println("largeur de base : " + motor.getLargeur());
	 */	
	
	public double getBoussole() {
		return boussole;
	}
	
	public void goTo(double largeurF, double longueurF) {
		this.setAngularSpeed(200);
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
			forward(Math.sqrt((Math.pow(Math.abs(this.longueur-longueurF), 2)) + (Math.pow(Math.abs(this.largeur-largeurF), 2)) ),false);
			this.largeur = largeurF;
			this.longueur = longueurF;
			System.out.println("largeur  " + getLargeur());
			System.out.println("longueur  " + getLongueur());
			System.out.println("boussole  " + getBoussole());
			Delay.msDelay(5000);
		}else if (longueurF >= this.longueur && largeurF >= this.largeur) {
			System.out.println("pass 2");
			rotateEnFonctionBoussole(-90);
			double a;
			if(longueurF-this.longueur == 0) {
				a = 0;
			}else if( largeurF - this.largeur == 0){
				a = 90;
			}else {	
				a = Math.toDegrees(Math.atan((longueurF-this.longueur)/(largeurF-this.largeur)));
			}
			rotate(a,false);
			forward(Math.sqrt((Math.pow(Math.abs(this.longueur-longueurF), 2)) + (Math.pow(Math.abs(largeurF-this.largeur), 2)) ),false);
			this.largeur = largeurF;
			this.longueur = longueurF;
			System.out.println("largeur  " + getLargeur());
			System.out.println("longueur  " + getLongueur());
			System.out.println("boussole  " + getBoussole());
			Delay.msDelay(5000);
		}else if (longueurF <= this.longueur && largeurF <= this.largeur) {
			System.out.println("pass 3");
			rotateEnFonctionBoussole(90);
			double a;
			if(longueurF - this.longueur == 0) {
				a = 0;
			}else if(this.largeur-largeurF == 0){
				a = 90;
			}else {
				a = Math.toDegrees(Math.atan((this.longueur-longueurF)/(this.largeur-largeurF)));
			}
			
			//System.out.println("Angle " + a);
			//Delay.msDelay(3000);
			rotate(a,false);
			forward(Math.sqrt((Math.pow(Math.abs(longueurF-this.longueur), 2)) + (Math.pow(Math.abs(this.largeur-largeurF), 2)) ),false);
			this.largeur = largeurF;
			this.longueur = longueurF;
			System.out.println("largeur  " + getLargeur());
			System.out.println("longueur  " + getLongueur());
			System.out.println("boussole  " + getBoussole());
			Delay.msDelay(5000);
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
			forward(Math.sqrt((Math.pow(Math.abs(longueurF-this.longueur), 2)) + (Math.pow(Math.abs(largeurF-this.largeur), 2)) ),false);
			this.largeur = largeurF;
			this.longueur = longueurF;
			System.out.println("largeur  " + getLargeur());
			System.out.println("longueur  " + getLongueur());
			System.out.println("boussole  " + getBoussole());
			Delay.msDelay(5000);
			this.setAngularSpeed(240);
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
	//	mettre_a_jour_longueur_largeur_backward(distance);
	}
	
	

	public void rotate(double angle,boolean immediateReturn) {
		//C.rotate((int) angle);
		this.setAngularSpeed(80);
		super.rotate(angle*0.908,immediateReturn);
		if(angle==0)
			return;
		if(this.boussole<0 && angle>0){
			this.boussole+=angle;
			return;
		}
		if(this.boussole>0 && angle>0){
			if(this.boussole+angle > 180) {
				this.boussole = -360 +this.boussole+angle;
				return;
			}
			else {
				this.boussole += angle;
				return;
			}
		}
		if(this.boussole==0 && angle>0){
			this.boussole = angle;
			return;
		}
		if(this.boussole<0 && angle<0){
			if(this.boussole+angle<-180) {
				this.boussole = 360 - Math.abs(this.boussole+angle);
				return;
			}
			else {
				this.boussole += angle;
				return;
			}
		}
		if(this.boussole>0 && angle<0){
			this.boussole += angle;
			return;
		}
		if(this.boussole==0 && angle<0){
			this.boussole = angle;
			return;
		}		
			
			
			
			
//			
//			if(angleB - boussoleB > 180) { 
//				this.boussole = (360-angleB)+boussoleB;
//			}else { //angleB - boussoleB <= 180
//				this.boussole = (angleB-boussoleB);
//			}	
//		}else {
//			if(boussoleB - angleB < 180) {
//				this.boussole = (angleB - boussoleB);//
//			}else { //boussoleB - angleB >= 180		
//				this.boussole = ((360-boussoleB)+angleB);
//			}
//		}
		
		//this.afficheBoussole();
	}
	
	public void rotate(double angle) {
		this.rotate(angle, false);
	}
	
	public void rotateEnFonctionBoussole(double angleArrivee) { 

		double boussoleB;
		double angleB;
		if(angleArrivee == this.boussole) {
			return;
		}
		if(Math.abs(angleArrivee) == Math.abs(this.boussole) && Math.abs(angleArrivee) == 180) {
			return;
		}
		if(boussole < 0) {
			boussoleB = 360 + boussole;
		}else {
			boussoleB = boussole;
		}
		if(angleArrivee < 0) {
			angleB = 360 + angleArrivee;
		}else {
			angleB = angleArrivee;
		}
		if(angleB > boussoleB) {
			if(angleB - boussoleB > 180) { 
				rotate((angleB-360)-boussoleB);
			}else { //angleB - boussoleB <= 180
				rotate(angleB - boussoleB);
			}	
		}else {
			if(boussoleB - angleB < 180) {
				rotate(angleB - boussoleB);//
			}else { //boussoleB - angleB >= 180		
				rotate((360-boussoleB)+angleB);
			}
		}
		this.boussole = angleArrivee;
		
		
		
		

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
			}else if(i < 0 && this.boussole - i >= -180) {
				//System.out.println("maj 4");
				double a = 180 +this.boussole;
				double b = i - a;
				this.boussole = 180 - b;
			}else {
				this.boussole +=i;
			}
		}
		
//		if(this.boussole < -180 || this.boussole > 180) {
//			this.boussole = -this.boussole % 180;
//		}
	}
	
	public void boussole_a_0() {
		this.rotate(30,false);
		this.rotate(-60,true);
		int cmp =0;
		double min = 100;
		double angle_trouver = 0;
		while(this.isMoving()) {
			double valeur_en_cours = sensor.getDistance();
			if(valeur_en_cours<min) {
				min=valeur_en_cours;
				angle_trouver = this.getMovement().getAngleTurned();
				System.out.println(valeur_en_cours);
			}
			Delay.msDelay(3);
			cmp++;
		}
		//motor.rotate(indice_angle);
		//System.out.print("Voila l'angle : " + angle_trouver);



		this.rotate(60+angle_trouver, false);
		this.setBoussole(0);
		System.out.println("cmp= "+cmp);
		Delay.msDelay(3000);
	}
	
	
	
	public void afficheBoussole() {
		System.out.println(this.boussole);
	
	}
	
	public void afficheLongueur() {
		System.out.println("blablabla" + this.longueur);
	}
	
	public void afficheLargeur() {
		System.out.println("blibliblbi "+ this.largeur);
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
	

	
	
		


