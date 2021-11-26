package Moteur;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Pinces {
	
	RegulatedMotor p;
	
	public Pinces() {
		p = new EV3LargeRegulatedMotor(MotorPort.A);
	}
	
	/*
	 * methode permettant de fermer complètement les pinces
	 */
	public void fermer(boolean t) {
		p.setSpeed(10000);
		this.p.rotate(-4*360,t);
	}
	
	/*
	 * methode permettant d'ouvrir completement les pinces
	 */
	public void ouvrir(boolean t) {
		p.setSpeed(10000);
		this.p.rotate(4*360,t);
	}
	public static void main(String[] args) {
		
		Pinces pinces = new Pinces();
		pinces.fermer(false);
		//m.forward(3000);
		pinces.ouvrir(false);

	}

}
