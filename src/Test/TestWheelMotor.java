package Test;

import java.util.Scanner;

import Robot.WheelMotor;

public class TestWheelMotor {

	public static void main(String[] args) {
		
			Scanner sc = new Scanner(System.in);
			System.out.println("1, 2 ou 3");
			int num = sc.nextInt();
			sc.close();
			System.out.println(num);
			WheelMotor m = new WheelMotor();
			m.forward(10000);
			m.arcDeCercle(1000,true);	
			m.forward(3000);
	}

}
