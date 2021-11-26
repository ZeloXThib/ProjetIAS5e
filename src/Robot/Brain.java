package Robot;
//import perception.TestThread;
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
	private static WheelMotor motor = new WheelMotor(1, sensor);
	private static Pinces pince = new Pinces();
	private static final double DIST_MAX = 0.65;
	private static findPalet fp = new findPalet(sensor, motor, pince, DIST_MAX, 0);

	public static void strategie1(int d, int d2, int placement) {
		motor.setLinearSpeed(motor.getMaxLinearSpeed()-50);
		motor.forward();
		pince.ouvrir();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<600 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    double val_speed = motor.getLinearSpeed();
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();
	    pince.fermer();
	    
	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(385,false);
	    motor.rotate(d2,false);//-45 stratégie2
	    motor.setLinearSpeed(motor.getMaxLinearSpeed()-50);
	    motor.forwardUntil("WHITE");
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();
	    motor.setLinearSpeed(val_speed);
	    pince.ouvrir();
	    //motor.backward(100);
	    pince.fermer();
	    motor.backward(570);
	    if(d<0) {//ex -45,45
	    	motor.rotate(90);
	    }else {//ex 45,-45
	    	motor.rotate(-90);
	    }
	    motor.backward(90);
	    pince.ouvrir();
	    motor.forward();
	    i = 0;
	    while(Sensor.havePalet()==0 && i<80 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();
	    pince.fermer();
	    
	    if(d<0) {//ex -45,45
	    	motor.rotate(-90);
	    }else {//ex 45,-45
	    	motor.rotate(90);
	    }
	    motor.forwardUntil("WHITE");
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();
	    pince.ouvrir();
	    //motor.backward(100);
	    pince.fermer();
	    motor.backward(300);
	    	
	    if(d<0) {//ex -45,45
	    		motor.rotate(90);
	    }else {//ex 45,-45
	    		motor.rotate(-90);
	    }
	    
	    
	    if(placement == 2) {
	    		motor.backward(200);
	    }
	    	
	    	
	    motor.forwardUntil("BLACK");
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();

	   	if(d<0) {//ex -45,45
	    		motor.rotate(90);
	   	}else {//ex 45,-45
	    		motor.rotate(-90);
	   	}
	    	
	    motor.backwardUntil("WHITE");
	    motor.mettre_a_jour_longueur_largeur_backward(motor.getMovement().getDistanceTraveled());
	    motor.stop();
	    motor.forward(100,false);
	    fp.marquer_palet(140, 50);


	}

	public static void strategie2(int d, int d2) {
		motor.forward();
		pince.ouvrir();
	    int i = 0;
	    while(Sensor.havePalet()==0 && i<5000 ){
	    	Delay.msDelay(10);
	    	i++;
	    }
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();
	    pince.fermer();
	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(360,false);
	    motor.rotate(d2,false);//-45 stratégie2
	    motor.forwardUntil("WHITE");
	    motor.mettre_a_jour_longueur_largeur(motor.getMovement().getDistanceTraveled());
	    motor.stop();
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

		//motor.setLinearSpeed(motor.getMaxLinearSpeed()-50);
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
		if(numStrat != 3) {
			System.out.println("D'où part le robot ? Gauche ; Milieu : Droite");
			while(placement==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					placement = 1;
					motor.setLargeur(500);
				}
				if(Button.ENTER.isDown()) {
					placement = 2;
					motor.setLargeur(1000);
				}
				if(Button.RIGHT.isDown()) {
					placement = 3;
					motor.setLargeur(1500);
				}
			}
			Delay.msDelay(100);
		}else {
			//depart tojours au millieu
			motor.setLargeur(1000);
		}


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

	}else {
		pince.fermer();
		while(Button.ENTER.isDown()==false) {
			if(Button.DOWN.isDown()==true) {
				motor.boussole_a_0();
			}
		}
			
		/*
		System.out.println("largeur de base : " + motor.getLargeur());
		System.out.println("longueur de base : " + motor.getLongueur());
			
		//partie 1 :
			System.out.println("Partie 1");
			double[] d = fp.gotoScanPoint();
			System.out.println("largeur ou il va" + d[0]);
			System.out.println("longueur ou il va" + d[1]);
			//doit afficher 1000 , 2700
			Delay.msDelay(3000);
			motor.goTo(d[0], d[1]);
			System.out.println("largeur ou il est " + motor.getLargeur());
			System.out.println("longueur ou il est " + motor.getLongueur());
			//doit afficher 1000 , 2700
			Delay.msDelay(3000);
			motor.rotateEnFonctionBoussole(180);
			fp.marquer_palet(180, 40);
			Delay.msDelay(2000);
			//palet trouve donc on doit retourner en 1000 , 2700
			
		//partie 2 
			System.out.println("Partie 2");
			double[] e = fp.gotoScanPoint();
			System.out.println("largeur" + e[0]);
			System.out.println("longueur" + e[1]);
			//doit afficher 1000, 2700
			Delay.msDelay(2000);
			motor.goTo(e[0], e[1]);
			System.out.println(motor.getLargeur());
			System.out.println(motor.getLongueur());
			//doit afficher 1000, 2700
			Delay.msDelay(2000);
			motor.rotateEnFonctionBoussole(180);
			fp.marquer_palet(180, 40);
			Delay.msDelay(2000);
			//pas de palet donc on va au deuxieme point de scan 
			
		//partie 3	
			System.out.println("Partie 3");
			double[] t = fp.gotoScanPoint();
			System.out.println("largeur" + t[0]);
			System.out.println("longueur" + t[1]);
			//doit afficher 1000, 2100
			Delay.msDelay(2000);
			motor.goTo(t[0], t[1]);
			System.out.println(motor.getLargeur());
			System.out.println(motor.getLongueur());
			////doit afficher 1000, 2100
			Delay.msDelay(2000);
			motor.rotateEnFonctionBoussole(180);
			fp.marquer_palet(180, 40);
			Delay.msDelay(2000);
			//palet trouve
		*/
		}


		//Code dev
		
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
				strategie1(45,-45,1);//Direction gauche
			}
			else if (placement == 2) {//Strat 1 et placement au millieu
				if(direction == 1) {
					Delay.msDelay(100);
					strategie1(45,-45,2);//Direction gauche
				}
				else if(direction == 2) {//Strat 1 et placement au millieu
					Delay.msDelay(100);
					strategie1(-45,45,2);//Direction droite
				}
			}
			else {//Strat 1 et placement a droite
				Delay.msDelay(100);
				strategie1(-45,45,3);//Direction droite
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
				System.out.println(test);
				System.out.println("Distance : " + tabGoTo[i]+" Non(G) && OUI(D)");
				while(test==0) {
					if(Button.LEFT.isDown()) {
						test=1;
					}
					else if(Button.RIGHT.isDown()) {
						test=2;
						choix = tabGoTo[i];
						indice = i;
					}
				}
				Delay.msDelay(1000);
			}
			/*
			int[] modif = new int[6-indice];
			for(int f=indice;indice<=modif.length;f++) {
				modif[f]=tabGoTo[f];
			}
			*/
			motor.goTo(1000, tabGoTo[indice]);
			fp.scan(100);
			motor.goTo(1000, tabGoTo[tabGoTo.length-1]);
			fp.scan(180);

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

