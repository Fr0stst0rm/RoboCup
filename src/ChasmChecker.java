import javax.xml.stream.events.StartDocument;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class ChasmChecker implements Runnable{
	
	Thread th = null;
		
	public ChasmChecker() {
		th = new Thread(this);
	}
	
	public void start() {
		th.start();
	}

	@Override
	public void run() {
		

		LCD.drawString("Starting thread", 0, 0);
		
		LightSensor light = new LightSensor(SensorPort.S2);
		
		boolean stop = false;
		
		int temptest = 0;
		
	    while (!stop) {
	        LCD.drawInt(light.getLightValue(), 4, 0, 0);
	        LCD.drawInt(light.getNormalizedLightValue(), 4, 0, 1);
	        LCD.drawInt(SensorPort.S2.readRawValue(), 4, 0, 2);
	        LCD.drawInt(SensorPort.S2.readValue(), 4, 0, 3);
	        temptest++;
	        
	        try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if(temptest>100) {
	        	stop = true;
	        }
	      }
		LCD.drawString("Stop!", 0, 1);
		// TODO Auto-generated method stub
		
	}

}
