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
	private static Sensor sensor = new Sensor();
	//private static MovePilot pilot = new MovePilot(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
	private static WheelMotor motor = new WheelMotor(1);
	static Pinces pince = new Pinces();
	
	public static void strategie(int d, int d2) {
		/*
		pilot.forward();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<4000 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    Delay.msDelay(1000);
	    pilot.stop();
	    pince.fermer();
	    pilot.rotate(d);//45 stratégie1
	    pilot.forward();
	    Delay.msDelay(1700);
	    pilot.stop();
	    Delay.msDelay(1700);
	    pilot.rotate(d2);//-45 stratégie2
	    pilot.forward();
	    
	    Color rgb = Sensor.getColorOnGround();
	    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != "WHITE") {
	    	rgb = Sensor.getColorOnGround();
	    }
	    Delay.msDelay(50);
	    pilot.stop();
	    pince.ouvrir();
	    */
	}
	public static void main(String[] args) {
		
		pince.fermer();
		/*
		// AVANCER TANT QUE COULEUR != BLANC	
		System.out.print("ESt-ce que la pinces est ferme ?: Oui(G),Non(D)");
		int g = 0;
		while(g==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				g = 1;
				pince.ouvrir();
			}	
			if(Button.RIGHT.isDown()) {
				g = 2;
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
			System.out.println("Run");
			
			//motor.rotate(360*5);
			
			
			//Delay.msDelay(2000);//2sec
			
			System.out.println("Try Scan");
			findPalet fp = new findPalet(sensor, motor);
			fp.scan();
			
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
	    */
	}

}
