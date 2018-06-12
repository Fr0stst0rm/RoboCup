
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

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
			int rot = 665; // Degree
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
			int rot = -665; // Degree
			Motor.B.rotate(rot);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			LCD.drawString(e.toString(), 0, 5);
		}
	}

	public static void handleChasm() {
		BotStatus.chasmInterrupt = true;
		LCD.clear();
		LCD.drawString("Handle chasm", 0, 0);
		System.out.println("Handle chasm.");
		
		stop();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		moveHalfTileBackwards();

		Point nextPoint = new Point(BotStatus.currentPos);
		switch (BotStatus.currentDir) {
		case NORTH:
			nextPoint.y++;
			break;
		case EAST:
			nextPoint.x++;
			break;
		case WEST:
			nextPoint.x--;
			break;
		case SOUTH:
			nextPoint.y--;
			break;
		}

		MapTile tile = new MapTile();
		tile.isChasm = true;
		tile.visited = true;
		tile.wallNorth = true;
		tile.wallEast = true;
		tile.wallSouth = true;
		tile.wallWest = true;
		BotStatus.mazeMap.addTile(nextPoint, tile);

		System.out.println("Chasm:");
		System.out.println(tile);
		
		BotStatus.chasmInterrupt = false;
	}

	public static void handleVictim() {
		System.out.println("Beeep!");
		Sound.playTone(500, 5000);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean scanForVictims() {

		System.out.println("Scanning for victims...");

		DThermalIR thermal = new DThermalIR(SensorPort.S4);
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

		//SensorPort.S4.i2cDisable();

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
		stop();

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
		stop();

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

		boolean chasmFound = false;

		Point nextPoint = new Point(BotStatus.currentPos);
		switch (BotStatus.currentDir) {
		case NORTH:
			nextPoint.y++;
			break;
		case EAST:
			nextPoint.x++;
			break;
		case WEST:
			nextPoint.x--;
			break;
		case SOUTH:
			nextPoint.y--;
			break;
		}

		move(3.1f);

		boolean chasm = false;

		while (BotStatus.chasmInterrupt) {
			chasm = true;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (BotStatus.wallInterrupt) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!chasm) {

			BotStatus.currentPos = nextPoint;

			BotStatus.pathToStart.push(new Point(nextPoint));
		}
	}

	public static void moveHalfTileBackwards() {
		move(-1.4f); //TODO Anpassen
	}

	//	public static void move(float rotAmount) {
	//		move(rotAmount, false);
	//	}

	//public static void move(float rotAmount, boolean waitForMotor) {//3 for one Tile, 1.5 for half tile
	public static void move(float rotAmount) {
		float rotCorrection = rotAmount * 4.5f;
		try {
			Motor.A.setSpeed(300);
			Motor.C.setSpeed(300);
			float rot = 364.0f * rotAmount; // 30 cm
			Motor.A.rotate((int) (rot - rotCorrection), true); //Korrektur, weil motoren nicht gleich stark sind
			Motor.C.rotate((int) rot);//, !waitForMotor);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			LCD.drawString(e.toString(), 0, 5);
		}
	}

	public static void stop() {
		Motor.A.setSpeed(0);
		Motor.C.setSpeed(0);
	}

	public static void resetToCenter() {

		BotStatus.wallInterrupt = true;
		LCD.clear();
		LCD.drawString("Reset wall", 0, 0);
		System.out.println("Reset wall.");
		
		stop();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		move(-0.2f);
		
		BotStatus.wallInterrupt = false;
	}

}
