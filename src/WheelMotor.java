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
	public void forward() {
		super.forward();
		Delay.msDelay(3000);
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
		
}

