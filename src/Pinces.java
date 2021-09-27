import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Pinces {
	
	RegulatedMotor p;
	
	public Pinces() {
		p = new EV3LargeRegulatedMotor(MotorPort.A);
	}
	
	public void fermer() {
		this.p.rotate(-3*360);
	}
	
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
