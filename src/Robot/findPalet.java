package Robot;

import java.util.ArrayList;
import Moteur.Pinces;
import Moteur.WheelMotor;
import lejos.utility.Delay;
import perception.Sensor;

public class findPalet {

	//test de adri

	private ArrayList<Double> tab;//double[] tab;
	private ArrayList<Double> tabVar;//double[] tabVar;
	private Sensor sensor;
	private WheelMotor motor;
	private Pinces pince;
	private double dist_max;
	private double distance_parcourue;
	private double[][] pointScan = {
			{0,2700,1000},
			{0,2100,1000},
			{0,1800,1000},
			{0,1500,1000},
			{0,1200,1000},
			{0,900,1000}
	};

	public findPalet(Sensor sensor, WheelMotor motor, Pinces pince, double distance_max, double distance_parcourue) {
		//tab = new double[360];
		tab = new ArrayList<Double>();
		tabVar = new ArrayList<Double>();
		this.sensor = sensor;
		this.motor = motor;
		this.pince = pince;
		this.dist_max = distance_max;
		this.distance_parcourue =distance_parcourue;
	}

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


	public double[] gotoScanPoint() {
		double[] a = {this.pointScan[0][2],this.pointScan[0][1]};
		return a;
	}


	public double scan(int angle_scan) {
		motor.setAngularSpeed(40);
		double valeur_plus_petite = 100;
		int count_test = 0;
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
			Delay.msDelay(10);
			count_test++;
		}
		motor.rotate(angle_scan+indice_angle, false);
		return valeur_plus_petite;
	}

	public boolean marquer_palet(int angle_scan, int angle_verif) {
		double dist = 0;
		motor.rotateEnFonctionBoussole(180);
		double a = scan(angle_scan);
		if (a >= dist_max){
			scanDone();
			motor.rotateEnFonctionBoussole(180);
			return false;	
		}else if (sensor.getDistance() > a){
			a = scan(angle_verif);
		}
		pince.ouvrir(false);
		motor.forward();
		
		while(Sensor.havePalet() == 0) {
			double distanceee = motor.getMovement().getDistanceTraveled();
			if(motor.getMovement().getDistanceTraveled() > ((a*10000)+100) ) {
				motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
				motor.stop();
				System.out.println("A= " + (a*1000)+100);
				System.out.println("GetM= "+distanceee);
				Delay.msDelay(5000);
				pince.fermer(false);
				motor.rotateEnFonctionBoussole(0);
				motor.forwardUntil("WHITE");
				dist = motor.getMovement().getDistanceTraveled();
				motor.stop();
				motor.mettre_a_jour_longueur_largeur(dist);
				System.out.println("boucle 1");
				return false;
			}
			if(sensor.getDistance()<0.2) {
				System.out.println("MAINTENANT");
				motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
				double distance_parcourue = motor.getMovement().getDistanceTraveled();
				motor.stop();	
				motor.backward(distance_parcourue);
				pince.fermer(false);
				scanDone();
				Delay.msDelay(3000);
				System.out.println("boucle 2");
				motor.rotateEnFonctionBoussole(180);
				return false;
			}
		}
		System.out.println("boucle 3");
		dist = motor.getMovement().getDistanceTraveled();
		motor.stop();
		motor.mettre_a_jour_longueur_largeur(dist);
		System.out.println("largeur  " + motor.getLargeur());
		System.out.println("longueur  " + motor.getLongueur());
		System.out.println("boussole  " + motor.getBoussole());
		Delay.msDelay(5000);
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
		System.out.println("largeur  " + motor.getLargeur());
		System.out.println("longueur  " + motor.getLongueur());
		System.out.println("boussole  " + motor.getBoussole());
		Delay.msDelay(5000);
		return true;

	}
	
	//140 est la distance entre le capteur et le milieu des roues 
	public void mettre_a_jour_largeur() {
		if(motor.getLargeur()<750) {
			motor.rotateEnFonctionBoussole(90);
			double distance_percu = this.scan(40);
		//	if(Math.abs((distance_percu*1000)-motor.getLargeur())<400) {
				motor.setLargeur(distance_percu*1000+140);	
			//}
			//else {
			//	return;
			//}
		}
		else if(motor.getLargeur()>1250) {
			motor.rotateEnFonctionBoussole(-90);
			double distance_percu = this.scan(40);
			//if(Math.abs((distance_percu*1000)-motor.getLargeur())<400) {
				motor.setLargeur(2000-(distance_percu*1000+140));
				
			//}
			//else {
				
				return;
			//}
		}
		else {
			
			return;
		}
	}









}






