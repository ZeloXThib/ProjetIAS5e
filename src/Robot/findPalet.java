package Robot;

import java.util.ArrayList;

import Moteur.WheelMotor;
import lejos.utility.Delay;
import perception.Sensor;

public class findPalet {

	//test de adri

	private ArrayList<Double> tab;//double[] tab;
	private ArrayList<Double> tabVar;//double[] tabVar;
	private Sensor sensor;
	private WheelMotor motor;
	private double[][] pointScan = {
		{0,2100,1000},
		{0,1800,1000},
		{0,1500,1000},
		{0,1200,1000},
		{0,900,1000}
		};

	findPalet(Sensor sensor, WheelMotor motor) {
		//tab = new double[360];
		tab = new ArrayList<Double>();
		tabVar = new ArrayList<Double>();
		this.sensor = sensor;
		this.motor = motor;
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




	public boolean scan(double angleScan) {
		boolean trouve = false;

		motor.setAngularSpeed(80);
		motor.rotate(345,true); //345 pour faire un 360 avec une vitesse de 80
		double distanceMax = 0.8;
		double valeur_plus_petite = -1;
		int count_test = 0;
		double indice_angle = 0;

		while(motor.isMoving()) {
			double valeur_en_cours = sensor.getDistance();
			if(valeur_en_cours < valeur_plus_petite || valeur_plus_petite == -1 && valeur_en_cours < distanceMax) {
				valeur_plus_petite = valeur_en_cours;
				indice_angle = motor.getMovement().getAngleTurned();
			}
			Delay.msDelay(10);
			count_test++;
		}

		motor.rotate(indice_angle);

		if(is_palet(valeur_plus_petite) == false) {
			motor.rotate(10,true);
			motor.rotate(-10,true);
			while(motor.isMoving()) {
				double valeur_en_cours = sensor.getDistance();
				if(valeur_en_cours < valeur_plus_petite || valeur_plus_petite == -1 && valeur_en_cours < distanceMax) {
					valeur_plus_petite = valeur_en_cours;
					indice_angle = motor.getMovement().getAngleTurned();
				}
			}
			motor.rotate(indice_angle);

			if(is_palet(valeur_plus_petite) == false) {

				//renvoyer a l'endroit de scan sauf si deja 2 essais
				

				return false;

			}
		}
		return true;


	}


	public boolean is_palet(double distance) {
		double valeur_prec = sensor.getDistance();
		double valeur_en_cours;
		double distance_parcourue_au_cas_ou ;
		if((int)distance <= (int)sensor.getDistance() +1 || (int)distance >= (int)sensor.getDistance() +1) {
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

		}
		return false;
	}
}
