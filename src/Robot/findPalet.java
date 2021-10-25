package Robot;

import Moteur.WheelMotor;
import lejos.utility.Delay;
import perception.Sensor;

public class findPalet {

	private double[] tab;
	private double[] tabVar;
	private Sensor sensor;
	private WheelMotor motor;
	
	findPalet(Sensor sensor, WheelMotor motor) {
		tab = new double[360];
		tabVar = new double[360];
		this.sensor = sensor;
		this.motor = motor;
	}
	
	public void scan(double nombreSecondePourRota) {
		int deg = 0;
		//AQUISITION
		
		int time_rotate = 3;//sec
		int speed_angular = (int)(360/time_rotate);
		
		System.out.println("goo");
		motor.setAngularSpeed(speed_angular);
		motor.rotate(360,false);
		//Delay.msDelay(5000);
		
		for(int i = 0;i<360;i++) {
			tab[i]=sensor.getDistance();
			//System.out.println(tab[i]);
			Delay.msDelay((int)(((double)time_rotate/360)*1000));
			//problem de precision 
		}
		
		//TRAITEMENT
		for(int i=1;i<360;i++) {
			tabVar[i-1]=Math.abs(tab[i-1]-tab[i]/tab[i]);
		}
		
		//MAX INDICE
		int max_Indice = 0;
		for(int i=0;i<360;i++) {
			if(tabVar[max_Indice]<tabVar[i]) {
				max_Indice=i;
			}
		}
		
		if(max_Indice>=180) {
			deg = -360+max_Indice;
		} else {
			deg = max_Indice;
		}

		motor.rotate(deg,false);
		System.out.println(deg);
		
		
	}	
}
