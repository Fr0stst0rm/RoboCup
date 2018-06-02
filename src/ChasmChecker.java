import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;

public class ChasmChecker implements Runnable {

	public boolean stop = false;

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
		light.setFloodlight(true);

		while (!stop) {
			if (light == null) {
				light = new LightSensor(SensorPort.S2);
				light.setFloodlight(true);
			}
			if (light != null) {
				LCD.drawInt(light.getLightValue(), 4, 0, 4);
				if (light.getLightValue() <= BotStatus.blackTile) {
					BotUtility.handleChasm(light);
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LCD.drawString("Stop!", 0, 1);
		// TODO Auto-generated method stub

	}

}
