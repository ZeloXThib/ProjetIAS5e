package perception;

import lejos.utility.Delay;
import lejos.hardware.Button;

public class ThreadRobot extends Thread{

	public static void test_thread() {
		// TODO Auto-generated method stub
		Sensor sensor = new Sensor();
		while(Button.ENTER.isDown()==false) {
			if(sensor.havePalet()==0) {
				System.out.println("Jai un palet !!");
			}
		}
	}
}
