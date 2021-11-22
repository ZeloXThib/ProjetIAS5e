package Robot;
import perception.TestThread;
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
	private static findPalet fp = new findPalet(sensor, motor, pince);
	
	
	public static void strategie1(int d, int d2) {
		motor.forward();
		pince.ouvrir();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<5000 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    motor.stop();
	    pince.fermer();
	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(380,false);
	    motor.rotate(d2,false);//-45 stratégie2
	    motor.forwardUntil("WHITE");
	    pince.ouvrir();
	    //motor.backward(100);
	    pince.fermer();
	    motor.backward(570);
	    if(d<0) {//ex -45,45
	    	motor.rotate(90);
	    }else {//ex 45,-45
	    	motor.rotate(-90);
	    }
	    pince.ouvrir();
	    motor.forward();
	    while(Sensor.havePalet()==0 && i<150 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    motor.stop();
	    pince.fermer();
	    if(d<0) {//ex -45,45
	    	motor.rotate(-90);
	    }else {//ex 45,-45
	    	motor.rotate(90);
	    }
	    motor.forwardUntil("WHITE");
	    pince.ouvrir();
	    //motor.backward(100);
	    pince.fermer();
	    motor.backward(300);
	    if(d<0) {//ex -45,45
	    	motor.rotate(90);
	    }else {//ex 45,-45
	    	motor.rotate(-90);
	    }
	    motor.forwardUntil("BLACK");
	    
	    if(d<0) {//ex -45,45
	    	motor.rotate(90);
	    }else {//ex 45,-45
	    	motor.rotate(-90);
	    }
	    
	}
	
	public static void strategie2(int d, int d2) {
		motor.forward();
		pince.ouvrir();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<5000 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    motor.stop();
	    pince.fermer();
	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(360,false);
	    motor.rotate(d2,false);//-45 stratégie2
	    motor.forwardUntil("WHITE");
	    pince.ouvrir();
	    pince.fermer();
	    motor.goTo(1000, 2100);
	    
	}

	
	public static void main(String[] args) {	
		int dev = 0;
		int g = 0;
		int numStrat = 0;
		int direction = 0;
		int placement = 0;
		int[] pValeur = new int[6];
		
		//motor.setLinearSpeed(1000);
		//motor.setAngularSpeed(1000);
	    //motor.setLinearAcceleration(80);
	    motor.setAngularAcceleration(120);
		
		
		//-----------------------------------------//
		//Etat des pinces
		//-----------------------------------------//
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
	Delay.msDelay(100);
	
	if(dev==2) {
		System.out.print("Pince: Ouvrire(G),RienFaire(C),Fermer(D)");
		double val_def = motor.getAngularSpeed();
		while(g==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				g = 1;
				
				motor.setAngularSpeed(200);
				pince.ouvrir();
				motor.setAngularSpeed(val_def);
			}	
			if(Button.ENTER.isDown()) {
				g = 2;
			}
			if(Button.RIGHT.isDown()) {
				g = 3;
				motor.setAngularSpeed(200);
				pince.fermer();
				motor.setAngularSpeed(val_def);
				
			}
		}
		Delay.msDelay(100);

		//-----------------------------------------//
		//Statégie 1, 2 ou 3
		//-----------------------------------------//
				
		System.out.println("Statégie 1, 2 ou 3");
		
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
		Delay.msDelay(100);
		
		
		//-----------------------------------------//
		//D'où part le robot ? Gauche ; Milieu : Droite
		//-----------------------------------------//
		
		System.out.println("D'où part le robot ? Gauche ; Milieu : Droite");
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
		Delay.msDelay(100);
		
		
		//-----------------------------------------//
		//Départ à gauche ou à droite ? 1=Gauche ; 2=Droite
		//-----------------------------------------//
		if((numStrat==1 && placement==2) || numStrat==2){
			System.out.println("Départ à gauche ou à droite ? 1=Gauche ; 2=Droite");
			
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
		Delay.msDelay(100);
		
		//-----------------------------------------//
		//Quel palet est présent sur le terrain (au bon endroit):
		//-----------------------------------------//
		
		if(numStrat==3) {
			System.out.println("Quel palet est présent sur le terrain (au bon endroit):");
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
				p1=0;
			}
		}
	}else {
		
		fp.scan();

		Delay.msDelay(3000);
		//pour nous mode dev 	

//		while(Button.ENTER.isDown()==false) {
//			if(Button.RIGHT.isDown())
//				motor.rotate(270,false);
//			if(Button.LEFT.isDown())
//				motor.rotate(90,false);
//			if(Button.DOWN.isDown())
//				motor.rotate(180,false);
//			if(Button.UP.isDown())
//				motor.rotate(360,false);
			//motor.rotate(120);
			//motor.rotate(60);
		

		
		
		//Code dev
		/*
		motor.forward();
		new Thread(new Runnable() {
			public void run() {
				while(Button.ENTER.isDown() ==false) {
					Color rgb = sensor.getColorOnGround();
					System.out.println(sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()));
					Delay.msDelay(20);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				if(Button.ENTER.isDown()) {
					motor.stop();
					System.exit(0);
					return;
				}
			}
		}).start();

		motor.setLongueur(300);
		motor.setLargeur(1500);
		motor.goTo(1000, 2100);
		//motor.forward(1868, false);
		//fp.paletTrouve(motor);
		*/
	}
	
	
	
			/////////////////////////////////////////////////////////////////////////////////
			//																				/
			//																				/
			//								  NumStrat1 									/
			//																				/
			//																				/
			/////////////////////////////////////////////////////////////////////////////////


		
	



		if(numStrat==1) {//Tous les palets sont présents sur la table
			if(placement == 1) {//Strat 1 et placement a gauche
				Delay.msDelay(100);
				strategie1(45,-45);//Direction gauche
			}
			else if (placement == 2) {//Strat 1 et placement au millieu
				if(direction == 1) {
					Delay.msDelay(100);
					strategie1(45,-45);//Direction gauche
				}
				else if(direction == 2) {//Strat 1 et placement au millieu
					Delay.msDelay(100);
					strategie1(-45,45);//Direction droite
				}
			}
			else {//Strat 1 et placement a droite
				Delay.msDelay(100);
				strategie1(-45,45);//Direction droite
			}
			
		}else if(numStrat==2) {
		
			/////////////////////////////////////////////////////////////////////////////////
			//																				/
			//																				/
			//								  NumStrat2 									/
			//																				/
			//																				/
			/////////////////////////////////////////////////////////////////////////////////
			
			if(placement == 1) {//Strat 2 et placement a gauche
				Delay.msDelay(100);
				strategie2(45,-45);//Direction gauche
			}
			else if (placement == 2) {//Strat 2 et placement au millieu
				if(direction == 1) {
					Delay.msDelay(100);
					strategie2(45,-45);//Direction gauche
				}
				else if(direction == 2) {//Strat 2 et placement au millieu
					Delay.msDelay(100);
					strategie2(-45,45);//Direction droite
				}
			}
			else {//Strat 2 et placement a droite
				Delay.msDelay(100);
				strategie2(-45,45);//Direction droite
			}
			
			
			
			/////////////////////////////////////////////////////////////////////////////////
			//																				/
			//																				/
			//								  NumStrat3 									/
			//																				/
			//																				/
			/////////////////////////////////////////////////////////////////////////////////
			
			
		
		}else if(numStrat==3) {
			int[] tabGoTo = {600,900,1200,1500,1800};
			int choix=0;
			int indice=0;
			for(int i = 0;i<tabGoTo.length&&indice==0;i++) {
				int test = 0;
				System.out.println("Distance : " + tabGoTo[i]);
				while(test==0) {
					if(Button.RIGHT.isDown()) {
						test=1;
					}
					else if(Button.LEFT.isDown()) {
						test=2;
						choix = tabGoTo[i];
						indice = i;
					}
				}	
			}
			int[] modif = new int[6-indice];
			for(int f=indice;indice<=modif.length;f++) {
				modif[f]=tabGoTo[f];
			}
			motor.goTo(1000, modif[indice]);
			
			/*
			motor.setAngularSpeed(120);
			motor.rotate(360,true);
			System.out.println(sensor.getDistance());
			while(Button.ENTER.isDown()==false) {
				System.out.println(sensor.getDistance());
				Delay.msDelay(500);
			}*/
			
		}
	}

}