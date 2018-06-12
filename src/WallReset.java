import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class WallReset implements Runnable {

	public boolean stop = false;

	Thread th = null;

	public WallReset() {
		th = new Thread(this);
	}

	public void start() {
		th.start();
	}

	public void stop() {
		stop = true;
	}
	
	@Override
	public void run() {

		TouchSensor touch = new TouchSensor(SensorPort.S3);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (!stop) {
				if (touch.isPressed()) {
					System.out.println("Wall touched. Reseting to center.");
					BotUtility.resetToCenter();
				}
			}
		
	}

}
