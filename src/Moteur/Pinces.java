package Moteur;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Pinces {
	/**
	 * class qui gere le moteur lié aux pinces 
	 * @attribut p : moteur des pinces 
	 */
	RegulatedMotor p;
	
	/**
	 * constructeur de la class
	 */
	public Pinces() {
		p = new EV3LargeRegulatedMotor(MotorPort.A);
	}
	
	/**
	 * methode permettant de fermer complètement les pinces
	 * @param t : permet de savoir si on rend la main ou non pendant la fermeture
	 */
	public void fermer(boolean t) {
		p.setSpeed(10000);
		this.p.rotate(-4*360,t);
	}
	
	/**
	 * methode permettant d ouvrir complètement les pinces
	 * @param t : permet de savoir si on rend la main ou non pendant l ouverture
	 */
	public void ouvrir(boolean t) {
		p.setSpeed(10000);
		this.p.rotate(4*360,t);
	}
	

}
