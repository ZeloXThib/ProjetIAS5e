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
	
	public void scan(double nombreSecondePourRota) {
		double deg = 0;
		//AQUISITION
		
		int time_rotate = 3;//sec
		int speed_angular = (int)(360/time_rotate);
		
		System.out.println("goo");
		double val_vitesse_rotation = motor.getAngularSpeed();
		System.out.println("Vitesse rota default: "+val_vitesse_rotation);
		Delay.msDelay(1000);
		motor.setAngularSpeed(speed_angular);
		motor.rotate(360,true);
		
		
		//Delay.msDelay(5000);
		
		int count_test = 0;
		double valeur_precedente = -1;
		double variation = -1;
		double distance_enregistre = -1;
		int indice_angle = 0;
		while(motor.isMoving()){
			double valeur_en_cours = sensor.getDistance();
			if(valeur_precedente != -1) {
				double variation_en_cours = Math.abs((valeur_precedente-valeur_en_cours)/valeur_en_cours);
					if(variation_en_cours > variation) {
						variation = variation_en_cours;
						indice_angle = count_test;
						distance_enregistre = valeur_en_cours;
					}
				}
			valeur_precedente = valeur_en_cours;
			count_test++;
			Delay.msDelay(5);
			//tab.add((double)sensor.getDistance());
			//System.out.println(tab[i]);
			//(int)(((double)time_rotate/360)*1000));
			//problem de precision 
		}
		double indice_deg = (indice_angle*360)/count_test;	
		if(indice_deg>=180) {
			deg = -360+indice_deg;
		} else {
			deg = indice_deg;
		}
		
		motor.rotate(deg,false);
		//si la distance après rotation est égale à la distance du palet enregistré lors du scan alors on va le chercher 
		if((int)sensor.getDistance()==(int)distance_enregistre) { 
			//avance vers le palet et le prend
			//sinon appelle de la fonction -5 degrès +5 degrès de scan pour se remettre en face 
			//méthode qui scan sur au total 10 degres et qui prend le point le plus proche
		}
		System.out.println("Degres choisi: "+deg);
		System.out.println("Nb tests: "+count_test);
		System.out.println("indice angle: "+indice_angle);
		Delay.msDelay(5000);
		motor.setAngularSpeed(val_vitesse_rotation);
		
		//TRAITEMENT
		/*for(int i=1;i<count_test;i++) {
			
			tabVar.add(Math.abs(tab.get(i-1)-tab.get(i)/tab.get(i)));
		}
		
		//MAX INDICE
		int max_Indice = 0;
		for(int i=0;i<count_test;i++) {
			if(tabVar.get(max_Indice)<tabVar.get(i)) {
				max_Indice=i;
			}
		}
		*/
		//Conversion des indices en deg
		//(x*360)/count_test
		
		
		
	}	
}
