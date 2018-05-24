import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class BotUtility {

	public static void rotateSensor90DegreesRight() {
		try {
			Motor.B.setSpeed(300);
			int rot = 655; // Degree
			Motor.B.rotate(rot);
		} catch (IllegalStateException e) {
			LCD.drawString(e.toString(), 0, 5);
		}
	}

	public static void rotateSensor90DegreesLeft() {
		try {
			Motor.B.setSpeed(300);
			int rot = -655; // Degree
			Motor.B.rotate(rot);
		} catch (IllegalStateException e) {
			LCD.drawString(e.toString(), 0, 5);
		}
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

	public static void rotate90DegreesLeft() {
		Motor.A.setSpeed(150);
		Motor.C.setSpeed(150);
		int rot = -500; // Degree
		Motor.A.rotate(-(rot - 15), true);
		Motor.C.rotate(rot + 15);
		Motor.A.stop();
		Motor.C.stop();

		switch (BotStatus.currentDir) {
		case NORTH:
			BotStatus.currentDir = Direction.WEST;
			break;
		case EAST:
			BotStatus.currentDir = Direction.NORTH;
			break;
		case WEST:
			BotStatus.currentDir = Direction.SOUTH;
			break;
		case SOUTH:
			BotStatus.currentDir = Direction.EAST;
			break;
		}
	}

	public static void rotate90DegreesRight() {
		Motor.A.setSpeed(150);
		Motor.C.setSpeed(150);
		int rot = 500; // Degree
		Motor.A.rotate(-(rot - 15), true);
		Motor.C.rotate(rot + 15);
		Motor.A.stop();
		Motor.C.stop();

		switch (BotStatus.currentDir) {
		case NORTH:
			BotStatus.currentDir = Direction.EAST;
			break;
		case EAST:
			BotStatus.currentDir = Direction.SOUTH;
			break;
		case WEST:
			BotStatus.currentDir = Direction.NORTH;
			break;
		case SOUTH:
			BotStatus.currentDir = Direction.WEST;
			break;
		}
	}

	public static MapTile scanWalls() {
		MapTile tile = new MapTile();

		int wallDistance = 20;

		int MAX_DISTANCE = 50; // In centimeters
		int PERIOD = 250; // In milliseconds
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		FeatureDetector fd = new RangeFeatureDetector(us, MAX_DISTANCE, PERIOD);
		Feature result = null;

		boolean next = true;

		do {
			//Gerade
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				result = fd.scan();
				if (result != null) { //Null = no object in range 
					double distance = result.getRangeReading().getRange();
					LCD.drawString("R front: " + distance, 0, 1);
					if (distance < wallDistance) {
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
						//TODO scan for victim
						//if(victim == true){
						//handleVictim();
						BotStatus.victimsFound = BotStatus.victimsToFind;
						//}
						next = true;
					}
				}
			} catch (IllegalStateException e) {
				next = false;
			}
		} while (!next);

		//Rechts
		rotateSensor90DegreesRight();
		do {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				result = fd.scan();
				if (result != null) { //Null = no object in range
					double distance = result.getRangeReading().getRange();
					LCD.drawString("R right: " + distance, 0, 2);
					if (distance < wallDistance) {
						switch (BotStatus.currentDir) {
						case NORTH:
							tile.wallEast = true;
							break;
						case EAST:
							tile.wallSouth = true;
							break;
						case WEST:
							tile.wallNorth = true;
							break;
						case SOUTH:
							tile.wallWest = true;
							break;
						}
						//TODO scan for victim
						//if(victim == true){
						//handleVictim();
						BotStatus.victimsFound = BotStatus.victimsToFind;
						//}
						next = true;
					}
				}
			} catch (IllegalStateException e) {
				next = false;
			}
		} while (!next);

		//Links	
		rotateSensor90DegreesLeft();
		rotateSensor90DegreesLeft();

		do {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				result = fd.scan();
				if (result != null) { //Null = no object in range
					double distance = result.getRangeReading().getRange();
					LCD.drawString("R left: " + distance, 0, 3);
					if (distance < wallDistance) {
						switch (BotStatus.currentDir) {
						case NORTH:
							tile.wallWest = true;
							break;
						case EAST:
							tile.wallNorth = true;
							break;
						case WEST:
							tile.wallSouth = true;
							break;
						case SOUTH:
							tile.wallEast = true;
							break;
						}
						//TODO scan for victim
						//if(victim == true){
						//handleVictim();
						BotStatus.victimsFound = BotStatus.victimsToFind;
						//}
						next = true;
					}
				}
			} catch (IllegalStateException e) {
				next = false;
			}
		} while (!next);

		rotateSensor90DegreesRight();

		us.reset();
		
		tile.visited = true;

		return tile;
	}

	public static void moveToNextTile() {
		try {
			Motor.A.setSpeed(300);
			Motor.C.setSpeed(300);
			int rot = 350 * 3; // 30 cm
			Motor.A.rotate(rot - 12, true); //Korrektur, weil motoren nicht gleich stark sind
			Motor.C.rotate(rot);
		} catch (IllegalStateException e) {
			LCD.drawString(e.toString(), 0, 5);
		}

	}

}
