import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Test implements SensorPortListener {

	public static void main(String[] args) {
		try {
			new Test();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Test() throws InterruptedException {

		SensorPort.S1.addSensorPortListener(this);

		LCD.drawString("Scann path", 0, 0);
		Button.waitForAnyPress();
		// TODO

		LCD.drawString("Scann checkpoint", 0, 1);
		Button.waitForAnyPress();
		// TODO
		LCD.drawString("Scann pit", 0, 2);
		Button.waitForAnyPress();
		// TODO
		LCD.clear();
		LCD.drawString("Press button to\nstart ...", 0, 0);
		Button.waitForAnyPress();

		for (int i = 1; i <= 5; i++) {
			LCD.clear();
			LCD.drawString("" + i, 0, 0);

			Thread.sleep(1000);
		}

		// Motor.A.forward();
		// Motor.C.forward();

		int MAX_DISTANCE = 50; // In centimeters
		int PERIOD = 100; // In milliseconds
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		FeatureDetector fd = new RangeFeatureDetector(us, MAX_DISTANCE, PERIOD);

		Feature result = fd.scan();
		while (result != null) {
			LCD.drawString("Range: " + result.getRangeReading().getRange(), 0, 0);
		}

		// Motor.B.setSpeed(100);
		// int rot = 650; // Degree
		// Motor.B.rotate(rot);
		// Thread.sleep(1000);
		// Motor.B.rotate(-2*rot);
		// Thread.sleep(1000);
		// Motor.B.rotate(rot);

		// Button.waitForAnyPress();
	}

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		if (aSource.getClass().equals(LightSensor.class)) {

		}

	}

}
