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
	private double[][] pointScan = {
		{0,2100,1000},
		{0,1800,1000},
		{0,1500,1000},
		{0,1200,1000},
		{0,900,1000}
		};

	public findPalet(Sensor sensor, WheelMotor motor, Pinces pince) {
		//tab = new double[360];
		tab = new ArrayList<Double>();
		tabVar = new ArrayList<Double>();
		this.sensor = sensor;
		this.motor = motor;
		this.pince = pince;
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


		


	public boolean scan() {
        motor.setAngularSpeed(40);
        double valeur_plus_petite = 100;
        int count_test = 0;
        double indice_angle = 0;
        double distanceMax = 0.8;
        motor.rotate(90,false);
        motor.rotate(-180,true);
        while(motor.isMoving()) {
            double valeur_en_cours = sensor.getDistance();
            System.out.print(valeur_en_cours);
            if(valeur_en_cours < valeur_plus_petite) {
                //valeur_plus_petite == 100 && valeur_en_cours < distanceMax) {
                valeur_plus_petite = valeur_en_cours;
                indice_angle = motor.getMovement().getAngleTurned();
            }
            System.out.print(valeur_plus_petite);
            Delay.msDelay(10);
            count_test++;
        }

        System.out.println("Nb test : "+count_test);
        Delay.msDelay(2000);
        motor.rotate(-180+indice_angle, false);
        System.out.println("Angle : "+indice_angle);
        Delay.msDelay(2000);



		if(is_palet(valeur_plus_petite) == false) {
			motor.rotate(20,false);
			motor.rotate(-40,true);
			double min = 100;
			double angle_trouver = 0;
			while(motor.isMoving()) {
				double valeur_en_cours = sensor.getDistance();
				if(valeur_en_cours<min) {
					min=valeur_en_cours;
					angle_trouver = motor.getMovement().getAngleTurned();
				}
				Delay.msDelay(10);
			}
			//motor.rotate(indice_angle);
			System.out.print("Voila l'angle : " + angle_trouver);
			Delay.msDelay(3000);
			motor.rotate(40+angle_trouver, false);		
		}
		motor.forward();
		pince.ouvrir();
		int i=0;
		 while(Sensor.havePalet()==0 && i<300 ){
		    	Delay.msDelay(10);
		    	i++;
		    }
		 motor.stop();
		 pince.fermer(); 
		scanDone();
		
		return true;
	}


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
		else
			return true;
		
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






