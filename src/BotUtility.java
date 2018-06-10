
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class BotUtility {

	public static void rotateSensor90DegreesRight() {
		try {
			switch (BotStatus.lookDir) {
			case NORTH:
				BotStatus.lookDir = Direction.EAST;
				break;
			case WEST:
				BotStatus.lookDir = Direction.NORTH;
				break;
			case EAST:
				BotStatus.lookDir = Direction.SOUTH;
				break;
			case SOUTH:
				BotStatus.lookDir = Direction.WEST;
				break;
			}
			Motor.B.setSpeed(300);
			int rot = 655; // Degree
			Motor.B.rotate(rot);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			LCD.drawString(e.toString(), 0, 5);
		}
	}

	public static void rotateSensor90DegreesLeft() {
		try {
			switch (BotStatus.lookDir) {
			case NORTH:
				BotStatus.lookDir = Direction.WEST;
				break;
			case WEST:
				BotStatus.lookDir = Direction.SOUTH;
				break;
			case EAST:
				BotStatus.lookDir = Direction.NORTH;
				break;
			case SOUTH:
				BotStatus.lookDir = Direction.EAST;
				break;
			}
			Motor.B.setSpeed(300);
			int rot = -655; // Degree
			Motor.B.rotate(rot);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			LCD.drawString(e.toString(), 0, 5);
		}
	}

	public static void handleChasm(LightSensor lightsensor) {
		BotStatus.mapping = false;
		Motor.A.stop();
		Motor.C.stop();
		LCD.drawString("Chasm detected!", 0, 1);
		while (lightsensor.getLightValue() <= BotStatus.blackTile) {
			move(-0.1f);
		}
		moveHalfTileBackwards();
		BotStatus.mapping = true;
	}

	public static void handleVictim() {
		System.out.println("Beeep!");
		Sound.playTone(500, 5000);
	}

	public static boolean scanForVictims() {

		System.out.println("Scanning for victims...");

		DThermalIR thermal;

		thermal = new DThermalIR(SensorPort.S4);
		//thermal.setEmissivity(0.53f);
		//System.out.println(thermal.readEmissivity());
		//Button.ENTER.waitForPressAndRelease();

		Float obj = null;
		Float amb = null;

		do {
			try {
				obj = thermal.readObject();
				amb = thermal.readAmbient();
				System.out.println("ObjTemp = " + obj);
				System.out.println("AmbTemp = " + amb);
			} catch (Exception e) {
				System.out.println("Exception while reading temp values!");
				e.printStackTrace();
				thermal = new DThermalIR(SensorPort.S4);
			}
		} while ((obj == null) && (amb == null));

		SensorPort.S4.i2cDisable();

		if (obj > amb + 15) {
			System.out.println("Victim found!");
			BotStatus.victimsFound++;
			return true;
		}

		return false;

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

		switch (BotStatus.lookDir) {
		case NORTH:
			BotStatus.lookDir = Direction.WEST;
			break;
		case WEST:
			BotStatus.lookDir = Direction.SOUTH;
			break;
		case EAST:
			BotStatus.lookDir = Direction.NORTH;
			break;
		case SOUTH:
			BotStatus.lookDir = Direction.EAST;
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

		switch (BotStatus.lookDir) {
		case NORTH:
			BotStatus.lookDir = Direction.EAST;
			break;
		case WEST:
			BotStatus.lookDir = Direction.NORTH;
			break;
		case EAST:
			BotStatus.lookDir = Direction.SOUTH;
			break;
		case SOUTH:
			BotStatus.lookDir = Direction.WEST;
			break;
		}
	}

	//TODO add victim detection
	public static MapTile scanWalls() {

		MapTile tile = new MapTile();
		try {
			int wallDistance = 25;

			UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);

			boolean next = true;

			do {
				//Gerade
				System.out.println("Scanning forward");

				try {
					us.ping();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double distance = us.getDistance();
					System.out.println("Wall found. Distance: " + distance);
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
						if (scanForVictims()) {
							handleVictim();
						}
						next = true;
					}

				} catch (IllegalStateException e) {
					//e.printStackTrace();
					next = false;
				}
			} while (!next);

			//Rechts

			rotateSensor90DegreesRight();
			do {
				System.out.println("Scanning right");
				try {
					us.ping();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double distance = us.getDistance();
					System.out.println("Wall found. Distance: " + distance);
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
						if (scanForVictims()) {
							handleVictim();
						}
						next = true;

					}
				} catch (IllegalStateException e) {
					//e.printStackTrace();
					next = false;
				}
			} while (!next);

			//Links	
			rotateSensor90DegreesLeft();
			rotateSensor90DegreesLeft();

			do {
				System.out.println("Scanning left");
				try {
					us.ping();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double distance = us.getDistance();
					System.out.println("Wall found. Distance: " + distance);
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
						if (scanForVictims()) {
							handleVictim();
						}
						next = true;
					}
				} catch (IllegalStateException e) {
					//e.printStackTrace();
					next = false;
				}
			} while (!next);

			rotateSensor90DegreesRight();

			System.out.println("Turning sensor off");

			us.off();

			tile.visited = true;

			System.out.println("Scanning walls finished.");
		} catch (

		IllegalStateException e) {
			System.out.println("Possibel error 4");
		}
		return tile;
	}

	public static void moveToNextTile() {
		move(3);
		switch (BotStatus.currentDir) {
		case NORTH:
			BotStatus.currentPos.y++;
			break;
		case EAST:
			BotStatus.currentPos.x++;
			break;
		case WEST:
			BotStatus.currentPos.x--;
			break;
		case SOUTH:
			BotStatus.currentPos.y--;
			break;
		}
		BotStatus.pathToStart.push(new Point(BotStatus.currentPos));
	}

	public static void moveHalfTileBackwards() {
		move(-1.5f);
	}

	public static void move(float rotAmount) {//3 for one Tile, 1.5 for half tile
		float rotCorrection = rotAmount * 4;
		try {
			Motor.A.setSpeed(300);
			Motor.C.setSpeed(300);
			float rot = 373.5f * rotAmount; // 30 cm
			Motor.A.rotate((int) (rot - rotCorrection), true); //Korrektur, weil motoren nicht gleich stark sind
			Motor.C.rotate((int) rot);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			LCD.drawString(e.toString(), 0, 5);
		}

	}

}
