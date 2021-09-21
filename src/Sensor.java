import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Sensor {
	
	private String port;
	SensorModes sensor = null;
	static GraphicsLCD g = null;
	
	public Sensor(String port_) {
		port = port_;
	}
	
	public Sensor() {
		this("S1");//Par default S1
	}
	
	
	public void setPort(String port_) {
		this.port = port_;
	}
	
	static public void affiche(String text) {
		if(g == null) {
			g = BrickFinder.getDefault().getGraphicsLCD();
		}		
		g.clear();
		g.drawString(text, 0, 0, GraphicsLCD.VCENTER|GraphicsLCD.LEFT);
	}
	
	
	public float getDistance() {
		
		
		// get a port instance
		Port port_ = LocalEV3.get().getPort(this.port);

		if(sensor == null) {
			// Get an instance of the Ultrasonic EV3 sensor
			sensor = new EV3UltrasonicSensor(port_);
		}		

		// get an instance of this sensor in measurement mode
		SampleProvider distance_s= sensor.getMode("Distance");

	    // initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		float[] sample = new float[distance_s.sampleSize()];

				
	    distance_s.fetchSample(sample, 0);
		
		
		return sample[0];
		
		
	}
	
	public float havePalet() {
		
		
		// get a port instance
		Port port_ = LocalEV3.get().getPort(this.port);

		if(sensor == null) {
			// Get an instance of the Ultrasonic EV3 sensor
			sensor = new EV3TouchSensor(port_);
		}

		// get an instance of this sensor in measurement mode
		SampleProvider res= sensor.getMode("Touch");

	    // initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		float[] sample = new float[res.sampleSize()];

				
	    res.fetchSample(sample, 0);
		
		
		return sample[0];
		
		
	}
	
	
	public static void main(String[] args) {
		
		affiche("Hello");
		Delay.msDelay(500);
		
		
		Sensor UltraSonic = new Sensor("S1");
		Sensor ButtonPression = new Sensor("S3");
		affiche("Hello2");
		Delay.msDelay(500);
		/*
		int i = 0;
		while((UltraSonic.getDistance() > 0.2) && (i<20)){//0,2 m = 20 cm
			//Do nothing
			i++;
			affiche(i+"");
	    	Delay.msDelay(1000);
		}
		
		
		affiche("Detecte... "+UltraSonic.getDistance());
		Delay.msDelay(5000);
		*/
		
		int i = 0;
		while((ButtonPression.havePalet() > 0.5) && (i<20)){//0,2 m = 20 cm
			//Do nothing
			i++;
			affiche(i+" : "+ ButtonPression.havePalet());
	    	Delay.msDelay(1000);
		}
		
		
		affiche("Detecte... "+ButtonPression.havePalet());
		Delay.msDelay(5000);
		
	}

}
