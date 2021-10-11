package Robot;
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
	public static boolean FindPalet(MovePilot p) {
		
		float[] dist = new float[20];
		boolean find = false;
		p.rotate(360);
		
		if(p.isMoving()) {
		/*	
			while(!find) {
				for(int j=0; j<dist.length-1;j++) {
					dist[j] = dist[j+1];
				}
				dist[19] = (getDistance());
				
				
				//fais la moyenne
				float var_moy = 0;
				for(int i=0; i<dist.length-1;i++) {
					var_moy+= Math.abs(dist[i] - dist[i+1]);
				}
				var_moy = var_moy/dist.length;
				
				if(var_moy < Math.abs(dist[19] - dist[18])) {
					//La variation de la derniére distance perçus est supérieurs aux 20 deniéres variations
					find= true;
				}
			}
			p.stop();
			System.out.println("Trouvé ??");
		*/
		}
		
		return false;
	}
	
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

	/*public static String WichButtonPressed() {
		Buttons.GetClicks ()
	}*/
	
	public static void main(String[] args) {
		
		affiche("Initialisation Sensor");
		Sensor Sensor_OBJ = new Sensor("S1","S3","S4");
		affiche("Initialisation done!");

		//Sensor.getDistance();
		//Motor
		MovePilot chassi = new MovePilot(56,135,new EV3LargeRegulatedMotor(MotorPort.B),new EV3LargeRegulatedMotor(MotorPort.C));
		chassi.forward();
		
		
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
		Delay.msDelay(10000);//10 sec
		
		*/
		
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
		
		Sensor.FindPalet(chassi);
		
		/*
		Color rgb = Sensor.getColorOnGround();
		Sensor.havePalet(); //Pour stop
		int i = 0;
		while((Sensor.havePalet() < 0.5) && (i<10000) && !colorIsWisWHITE()){//10000 100sec
			//Do nothing
			i++;
			rgb = Sensor.getColorOnGround();
			//Lcd.print(6, "r=%d g=%d b=%d", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
			//Color color = new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
			affiche(i+" : C: "+ rgb.getRed()+ "," + rgb.getGreen() + "," +  rgb.getBlue());
			affiche(i+" : C: "+ Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()));
	    	Delay.msDelay(10);
		}
		//Color code
		//Gris table:
		//Orange 
		
		rgb = Sensor.getColorOnGround();
		affiche("Detecte... "+ rgb.getRed()+ "," + rgb.getGreen() + "," +  rgb.getBlue());
		affiche("C: "+ Sensor.Color_to_String(rgb.getRed(), rgb.getGreen(), rgb.getBlue()));
		chassi.stop();
		Delay.msDelay(10000);
		*/
		
		
		
	}

}
