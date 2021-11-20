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
	public void fermer() {
		p.setSpeed(10000);
		this.p.rotate(-4*360);
	}
	
	/*
	 * methode permettant d'ouvrir completement les pinces
	 */
	public void ouvrir() {
		p.setSpeed(10000);
		this.p.rotate(4*360);
	}
	public static void main(String[] args) {
		WheelMotor m = new WheelMotor(1);
		Pinces pinces = new Pinces();
		pinces.fermer();
		//m.forward(3000);
		pinces.ouvrir();

	}

}
