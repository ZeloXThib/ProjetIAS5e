package Robot;

import java.util.ArrayList;
import Moteur.Pinces;
import Moteur.WheelMotor;
import lejos.utility.Delay;
import perception.Sensor;

public class findPalet {

	/**
	 * class qui gere tout ce qu'il se passe autour des scan, trouver un palet, trouver un mur, trouver un robot, marquer un palet
	 */

	
	private Sensor sensor;
	private WheelMotor motor;
	private Pinces pince;
	private double dist_max; //distance max que le robot peut capter afin qu'il ne reçoive pas des valeurs trop biaisées
	private double distance_parcourue; 
	private double[][] pointScan = { //tous les points de scan où le robot peut aller 
			{0,2700,1000},
			{0,2100,1000},
			{0,1800,1000},
			{0,1500,1000},
			{0,1200,1000},
			{0,900,1000}
	};

	/**
	 * 
	 * @param sensor
	 * @param motor
	 * @param pince
	 * @param distance_max
	 * @param distance_parcourue
	 * constructeur de findPalet
	 */
	public findPalet(Sensor sensor, WheelMotor motor, Pinces pince, double distance_max, double distance_parcourue) {
		//tab = new double[360];
		this.sensor = sensor;
		this.motor = motor;
		this.pince = pince;
		this.dist_max = distance_max;
		this.distance_parcourue =distance_parcourue;
	}
	
	/**
	 * methode qui permet de supprimer le premier point de scan de Point_scan
	 * C'est a dire qu'à chaque fois qu'on aura capté un palet au premier point de scan, on passe au prochain point de scan pour le prochain palet
	 */
	public void scanDone() {
		double[][] tab = new double[pointScan.length-1][3];
		if(pointScan.length>1) {
			for(int i=0;i<pointScan.length-1;i++) {
				for(int j=0;j<pointScan[0].length;j++) {
					tab[i][j]=pointScan[i+1][j];
				}
			}
			pointScan = tab;
		}
	}

	
	/**
	 * 
	 * @return dans un tableau 1*2 la largeur (double[0]) et la longueur (double[1]) auxquelles le robot doit se rendre pour effectuer un nouveau scan 
	 */
	public double[] gotoScanPoint() {
		double[] a = {this.pointScan[0][2],this.pointScan[0][1]};
		return a;
	}

	/**
	 * methode permettant d'effectuer un angle de balayage et de s'orienter vers la distance la plus courte
	 * @param angle_scan  : angle de balayage  
	 * @return : la distance la plus courte 
	 */
	public double scan(int angle_scan) {
		motor.setAngularSpeed(40);
		double valeur_plus_petite = 100;
		double indice_angle = 0;
		double distanceMax = 0.8;
		motor.rotate(angle_scan/2,false);
		motor.rotate(-angle_scan,true);
		while(motor.isMoving()) {
			double valeur_en_cours = sensor.getDistance();
			//System.out.print(valeur_en_cours);
			if(valeur_en_cours < valeur_plus_petite) {
				//valeur_plus_petite == 100 && valeur_en_cours < distanceMax) {
				valeur_plus_petite = valeur_en_cours;
				indice_angle = motor.getMovement().getAngleTurned();
			}
			//System.out.print(valeur_plus_petite);
			Delay.msDelay(5);
		}
		motor.rotate(angle_scan+indice_angle, false);
		return valeur_plus_petite;
	}

	/**
	 * 
	 * @param angle_scan : premier angle de balayage
	 * @param angle_verif : deuxieme angle de balayage (souvent plus petit que le premier afin d'afiner la distance la plus courte)
	 * @return true si on a marque un palet
	 * 		   false si on a trouve un mur ou un robot
	 */
	public boolean marquer_palet(int angle_scan, int angle_verif) {
		double dist = 0;
		motor.rotateEnFonctionBoussole(180); //apres s etre rendu au point de scan on s oriente vers 0
		double a = scan(angle_scan);
		if (a >= dist_max){ //si la distance captee avec le scan est superieur a dist_max on a pas trouve de palet
			scanDone();
			motor.rotateEnFonctionBoussole(180);
			return false;	
		}else if (sensor.getDistance() > a){//si l'orientation du robot n'est pas excellente, on effectue un nouveau scan plus petit pour affiner l'orientation du robot.
			a = scan(angle_verif);			
		}
		pince.ouvrir(false);
		motor.forward();
		
		while(Sensor.havePalet() == 0) {			
			if(motor.getMovement().getDistanceTraveled() > ((a*1000)+100) ) { //return false si on a avance plus de distance que la distance qu'on aurait du parcourir pour avoir le palet
				dist = motor.getMovement().getDistanceTraveled();
				motor.stop();
				motor.mettre_a_jour_longueur_largeur(dist);
				pince.fermer(false);
				motor.rotateEnFonctionBoussole(0);
				motor.forwardUntil("WHITE");
				dist = motor.getMovement().getDistanceTraveled();
				motor.stop();
				motor.mettre_a_jour_longueur_largeur(dist);
				pince.ouvrir(false);
				pince.fermer(false);
				motor.boussole_a_0();
				motor.setLongueur(2700-35);
				mettre_a_jour_largeur();
				System.out.println("boucle 1");
				return false;
			}
			if(sensor.getDistance()<0.2) { // false si on capte un mur
				System.out.println("MAINTENANT");
				motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
				double distance_parcourue = motor.getMovement().getDistanceTraveled();
				motor.stop();	
				motor.backward(distance_parcourue);
				pince.fermer(false);
				scanDone();
				//Delay.msDelay(3000);
				System.out.println("boucle 2");
				motor.rotateEnFonctionBoussole(180);
				return false;
			}
		}
		System.out.println("boucle 3");
		dist = motor.getMovement().getDistanceTraveled();
		motor.stop();
		motor.mettre_a_jour_longueur_largeur(dist);
		pince.fermer(true);
		//Delay.msDelay(5000);
		System.out.println("boussole  " + motor.getBoussole());
		motor.rotateEnFonctionBoussole(0);
		motor.forwardUntil("WHITE");
		dist = motor.getMovement().getDistanceTraveled();
		motor.stop();
		motor.mettre_a_jour_longueur_largeur(dist);
		pince.ouvrir(false);
		pince.fermer(false);
		motor.boussole_a_0();
		motor.setLongueur(2700-35);
		mettre_a_jour_largeur();
		
		
		return true;

	}
	
	/**
	 * methode permettant de mettre a jour la largeur en s'orienter vers un mur sur le cote et en effectuant un petit scan 
	 */
	public void mettre_a_jour_largeur() {
		if(motor.getLargeur()<750) {
			motor.rotateEnFonctionBoussole(90);
			double distance_percu = this.scan(40);
			motor.setLargeur(distance_percu*1000+140);	
			
		}
		else if(motor.getLargeur()>1250) {
			motor.rotateEnFonctionBoussole(-90);
			double distance_percu = this.scan(40);
			motor.setLargeur(2000-(distance_percu*1000+140));			
			return;		
		}
		else {
			
			return;
		}
	}









}






