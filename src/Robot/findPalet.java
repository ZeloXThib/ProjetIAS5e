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
			if(pointScan[0][0] == 1) {
				for(int i=0;i<pointScan.length;i++) {
					for(int j=0;j<pointScan[0].length;j++) {
						tab[i][j]=pointScan[i+1][j];
					}
				}
				pointScan = tab;
			}

			else {
				pointScan[0][0]++;
			}
		}
	}


	public double[] gotoScanPoint() {
		double[] a = {this.pointScan[0][1],this.pointScan[0][2]};
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
		double a = scan(angle_scan);
		if (is_palet(a)==false){
			a = scan(angle_verif);	
		}
		motor.forward();
		pince.ouvrir();
		int i=0;
		while(Sensor.havePalet() == 0) {	
			
			if( motor.getMovement().getDistanceTraveled() < ((a+this.dist_max)/2)+100 ) {
				motor.stop();
				pince.fermer();
				scanDone();
				System.out.println("boucle 1");
				return false;
			}
			if(sensor.getDistance()<0.2) {
				motor.stop();
				double distance_parcourue = motor.getMovement().getDistanceTraveled();
				motor.backward(distance_parcourue);
				pince.fermer();
				scanDone();
				System.out.println("boucle 2");
				return false;
			}
		}
		System.out.println("boucle 3");
		motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
		motor.stop();
		pince.fermer();
		motor.rotateEnFonctionBoussole(0);
		motor.forwardUntil("WHITE");
		motor.setLongueur(2700-35);
		pince.ouvrir();
		motor.boussole_a_0();
		pince.fermer();
		return true;
	}
	
	public void mettre_a_jour_largeur() {
		if(motor.getLargeur()<501) {
			motor.rotateEnFonctionBoussole(90);
			double distance_percu = this.scan(40);
			if(Math.abs((distance_percu*100)-motor.getLargeur())<200) {
				motor.setLargeur(distance_percu);
				System.out.println(motor.getLargeur());
			}
			else {
				System.out.println("Trop de différence");
				return;
			}
		}
		else if(motor.getLargeur()>1500) {
			motor.rotateEnFonctionBoussole(-90);
			double distance_percu = this.scan(40);
			if(Math.abs((distance_percu*100)-motor.getLargeur())<200) {
				motor.setLargeur(distance_percu);
				System.out.println(motor.getLargeur());
			}
			else {
				System.out.println("Trop de différence");
				return;
			}
		}
		else {
			System.out.println("Pas la bonne largeur");
			return;
		}
	}
	
	


	//      
	//		motor.forward();
	//		pince.ouvrir();
	//		int i=0;
	//		 while(Sensor.havePalet()==0 && i<300 && (motor.getMovement().getDistanceTraveled() < ((valeur_plus_petite+min)/2)+100 )){
	//		    	Delay.msDelay(10);
	//		    	i++;
	//		    }
	//		 motor.stop();
	//		 pince.fermer(); 
	//		scanDone();
	//		
	//		
	//		return true;
	//	}



	public boolean is_palet(double distance) {

		double distance_en_cours = sensor.getDistance();
		if(distance_en_cours>distance) {

			return false;

			/*double valeur_prec = sensor.getDistance();
		double valeur_en_cours;
		double distance_parcourue_au_cas_ou ;
		if((int)distance <= (int)sensor.getDistance() +0.01 || (int)distance >= (int)sensor.getDistance() +0.01) {
			System.out.println("Meme distance j'y vais");
			motor.travel(distance, true);
			while(motor.isMoving() || Sensor.havePalet()==0) {
				if(sensor.getDistance() > valeur_prec && valeur_prec > 0.3) {
					return true;
				}
				else if(sensor.getDistance()< 0.3) {
					distance_parcourue_au_cas_ou = motor.getMovement().getDistanceTraveled();
					motor.stop();
					motor.backward(distance_parcourue_au_cas_ou);
					return false;
				}
				valeur_prec = sensor.getDistance();
			}


		}*/

		} else {

			return true;
		}


	}
	/*
	public boolean paletTrouve(WheelMotor m) {
		m.rotateEnFonctionBoussole(180);
		boolean trouve = false;
		while (trouve == false && sensor.havePalet()==0) {
			if(scan()==false) {
				double[] a = gotoScanPoint();
				motor.goTo(a[0],a[1]);
				m.rotateEnFonctionBoussole(180);
			}else {
				trouve = true;
			}
		}
		return trouve;
	}
	/*

	/**
	public static void main(String[] args) {

		boolean trouve = false;
		while(trouve == false) {
			if(scan(180)==false) {
				double[] a = gotoScanPoint();
				motor.goTo(a[0],a[1]);
			}else {
				trouve = true;
			}

		}


	}
	 */
}






