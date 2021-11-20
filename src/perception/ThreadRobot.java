import lejos.nxt.*;
import lejos.utility.Delay;
import lejos.hardware.Button;

public class Thread_havePalet extends Thread{

	public static void test_thread() {
		// TODO Auto-generated method stub
		private Sensor sensor;
		while(Button.ENTER.isDown()==false) {
			if(havePalet()==0) {
				System.out.println("Jai un palet !!");
			}
		}
	}
