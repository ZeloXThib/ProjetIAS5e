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
	
	
	public static void strategie1(int d, int d2) {
		motor.forward();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<4000 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    motor.stop();
	    pince.fermer();
	    motor.rotate(d,false);//45 strat�gie1
	    motor.forward(300,false);
	    motor.rotate(d2);//-45 strat�gie2
	    motor.forwardUntilWhite();
	    pince.ouvrir();
	}

	
	public static void main(String[] args) {	
		
		
		//-----------------------------------------//
		//Etat des pinces
		//-----------------------------------------//

		
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

		//-----------------------------------------//
		//Stat�gie 1, 2 ou 3
		//-----------------------------------------//
				
	
		System.out.println("Stategie 1, 2 ou 3");
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
		
		
		//-----------------------------------------//
		//D'o� part le robot ? Gauche ; Milieu : Droite
		//-----------------------------------------//
		
		System.out.println("D'ou part le robot ? Gauche ; Milieu : Droite");
		int h = 0;
		while(h==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				h = 1;
			}
			if(Button.ENTER.isDown()) {
				h = 2;
			}
			if(Button.RIGHT.isDown()) {
				h = 3;
			}
		}
		
		
		//-----------------------------------------//
		//D�part � gauche ou � droite ? 1=Gauche ; 2=Droite
		//-----------------------------------------//
		
		if(g==1 || g==2){
			System.out.println("Depart a gauche ou a droite ? 1=Gauche ; 2=Droite");
			int r = 0;
			while(r==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					r = 1;
				}
				if(Button.ENTER.isDown()) {
					r = 2;
				}
			}
		}
		
		//-----------------------------------------//
		//Quel palet est pr�sent sur le terrain (au bon endroit):
		//-----------------------------------------//
		
		if(g==3) {
			System.out.println("Quel palet est present sur le terrain (au bon endroit):");
			System.out.println("Palet p1 :");
			int p1 = 0;
			while(p1==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p1 = 1;
				}
				if(Button.ENTER.isDown()) {
					p1 = 2;
				}
			}
			System.out.println("Palet p2 :");
			int p2 = 0;
			while(p2==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p2 = 1;
				}
				if(Button.ENTER.isDown()) {
					p2 = 2;
				}
			}
			System.out.println("Palet p3 :");
			int p3 = 0;
			while(p3==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p3 = 1;
				}
				if(Button.ENTER.isDown()) {
					p3 = 2;
				}
			}
			System.out.println("Palet p4 :");
			int p4 = 0;
			while(p4==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p4 = 1;
				}
				if(Button.ENTER.isDown()) {
					p4 = 2;
				}
			}
			System.out.println("Palet p5 :");
			int p5 = 0;
			while(p5==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p5 = 1;
				}
				if(Button.ENTER.isDown()) {
					p5 = 2;
				}
			}
			System.out.println("Palet p6 :");
			int p6 = 0;
			while(p6==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p6 = 1;
				}
				if(Button.ENTER.isDown()) {
					p6 = 2;
				}
			}
			System.out.println("Palet p7 :");
			int p7 = 0;
			while(p7==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p7 = 1;
				
				if(Button.ENTER.isDown()) {
					p7 = 2;
				}
			}
			System.out.println("Palet p8 :");
			int p8 = 0;
			while(p8==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p8 = 1;
				}
				if(Button.ENTER.isDown()) {
					p8 = 2;
				}
			}
			System.out.println("Palet p9 :");
			int p9 = 0;
			while(p9==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					p9 = 1;
				}
				if(Button.ENTER.isDown()) {
					p9 = 2;
				}
			}
			
		}
		
	}
		
		
		
		
		
		
		
		if(g==1) {
			//motor.rotate(360*5);
			//Delay.msDelay(2000);//2sec
			strategie1(45,-45);
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
