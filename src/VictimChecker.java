import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class VictimChecker implements Runnable {

	public boolean stop = false;

	Thread th = null;

	public VictimChecker() {
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

		System.out.println("Scanning for victims...");

		DThermalIR thermal = new DThermalIR(SensorPort.S4);

		Float obj = thermal.readObject();
		Float amb = thermal.readAmbient();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (!stop) {
			do {
				try {
					obj = thermal.readObject();
					amb = thermal.readAmbient();

//					System.out.println("ObjTemp = " + obj);
//					System.out.println("AmbTemp = " + amb);
				} catch (Exception e) {
					//System.out.println("Exception while reading temp values!");
					e.printStackTrace();
					thermal = new DThermalIR(SensorPort.S4);
				}
			} while ((obj == null) || (amb == null) || (obj < 0) || (amb < 0));

			if (obj > amb + 13) {
				System.out.println("ObjTemp = " + obj);
				System.out.println("AmbTemp = " + amb);
				System.out.println("Victim found!");
				BotStatus.victimsFound++;
				BotUtility.handleVictim();
				//BotStatus.victimsInterrupt = true;
				stop = true;
			}

		}

	}

}
