import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {

	Direction currentDir = Direction.NORTH;

	public static void main(String[] args) {
		try {
			new Main();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Main() throws InterruptedException {

		LCD.drawString("V 0.14", 0, 0);
		Button.waitForAnyPress();

		//		LCD.drawString("Scann path", 0, 0);
		//		Button.waitForAnyPress();
		//		// TODO
		//
		//		LCD.drawString("Scann checkpoint", 0, 1);
		//		Button.waitForAnyPress();
		//		// TODO
		//		LCD.drawString("Scann pit", 0, 2);
		//		Button.waitForAnyPress();
		//		// TODO
		//		LCD.clear();
		//		LCD.drawString("Press button to\nstart ...", 0, 0);
		//		Button.waitForAnyPress();

		for (int i = 1; i <= 3; i++) {
			LCD.clear();
			LCD.drawString("" + i, 0, 0);

			Thread.sleep(1000);
		}

//		BotUtility.rotateSensor90DegreesRight();
//
//		BotUtility.rotateSensor90DegreesLeft();
//
//		BotUtility.rotateSensor90DegreesLeft();
//
//		BotUtility.rotateSensor90DegreesRight();

		BotUtility.rotate90DegreesLeft();

		// Wand erkennen Funktioniert 
		//		Motor.A.setSpeed(100); // Speed richtig einstellen
		//		Motor.A.setSpeed(100);
		//		
		//		Motor.A.forward();
		//		Motor.C.forward();
		//
		//		int MAX_DISTANCE = 50; // In centimeters
		//		int PERIOD = 100; // In milliseconds
		//		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		//		FeatureDetector fd = new RangeFeatureDetector(us, MAX_DISTANCE, PERIOD);
		//		Feature result = null;
		//
		//		boolean stop = false;
		//		while (!stop) {
		//			try {
		//				result = fd.scan();
		//				LCD.drawString("Range: " + result.getRangeReading().getRange(), 0, 0);
		//				if (result.getRangeReading().getRange() < 15) {
		//					stop = true;
		//				}
		//			} catch (NullPointerException e) {
		//				//stop = true;
		//			}
		//		}
		//		if (result != null) {
		//			LCD.clear();
		//			LCD.drawString("Range: " + result.getRangeReading().getRange(), 0, 0);
		//		}
		//		LCD.drawString("Stop!", 0, 1);
		//
		//		Motor.A.stop();
		//		Motor.C.stop();

		//		Motor.B.setSpeed(100);
		//		int rot = 650; // Degree
		//		Motor.B.rotate(rot);
		//		Thread.sleep(1000);
		//		Motor.B.rotate(-2 * rot);
		//		Thread.sleep(1000);
		//		Motor.B.rotate(rot);

		//Button.waitForAnyPress();

	}

}
