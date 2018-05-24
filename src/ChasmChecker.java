import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;

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
		

		LCD.drawString("Starting ChasmChecker thread", 0, 0);
		
		LightSensor light = new LightSensor(SensorPort.S2);
		
		boolean stop = false;
		int temptest = 0;
		
	    while (!stop) {
	        LCD.drawInt(light.getLightValue(), 4, 0, 1); 
	      
	        temptest++;
	        
	        try {
				Thread.sleep(50);
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
