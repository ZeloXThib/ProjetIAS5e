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
	/**
	 * class permettant de gerer tout ce qui tourne autour des moteurs des roues
	 * @attribut boussole: boussole du robot (vers ou est oriente le robot)
	 * @attribut longueur: coordonnee longueur du robot
	 * @attribut largeur: coordoneee largeur du robot
	 * @attribut sensor: capteurs du robot
	 */
	
	private double boussole;
	private double longueur;
	private double largeur;
	private Sensor sensor;
	
	
	/**
	 * constructeur de WheelMotor
	 * @param i : permet de definir position de départ du robot 
	 * @param sensor : permet d acceder aux capteurs du robot
	 */
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

	/**
	 * methode qui permet de mettre a jour les attibuts longueur et largeur du robot grace a la distance  
	 * @param distance  : distance parcourue par le robot
	 */
	public void mettre_a_jour_longueur_largeur(double distance) {
		System.out.println("Distance="+(int)distance);
		System.out.println("Boussole="+this.boussole);
		if(boussole > 0 && boussole < 90) {
			largeur -= Math.sin(Math.toRadians(boussole))*distance;
			longueur += Math.cos(Math.toRadians(boussole))*distance; 
		}else if (boussole < 0 && boussole > -90) {
			largeur += Math.sin(Math.abs(Math.toRadians(boussole)))*distance;
			longueur += Math.cos(Math.abs(Math.toRadians(boussole)))*distance; 
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
			largeur += Math.cos(Math.toRadians(Math.abs(angle)))*distance;
			longueur -= Math.sin(Math.toRadians(Math.abs(angle)))*distance;
		}else if (boussole > 90 && boussole < 180) {
			double angle = boussole-90;
			largeur -= Math.cos(Math.toRadians(angle))*distance;
			longueur -= Math.sin(Math.toRadians(angle))*distance;
		}else if (boussole == 180 || boussole == -180) {
			largeur += 0;
			longueur -= distance; 
		}
		System.out.println("aL "+this.longueur);
		System.out.println("al "+this.largeur);
	}

	
	

	/**
	 * 
	 * @return : getter de la boussole du robot
	 */
	public double getBoussole() {
		return boussole;
	}
	
	
	/**
	 * methode permettant d'aller à un endroit dans l'arène
	 * @param largeurF : largeur vers laquelle le robot doit aller
	 * @param longueurF : longueur vers laquelle le robot doit aller
	 */
	public void goTo(double largeurF, double longueurF) {
		if(largeurF==this.largeur && longueurF==this.longueur) {
			System.out.println("J'y suis deja");
			return;
		}
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
			rotate(a,false);
			forward(Math.sqrt((Math.pow(Math.abs(longueurF-this.longueur), 2)) + (Math.pow(Math.abs(this.largeur-largeurF), 2)) ),false);
			this.largeur = largeurF;
			this.longueur = longueurF;
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
			this.setAngularSpeed(240);
		}
		
	}
	
	/**
	 * methode qui permet d avancer jusqu'a une couleur 
	 * @param couleur : couleur choisie 
	 */
	public void forwardUntil(String couleur) {
		super.forward();  
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != couleur) {
	    	rgb = Sensor.getColorOnGround();
	    }

	}
	
	/**
	 * methode qui permet de reculer jusqu'a une couleur
	 * @param couleur : couleur choisie 
	 */
	public void backwardUntil(String couleur) {
		super.backward();  
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != couleur) {
	    	rgb = Sensor.getColorOnGround();
	    }
	    
	}
	
	/**
	 * methode qui permet d'arreter un mouvement 
	 */
	public void stop() {
		super.stop();
	}
	
	/**
	 * methode qui permet d'avancer (il avancera tant qu'il n'aura pas l'information d'arreter dans le code)
	 */
	public void forward() {
		super.forward();
		
	}
	
	/**
	 * methode qui permet d'avancer sur une distance donnee et qui rend la main ou non tout en avançant 
	 * @param distance : distance parcourue par le robot
	 * @param immediateReturn : boolean permettant de savoir si on rend la main ou non tout en avançant
	 */
	public void forward(double distance,boolean immediateReturn) {
		super.travel(distance,immediateReturn);
		
	}
	
	/**
	 * methode qui permet de reculer sur une distance donnee
	 * @param distance : distance à reculer
	 */
	public void backward(double distance) {
		super.travel(-distance);
	}
	
	/**
	 * methode qui permet de tourner d'un certain angle en degré et qui rend la main ou non tout en tournant
	 * @param angle : angle de rotation (positif pour aller à gauche, negatif sinon)
	 * @param immediateReturn : boolean qui permet de rendre la main ou non en tournant 
	 */
	public void rotate(double angle,boolean immediateReturn) {
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
	}
	
	/**
	 * methode qui permet de tourner sans rendre la main tant que l'angle n a pas ete fait 
	 * @param angle : angle de rotation
	 */
	public void rotate(double angle) {
		this.rotate(angle, false);
	}
	
	/**
	 * methode qui permet de s'orienter vers un angle en fonction de l'orientation de base du robot (boussole)
	 * @param angleArrivee : angle vers lequel on  veut s'orienter
	 */
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
	

	/**
	 * methode qui permet de mettre a jour la boussole suite à des rotations
	 * @param i : angle realise
	 */
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
		

	}
	
	/**
	 * methode permettant de recalibrer la boussole a 0 suite à des erreurs possibles du robot sur des rotations
	 */
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
			}
			Delay.msDelay(3);
			cmp++;
		}

		this.rotate(60+angle_trouver, false);
		this.setBoussole(0);
	}
	
	
	/**
	 * methode qui permet d'afficher la boussole dans la console
	 */
	public void afficheBoussole() {
		System.out.println(this.boussole);
	
	}
	
	/**
	 * methode qui permet d'afficher la longueur dans la console
	 */
	public void afficheLongueur() {
		System.out.println("blablabla" + this.longueur);
	}
	
	
	/**
	 * methode qui permet d'afficher la largeur dans la console
	 */
	public void afficheLargeur() {
		System.out.println("blibliblbi "+ this.largeur);
	}
	
	/**
	 * setter de la longueur
	 */
	public void setLongueur(double d) {
		this.longueur = d;
	}
	
	/**
	 * setter de la largeur
	 */
	public void setLargeur(double d) {
		this.largeur = d;
	}
	
	/**
	 * setter de la boussole
	 */
	public void setBoussole(double boussole) {
		this.boussole = boussole;
	}
	
	/**
	 * getter de la longueur
	 */
	public double getLongueur() {
		return longueur;
	}
	
	/**
	 * getter de la largeur
	 */
	public double getLargeur() {
		return largeur;
	}
	
	
	
	
	}
	

	
	
		


