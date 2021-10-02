package Robot;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Pinces {
	
	RegulatedMotor p;
	
	public Pinces() {
		p = new EV3LargeRegulatedMotor(MotorPort.A);
	}
	
	/*
	 * methode permettant de fermer compl�tement les pinces
	 */
	public void fermer() {
		this.p.rotate(-3*360);
	}
	
	/*
	 * methode permettant d'ouvrir completement les pinces
	 */
	public void ouvrir() {
		this.p.rotate(3*360);
	}
	public static void main(String[] args) {
		WheelMotor m = new WheelMotor();
		Pinces pinces = new Pinces();
		pinces.fermer();
		m.forward(3000);
		pinces.ouvrir();

	}

}