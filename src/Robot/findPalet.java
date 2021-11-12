package Robot;

import java.util.ArrayList;

import Moteur.WheelMotor;
import lejos.utility.Delay;
import perception.Sensor;

public class findPalet {

	private ArrayList<Double> tab;//double[] tab;
	private ArrayList<Double> tabVar;//double[] tabVar;
	private Sensor sensor;
	private WheelMotor motor;
	
	findPalet(Sensor sensor, WheelMotor motor) {
		//tab = new double[360];
		tab = new ArrayList<Double>();
		tabVar = new ArrayList<Double>();
		this.sensor = sensor;
		this.motor = motor;
	}
	
	public void scan() {
		
		motor.setAngularSpeed(80);
		motor.rotate(345,true); //345 pour faire un 360 avec une vitesse de 80
		
		double valeur_plus_petite = -1;
		int count_test = 0;
		double indice_angle = 0;
		
		while(motor.isMoving()) {
			double valeur_en_cours = sensor.getDistance();
			if(valeur_en_cours < valeur_plus_petite || valeur_plus_petite == -1) {
				valeur_plus_petite = valeur_en_cours;
				indice_angle = motor.getMovement().getAngleTurned();
			}
			Delay.msDelay(10);
			count_test++;
		}

		motor.rotate(indice_angle);
	}
	
	public void is_palet(double distance) {
		if((int)distance == (int)sensor.getDistance()) {
			System.out.println("Meme distance j'y vais");
			motor.travel(distance, true);
			if(sensor.getDistance()<30) {
				
			}
		}
		else {
			//faire un scan de -10/10 pour capter un palet éventuel 
		}
	}
}
