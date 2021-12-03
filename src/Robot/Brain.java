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
	/**
	 * class principale qui gere les differentes strategies
	 */
	
	
	private static Sensor sensor = new Sensor();
	private static WheelMotor motor = new WheelMotor(1, sensor);
	private static Pinces pince = new Pinces();
	private static final double DIST_MAX = 0.65;
	private static findPalet fp = new findPalet(sensor, motor, pince, DIST_MAX, 0);


	/**
	 * methode strategie1 qui est appelee au depart du round lorsqu tous les palets sont la
	 * @param d va prendre 45 ou -45 en fonction de l'endroit de depart 
	 * @param d2 va prendre 45 ou -45 en fonction de l'endroit de depart 
	 * @param placement va prendre 0,1,2 en fonction de si il est a gauche au mileu ou a droite 
	 * @param angle angle vers lequel s'orienter pour trouver le 2 eme palet (155 ou -155)
	 */
	public static void strategie1(int d, int d2, int placement, double angle) {
		double dist = 0;
		motor.setLinearSpeed(motor.getMaxLinearSpeed()-50);
		motor.forward();
		pince.ouvrir(false);
	    while(Sensor.havePalet()==0 ) {
	    }
	    double val_speed = motor.getLinearSpeed();
	    dist = motor.getMovement().getDistanceTraveled();
	    motor.stop();
	    motor.mettre_a_jour_longueur_largeur(dist);
	    pince.fermer(true);

	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(400,false);
	    motor.mettre_a_jour_longueur_largeur(400);
	    motor.rotate(d2,false);//-45 stratégie2
	    System.out.println("Largeur "+motor.getLargeur());
	    System.out.println("Longeur "+motor.getLongueur());
	   
	    motor.forwardUntil("WHITE");
	    System.out.println("Largeur "+motor.getLargeur());
	    System.out.println("Longeur "+motor.getLongueur());
	    
	    dist = motor.getMovement().getDistanceTraveled();
	    motor.stop();
	    motor.mettre_a_jour_longueur_largeur(dist);
	    motor.setLongueur(2700-35);
	    motor.setLinearSpeed(val_speed);
	    pince.ouvrir(false);
	    //motor.backward(100);
	    pince.fermer(false);

	   // 
	    motor.rotateEnFonctionBoussole(angle);
	    System.out.println(motor.getLargeur());
	    motor.forward();
		pince.ouvrir(false);
	    while(Sensor.havePalet()==0 ) {
	    }
	    dist = motor.getMovement().getDistanceTraveled();
	    motor.stop();
	    motor.mettre_a_jour_longueur_largeur(dist);
	    pince.fermer(true);
	    motor.rotateEnFonctionBoussole(0);
	    System.out.println(motor.getLargeur());
	    motor.forwardUntil("WHITE"); 
	    dist = motor.getMovement().getDistanceTraveled();
	    motor.stop();
	    motor.mettre_a_jour_longueur_largeur(dist);
	    System.out.println("Largeur "+motor.getLargeur());
	    System.out.println("Longeur "+motor.getLongueur());
	    motor.setLongueur(2700-35);
	    pince.ouvrir(false);
	    pince.fermer(false);
	    //
	    motor.boussole_a_0();
	    motor.afficheLargeur();
	    motor.afficheLongueur();
	    
	    fp.mettre_a_jour_largeur();
	    motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
	    fp.marquer_palet(180, 40);


	}
	/**
	 * methode strategie1 qui est appelee apres la pause du round lorsqu'au moins encore un palet est bien plac�
	 * @param d d va prendre 45 ou -45 en fonction de l'endroit de depart 
	 * @param d2 va prendre 45 ou -45 en fonction de l'endroit de depart
	 * @param placement va prendre 0,1,2 en fonction de si il est a gauche au mileu ou a droite 
	 * @param angle angle vers lequel s'orienter pour trouver le 2 eme palet (155 ou -155)
	 */
	public static void strategie2(int d, int d2, int placement, double angle) {
		double dist = 0;
		motor.setLinearSpeed(motor.getMaxLinearSpeed()-50);
		motor.forward();
		pince.ouvrir(false);
	    while(Sensor.havePalet()==0 ) {
	    }
	    double val_speed = motor.getLinearSpeed();
	    dist = motor.getMovement().getDistanceTraveled();
	    motor.stop();
	    motor.mettre_a_jour_longueur_largeur(dist);

	    pince.fermer(true);

	    motor.rotate(d,false);//45 stratégie1
	    motor.forward(400,false);
	    motor.mettre_a_jour_longueur_largeur(400);
	    motor.rotate(d2,false);//-45 stratégie2
	    System.out.println("Largeur "+motor.getLargeur());
	    System.out.println("Longeur "+motor.getLongueur());
	    motor.forwardUntil("WHITE");
	    dist = motor.getMovement().getDistanceTraveled();
	    motor.stop();
	    motor.mettre_a_jour_longueur_largeur(dist);
	    motor.setLongueur(2700-35);
	    motor.setLinearSpeed(val_speed);
	    pince.ouvrir(false);
	    //motor.backward(100);
	    pince.fermer(false);
	    motor.boussole_a_0();
	    motor.afficheLargeur();
	    motor.afficheLongueur();
	    
	    fp.mettre_a_jour_largeur();
	    motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
	    fp.marquer_palet(180, 40);
	    
		
		
		

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
				pince.ouvrir(false);
				motor.setAngularSpeed(val_def);
			}
			if(Button.ENTER.isDown()) {
				g = 2;
			}
			if(Button.RIGHT.isDown()) {
				g = 3;
				motor.setAngularSpeed(200);
				pince.fermer(false);
				motor.setAngularSpeed(val_def);

			}
		}
		Delay.msDelay(100);

		//-----------------------------------------//
		//Statégie 1, 2 ou 3
		//-----------------------------------------//

		System.out.println("Stategie 1, 2 ou 3");

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
			System.out.println("D'ou part le robot ? Gauche ; Milieu : Droite");
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
			System.out.println("Depart a gauche ou a droite ? 1=Gauche ; 2=Droite");

			while(direction==0){
				Delay.msDelay(10);
				if(Button.LEFT.isDown()) {
					direction = 1;
				}
				if(Button.RIGHT.isDown()) {
					direction = 2;
				}
			}
		}
		Delay.msDelay(100);

	}else {


		System.out.print("Pince: Ouvrire(G),RienFaire(C),Fermer(D)");
		double val_def = motor.getAngularSpeed();
		while(g==0){
			Delay.msDelay(10);
			if(Button.LEFT.isDown()) {
				g = 1;

				motor.setAngularSpeed(200);
				pince.ouvrir(false);
				motor.setAngularSpeed(val_def);
			}
			if(Button.ENTER.isDown()) {
				g = 2;
			}
			if(Button.RIGHT.isDown()) {
				g = 3;
				motor.setAngularSpeed(200);
				pince.fermer(false);
				motor.setAngularSpeed(val_def);

			}

		}	
		

		double dist =0;
		motor.setLargeur(1000);
		motor.setLongueur(2700);
		motor.rotateEnFonctionBoussole(180);
		motor.forward();
		Delay.msDelay(3000);
		dist = motor.getMovement().getDistanceTraveled();
		motor.stop();
		motor.mettre_a_jour_longueur_largeur(dist);
		motor.goTo(1000, 2700);

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
				strategie1(45,-45,1,-158);//Direction gauche


				while(true) {
					motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
					fp.marquer_palet(190, 40);
				}

			}
			else if (placement == 2) {//Strat 1 et placement au millieu
				if(direction == 1) {
					Delay.msDelay(100);
					strategie1(45,-45,2,-158);//Direction gauche

					
					while(true) {
						motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
						fp.marquer_palet(190, 40);
					}

				}
				else if(direction == 2) {//Strat 1 et placement au millieu
					Delay.msDelay(100);
					strategie1(-45,45,2,158);//Direction droite

					
					while(true) {
						motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
						fp.marquer_palet(190, 40);
					}

				}
			}
			else {//Strat 1 et placement a droite
				Delay.msDelay(100);
				strategie1(-45,45,3,158);//Direction droite

				
				while(true) {
					motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
					fp.marquer_palet(190, 40);
				}

			}

		}else if(numStrat==2) {
			System.out.println("Je suis dans la strat 2 MTF");
			/////////////////////////////////////////////////////////////////////////////////
			//																				/
			//																				/
			//								  NumStrat2 									/
			//																				/
			//																				/
			/////////////////////////////////////////////////////////////////////////////////

			if(placement == 1) {//Strat 2 et placement a gauche
				Delay.msDelay(100);
				strategie2(45,-45,1,-158);//Direction gauche
				while(true) {
					motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
					fp.marquer_palet(190, 40);
				}
			}
			else if (placement == 2) {//Strat 2 et placement au millieu
				if(direction == 1) {
					Delay.msDelay(100);
					strategie2(45,-45,2,-158);//Direction gauche
					while(true) {
						motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
						fp.marquer_palet(190, 40);
					}
				}
				else if(direction == 2) {//Strat 2 et placement au millieu
					Delay.msDelay(100);
					strategie2(-45,45,2,158);//Direction droite
					while(true) {
						motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
						fp.marquer_palet(190, 40);
					}
				}
			}
			else {//Strat 2 et placement a droite
				Delay.msDelay(100);
				strategie2(-45,45,3,158);//Direction droite
				while(true) {
					motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
					fp.marquer_palet(190, 40);
				}
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
						
						indice = i;
					}
				}
				Delay.msDelay(1000);
			}
			
			motor.goTo(1000, tabGoTo[indice]);
			fp.marquer_palet(190, 40);
			while(true) {
				motor.goTo(fp.gotoScanPoint()[0], fp.gotoScanPoint()[1]);
				fp.marquer_palet(190, 40);
			}

		

		}
	}
}

