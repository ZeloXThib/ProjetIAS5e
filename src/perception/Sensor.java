package perception;
import java.util.ArrayList;
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
	private ArrayList<Integer> yellow = new ArrayList<Integer>();
	private ArrayList<Integer> red = new ArrayList<Integer>();
	
	public Sensor(String port_Ultrasound_s, String port_Pression_s, String port_Color_s) {
		// get a port instance
		port_Ultrasound = LocalEV3.get().getPort(port_Ultrasound_s);
		sensor_Ultrasound = new EV3UltrasonicSensor(port_Ultrasound);
		
		port_Pression = LocalEV3.get().getPort(port_Pression_s);
		sensor_Pression = new EV3TouchSensor(port_Pression);
		
		port_S_Color = LocalEV3.get().getPort(port_Color_s);
		sensor_S_Color = new EV3ColorSensor(port_S_Color);
	}
	
	
	public Sensor() {
		this("S1","S3","S4");//Par default S1
	}
	

	
	static public void affiche(String text) {
		System.out.println(text);
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
	
	//Tourner sur lui meme lire les valeurs du capteur ultrasonic
	
	
	public static float havePalet() {
		
		// get an instance of this sensor in measurement mode
		SampleProvider presion = sensor_Pression.getMode(0);

	    // initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be


		float[] sample = new float[presion.sampleSize()];			

		presion.fetchSample(sample, 0);
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
	
	public static String Color_to_String(int r, int g, int b) {
		if((r<6 && g<6 && b<6)) {
			return "BLACK";
		}else if((r>8 && g>8 && b>8) && (r<27 && g<27 && b<27)) {
			return "GRAY";
		}else if((r>32 && g>22 && b<12)) {
			return "YELLOW";
		}else if(r>25 && g>25 && b>25) {
			return "WHITE";
		}else if(r>g && r>b) {
			return "RED";
		}else if(g>r && g>b) {
			return "GREEN";
		}else if(b>r && b>g) {
			return "BLUE";
		}else {
			return "NONE";
		}
		
		//JAUNE
		//37 26 8
		// 35 25 8
		
		//NOIR
		//2 3 3
		// 3 4 4
		
		//GRIS
		//12 12 1
		//13 12 1
		///////10 10 1
		
		//BLUE
		//2 5 11
		//2 4 11
	}
	
	public static boolean colorIsWisWHITE() {
		Color rgb = Sensor.getColorOnGround();
		return Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) == "WHITE";
	}
	
	public boolean yellow() {
		Color rgb = Sensor.getColorOnGround();
		if(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) == "YELLOW") {
			yellow.add(1);
			if(is_yellow())
				return true;
			return false;
		}
		else {
			yellow.clear();
			return false;
		}
	}
	
	public boolean red() {
		Color rgb = Sensor.getColorOnGround();
		if(Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()) == "RED") {
			red.add(1);
			if(is_red()) {
				return true;
			}
			return false;
		}
		else {
			red.clear();
			return false;
		}
	
	}
	
	public boolean is_yellow() {
		if(yellow.size()>=5)
			return true;
		return false;
	}
	
	public boolean is_red() {
		if(red.size()>=5)
			return true;
		return false;
	}

	/*public static String WichButtonPressed() {
		Buttons.GetClicks ()
	}*/
	
	
		
		
		


}
