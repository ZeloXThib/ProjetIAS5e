package Robot;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Brain {
		
	public static void main(String[] args) {
		
		// AVANCER TANT QUE COULEUR != BLANC
		
		
		Sensor sensor = new Sensor();
		MovePilot pilot = new MovePilot(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
		Pinces pince = new Pinces();
		
		System.out.print("ESt-ce que la pinces est ferm� ?: Oui(G),Non(D)");
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
		    pilot.forward();
		    int i = 0;
		    while(Sensor.havePalet()==0 && i<4000 ){
		    	Delay.msDelay(10);
		    	i++;
		    }
		    Delay.msDelay(1000);
		    pilot.stop();
		    pince.fermer();
		    pilot.rotate(45);
		    pilot.forward();
		    Delay.msDelay(1700);
		    pilot.stop();
		    Delay.msDelay(1700);
		    pilot.rotate(-45);
		    pilot.forward();
		    
		    Color rgb = Sensor.getColorOnGround();
		    while(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) != "WHITE") {
		    	rgb = Sensor.getColorOnGround();
		    }
		    Delay.msDelay(50);
		    pilot.stop();
		    pince.ouvrir();
		    
		}else if(g==2) {
		    System.out.println(">Choix 2");
		}else if(g==3) {
		    System.out.println(">Choix 3");
		    
		}
		
	   	
	    Delay.msDelay(10000);
	    
	}

}