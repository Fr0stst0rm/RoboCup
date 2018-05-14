import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class BotUtility {

	public static void rotateSensor90DegreesRight() throws InterruptedException {
		Motor.B.setSpeed(200);
		int rot = 655; // Degree
		Motor.B.rotate(rot);
	}

	public static void rotateSensor90DegreesLeft() throws InterruptedException {
		Motor.B.setSpeed(200);
		int rot = -655; // Degree
		Motor.B.rotate(rot);
	}

	public static void driveToNextTile() {
		//TODO
	}

	public static void handleChasm() {
		//TODO
	}

	public static void handleVictim() {
		//TODO
	}

	public static void scanForWalls() {
		//TODO
	}

	public static void rotate90DegreesLeft() {
		Motor.A.setSpeed(150);
		Motor.C.setSpeed(150);
		int rot = 500; // Degree
		Motor.A.rotate(-(rot - 25), true);
		Motor.C.rotate(rot);
		Motor.A.stop();
		Motor.C.stop();
		//TODO
	}

	public static void rotate90DegreesRight() {
		//TODO
	}

	public static MapTile scanTile() {
		MapTile tile = new MapTile();

		int MAX_DISTANCE = 50; // In centimeters
		int PERIOD = 100; // In milliseconds
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		FeatureDetector fd = new RangeFeatureDetector(us, MAX_DISTANCE, PERIOD);
		Feature result = null;

		//Gerade		
		try {
			result = fd.scan();
			LCD.drawString("Range: " + result.getRangeReading().getRange(), 0, 0);
			if (result.getRangeReading().getRange() < 17) {
				tile.wallNorth = true;
			}
		} catch (NullPointerException e) {
			switch (BotStatus.currentDir) {
			case NORTH:
				tile.wallNorth = true;
				break;
			case EAST:
				tile.wallEast = true;
				break;
			case WEST:
				tile.wallWest = true;
				break;
			case SOUTH:
				tile.wallSouth = true;
				break;
			}
		}

		return null;
	}

	//TODO SensorObserver

}
