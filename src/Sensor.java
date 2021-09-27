import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;


public class Sensor {
	
	private Port port_Ultrasound;
	private Port port_Pression;
	private Port port_S_Color;
	private static SensorModes sensor_Ultrasound = null;
	private static SensorModes sensor_Pression = null;
	private static SensorModes sensor_S_Color = null;
	static GraphicsLCD g = null;
	
	public Sensor(String port_Ultrasound_s, String port_Pression_s, String port_Color_s) {
		// get a port instance
		port_Ultrasound = LocalEV3.get().getPort(port_Ultrasound_s);
		sensor_Ultrasound = new EV3UltrasonicSensor(port_Ultrasound);
		
		port_Pression = LocalEV3.get().getPort(port_Pression_s);
		sensor_Pression = new EV3UltrasonicSensor(port_Pression);
		
		port_S_Color = LocalEV3.get().getPort(port_Color_s);
		sensor_S_Color = new EV3UltrasonicSensor(port_S_Color);
	}
	
	public Sensor() {
		this("S1","S3","S4");//Par default S1
	}
	

	
	static public void affiche(String text) {
		if(g == null) {
			g = BrickFinder.getDefault().getGraphicsLCD();
		}		
		g.clear();
		g.drawString(text, 0, 0, GraphicsLCD.VCENTER|GraphicsLCD.LEFT);
	}
	
	
	public static float getDistance() {	

		// get an instance of this sensor in measurement mode
		SampleProvider distance_s= sensor_Ultrasound.getMode("Distance");

	    // initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		float[] sample = new float[distance_s.sampleSize()];

				
	    distance_s.fetchSample(sample, 0);
		
		
		return sample[0];
		
		
	}
	
	public static float havePalet() {
		
		// get an instance of this sensor in measurement mode
		SampleProvider distance_s = sensor_Pression.getMode(0);

	    // initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		float[] sample = new float[distance_s.sampleSize()];

				
	    distance_s.fetchSample(sample, 0);
		
		
		return sample[0];
		
		
	}
	
	public static Color getColorOnGround() {
		
		
		// get an instance of this sensor in measurement mode
		sensor_S_Color.setCurrentMode("RGB");
		
	    // initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		float[] sample = new float[sensor_S_Color.sampleSize()];

		
		sensor_S_Color.fetchSample(sample, 0);
		
		return new Color((int)(sample[0] * 255), (int)(sample[1] * 255), (int)(sample[2] * 255));
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		affiche("Hello");
		//Delay.msDelay(5000);
		
		
		Sensor Sensor_OBJ = new Sensor("S1","S3","S4");
		affiche("Hello2");
		//Delay.msDelay(5000);
		
		
		Sensor.getDistance();
		//Motor
		MovePilot chassi = new MovePilot(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
		//chassi.forward();
		
		
		/*
		int i = 0;
		while((Sensor.getDistance() > 0.2) && (i<3000)){//0,2 m = 20 cm
			//Do nothing
			i++;
			affiche(i+": "+Sensor.getDistance());
	    	Delay.msDelay(10);
		}
		
		
		affiche("Detecte... "+Sensor.getDistance());
		chassi.stop();
		Delay.msDelay(10000);
		
		/*
		
		Sensor.havePalet();
		int i = 0;
		while((Sensor.havePalet() < 0.5) && (i<3000)){
			//Do nothing
			i++;
			affiche(i+" : "+ Sensor.havePalet());
	    	Delay.msDelay(10);
		}
		
		
		affiche("Detecte... "+Sensor.havePalet());
		chassi.stop();
		Delay.msDelay(10000);
		*/
		

		
		Sensor.getColorOnGround();
		Sensor.havePalet(); //Pour stop
		int i = 0;
		while((Sensor.havePalet() < 0.5) && (i<5000)){//4000 40sec
			//Do nothing
			i++;
			Color rgb = Sensor.getColorOnGround();
			//Lcd.print(6, "r=%d g=%d b=%d", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
			affiche(i+" : C: "+ rgb.getRed()+ "," + rgb.getGreen() + "," +  rgb.getBlue());
	    	Delay.msDelay(10);
		}
		//Color code
		//Gris table:
		//Orange 
		
		Color rgb = Sensor.getColorOnGround();
		affiche("Detecte... "+ rgb.getRed()+ "," + rgb.getGreen() + "," +  rgb.getBlue());
		//chassi.stop();
		Delay.msDelay(10000);
		
	}

}
