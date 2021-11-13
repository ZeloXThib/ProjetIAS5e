package Robot;

import Moteur.Pinces;
import Moteur.WheelMotor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import perception.Sensor;

public class Brain {
	private static WheelMotor motor = new WheelMotor(1);
	private static Sensor sensor = new Sensor();
	private static Pinces pince = new Pinces();
	private static findPalet fp = new findPalet(sensor, motor);
	
	
	public static void strategie(int d, int d2) {
		motor.forward();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<4000 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    motor.stop();
	    pince.fermer();
	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(300,false);
	    motor.rotate(d2);//-45 stratégie2
	    motor.forwardUntilWhite();
	    pince.ouvrir();
	}
	public static void main(String[] args) {	
		
		// AVANCER TANT QUE COULEUR != BLANC	
		System.out.print("ESt-ce que la pinces est ouverte ?: Oui(G),Non(D)");
		int g = 0;
		while(g==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				g = 1;
			}	
			if(Button.RIGHT.isDown()) {
				g = 2;
				pince.ouvrir();
			}
		}
		Delay.msDelay(500);
		
		System.out.print("Initialisation:\nChoix GAUCHE,MIDGAUCHE ou MIDDROITE : ");
		g = 0;
		while(g==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				g = 1;
			}
			
			if(Button.ENTER.isDown()) {
				g = 2;
			}
			
			if(Button.RIGHT.isDown()) {
				g = 3;
			}
		}
		
		if(g==1) {
			//motor.rotate(360*5);
			//Delay.msDelay(2000);//2sec
			strategie(45,-45);
			
			Delay.msDelay(2000);
			
			
		}else if(g==2) {
			motor.rotate(360);
		}else if(g==3) {
			motor.setAngularSpeed(120);
			motor.rotate(360,true);
			System.out.println(sensor.getDistance());
			while(Button.ENTER.isDown()==false) {
				System.out.println(sensor.getDistance());
				Delay.msDelay(500);
			}
			//strategie(-45,45);
		    
		}
	    Delay.msDelay(10000);
	   
	}

}
