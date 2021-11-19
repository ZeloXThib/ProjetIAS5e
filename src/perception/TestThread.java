package perception;

import Moteur.WheelMotor;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class TestThread extends Thread{
	
	//static WheelMotor motor = new WheelMotor(1);
	
	public void run() {
		while(Sensor.havePalet()==0) {
			System.out.println("le thread est en marche");
		}
	}
}
