import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MovePilot.*;


public class WheelMotor extends MovePilot{
	
	
	
	public WheelMotor() {	
		super(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
	}
	
	//oui
	
	public void forward(double i) {
		super.forward();
		Delay.msDelay((int)i);
		super.stop();
	}
	
	public void backward() {
		super.backward();
		Delay.msDelay(3000);
		super.stop();
	}
	
	public void arc(double rayon,double angle,boolean onsepas) {
		super.arc(rayon, angle, onsepas);
	}
	
	public static void arcDeCercle(WheelMotor m,double longueur,boolean droite) {
		if(droite == true) {
			m.rotate(-45);
			m.forward(longueur);
			m.rotate(45);
		}else {
			m.rotate(45);
			m.forward(longueur);
			m.rotate(-45);
		}
		
	}
	
	public static void main(String[] args) {
		WheelMotor m = new WheelMotor();
		m.forward(3000);
		arcDeCercle(m,1000,true);	
		m.forward(3000);
	}
	
		
}

