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
	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(300,false);
	    motor.rotate(d2);//-45 stratégie2
	    motor.forwardUntilWhite();
	    pince.ouvrir();
	}

	
	public static void main(String[] args) {	
		
		
		//-----------------------------------------//
		//Etat des pinces
		//-----------------------------------------//
	int dev = 0;
	System.out.print("ModeDev ? (Gauche:Oui,D:Non):");
	while(dev==0){
		Delay.msDelay(10);
		if(Button.LEFT.isDown()) {
			dev = 1;
		}	
		if(Button.RIGHT.isDown()) {
			dev = 2;
			
		}
	}
	
	if(dev==2) {
		System.out.print("Est-ce que la pinces est ouverte ?: Oui(G),Non(D)");
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
		//Statégie 1, 2 ou 3
		//-----------------------------------------//
				
		System.out.println("Statégie 1, 2 ou 3");
		int numStrat = 0;
		while(numStrat==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				numStrat = 1;
				/*(Stratégie 1) Tous les palets sont présents sur la table
				 * 
				 * (Strat 1; Gauche; Gauche)

				(Strat 1; Milieu; Gauche) 
				
				(Strat 1; Milieu; Droite)
				
				(Strat 1; Droite; Droite) 
				*/
			}
			if(Button.ENTER.isDown()) {
				numStrat = 2;
			}
			if(Button.RIGHT.isDown()) {
				numStrat = 3;
			}
		}
		
		
		//-----------------------------------------//
		//D'où part le robot ? Gauche ; Milieu : Droite
		//-----------------------------------------//
		
		System.out.println("D'où part le robot ? Gauche ; Milieu : Droite");
		int placement = 0;
		while(placement==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				placement = 1;
			}
			if(Button.ENTER.isDown()) {
				placement = 2;
			}
			if(Button.RIGHT.isDown()) {
				placement = 3;
			}
		}
		
		
		//-----------------------------------------//
		//Départ à gauche ou à droite ? 1=Gauche ; 2=Droite
		//-----------------------------------------//
		
		if(numStrat==1 || numStrat==2){
			System.out.println("Départ à gauche ou à droite ? 1=Gauche ; 2=Droite");
			int direction = 0;
			while(direction==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					direction = 1;
				}
				if(Button.ENTER.isDown()) {
					direction = 2;
				}
			}
		}
		
		//-----------------------------------------//
		//Quel palet est présent sur le terrain (au bon endroit):
		//-----------------------------------------//
		
		if(g==3) {
			System.out.println("Quel palet est présent sur le terrain (au bon endroit):");
			int[] pValeur = new int[6];
			for(int i=0; i<pValeur.length;i++) {
				int p1 = 0;
				System.out.println("Palet p"+(i+1)+": G(oui),D(non)");
				while(p1==0){
					Delay.msDelay(10);
					if(Button.LEFT.isDown()) {
						p1 = 1;
					}
					if(Button.RIGHT.isDown()) {
						p1 = 2;
					}
				}
				pValeur[i]=p1;
			}
		}
	}else {
		//Code dev
		
		
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

