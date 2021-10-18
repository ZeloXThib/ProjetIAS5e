package Robot;

import lejos.utility.Delay;

public class findPalet extends Brain {

	public double[] tab = new double[360];
	public double[] tabVar = new double[360];
	
	public void scan(int nombreSecondePourRota) {
		int deg = 0;
		//AQUISITION
		
		super.pilot.rotate(360, true);
		for(int i = 0;i<360;i++) {
			tab[i]=super.sensor.getDistance();
			Delay.msDelay((nombreSecondePourRota/360)*1000);
		}
		
		//TRAITEMENT
		for(int i=1;i<360;i++) {
			tabVar[i-1]=Math.abs(tab[i-1]/tab[i]);
		}
		
		//MAX INDICE
		int max_Indice = 0;
		for(int i=0;i<360;i++) {
			if(tabVar[max_Indice]<tabVar[i]) {
				max_Indice=i;
			}
		}
		if(max_Indice>=180) {
			deg = -360+max_Indice;
		} else {
			deg = max_Indice;
		}
		super.pilot.rotate(max_Indice);
	}	
}
