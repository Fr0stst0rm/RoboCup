
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Arrays;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Main {

	public static void main(String[] args) {

		if (Tools.logFile.exists()) {
			Tools.logFile.delete();
		}

		//Zu wenig memory :(
		System.setOut(new PrintStream(new SysLogStream()));
		System.setErr(new PrintStream(new SysLogStream()));

		//Simulation
		//BaseMap.initBaseMap();


		new Main();
	}

	public Main() {

		String version = "V 0.63";

		LCD.drawString(version, 0, 0);
		System.out.println(version);

		Button.waitForAnyPress();

		scanTileColors();

		//Dunkel = kleiner, Heller = größer
		LCD.clear();
		LCD.drawString("Black is <= " + BotStatus.blackTile, 0, 0);
		//LCD.drawString("Checkpoint is < " + BotStatus.blackTile, 0, 1);
		LCD.drawString("Path is >= " + BotStatus.pathTile, 0, 1);
		LCD.drawString("Press button to\nstart ...", 0, 4);
		Button.waitForAnyPress();

		for (int i = 3; i >= 1; i--) {
			LCD.clear();
			LCD.drawString("" + i, 0, 0);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		MapTile tile = new MapTile();
		tile.isStart = true;
		tile.wallEast = true;
		tile.wallSouth = true;
		tile.wallWest = true;
		tile.visited = true;

		System.out.println("Adding tile to map");

		BotStatus.mazeMap.addTile(0, 0, tile);

		System.out.println(BotStatus.mazeMap.getMapTile(BotStatus.currentPos));

		BotUtility.moveToNextTile();

		RelativeDirection lastRelativeDirection = RelativeDirection.FORWARD;

		while (!BotStatus.mappingEnded && (BotStatus.victimsFound < BotStatus.victimsToFind)) {

			LCD.clear();

			System.out.println("Scanning current tile:");

			boolean scan = false;

			try {
				if (!BotStatus.mazeMap.getMapTile(BotStatus.currentPos).visited) {
					scan = true;
					System.out.println("Tile already scanned");
				}
			} catch (IndexOutOfBoundsException | NullPointerException e) {
				scan = true;
			}

			if (scan) {
				try {
					tile = BotUtility.scanWalls();
				} catch (IllegalStateException e) {
					System.out.println("Possible error 1");
				}
				System.out.println("Adding new tile");
				try {
					BotStatus.mazeMap.addTile(BotStatus.currentPos, tile);
				} catch (IllegalStateException e) {
					System.out.println("Possible error 2");
				}
				System.out.println("New tile added.");
			}

			System.out.println();
			System.out.println("Current pos: " + BotStatus.currentPos);
			System.out.println();
			try {
				System.out.println(BotStatus.mazeMap.getMapTile(BotStatus.currentPos));
			} catch (IllegalStateException e) {
				System.out.println("Possible error 3");
			}
			if (!BotStatus.mazeMap.hasUnvisitedNeighboring(BotStatus.currentPos)) {
				handleDeadEnd();
			} else {

				System.out.println("No dead end");

				boolean moved = false;
				tile = BotStatus.mazeMap.getMapTile(BotStatus.currentPos);
				Direction currentDir = BotStatus.convertRelativeDirection(lastRelativeDirection);
				System.out.println("Current direction: " + BotStatus.currentDir);
				do {
					Direction nextDir = BotStatus.convertRelativeDirection(lastRelativeDirection);
					System.out.println("Next direction: " + nextDir.name());

					//Check if nextDir is a valid tile && not visited
					boolean tileIsValide = false;
					if (!tile.checkWall(nextDir)) {
						tileIsValide = true;
						try {
							tileIsValide = !BotStatus.mazeMap.getMapTile(BotStatus.currentPos, nextDir).visited;
						} catch (IndexOutOfBoundsException | NullPointerException e) {
							//e.printStackTrace();
						}
					}

					System.out.println("Next tile valide: " + tileIsValide);

					if (tileIsValide) {
						LCD.drawString("Next dir: " + BotStatus.currentDir.name(), 0, 0);
						//turn if lastRelativeDirection is not forward
						System.out.println("Turning bot " + lastRelativeDirection.name());
						switch (lastRelativeDirection) {
						case LEFT:
							BotUtility.rotate90DegreesLeft();
							break;
						case RIGHT:
							BotUtility.rotate90DegreesRight();
							break;
						case BACK:
							BotUtility.rotate90DegreesRight();
							BotUtility.rotate90DegreesRight();
							break;
						}

						System.out.println("Moving forward");
						BotUtility.moveToNextTile();
						moved = true;
					} else {
						switch (lastRelativeDirection) {
						case LEFT:
							lastRelativeDirection = RelativeDirection.FORWARD;
							break;
						case RIGHT:
							lastRelativeDirection = RelativeDirection.LEFT;
							break;
						case FORWARD:
							lastRelativeDirection = RelativeDirection.RIGHT;
							break;
						}
						if (currentDir.equals(BotStatus.convertRelativeDirection(lastRelativeDirection))) {
							System.out.println("The bot should never reach this point ==> endless loop");
							//							handleDeadEnd();
							moved = true;
						}
					}
				} while (!moved);

			}
			if(Button.waitForAnyPress(450) == Button.ID_ESCAPE) {
				BotStatus.mappingEnded = true;
			}
		}

		System.out.println(BotStatus.mazeMap);
		Button.waitForAnyPress();
		printMapToFile(BotStatus.mazeMap);

	}

	public void handleDeadEnd() {

		System.out.println("Dead end found!");

		//Find last unchecked crossroad
		Point start = BotStatus.currentPos;
		Point stop = null;
		do {
			stop = BotStatus.pathToStart.pop();
		} while (!BotStatus.pathToStart.empty() && !BotStatus.mazeMap.hasUnvisitedNeighboring(stop));

		if (stop.equals(new Point(0, 0))) {
			System.out.println("Maze completely mapped!");
			System.out.println("Returning to start tile.");
			BotStatus.mappingEnded = true;
		}

		Point correctedStart = new Point(BotStatus.mazeMap.rearrangeXOffset(start.x), BotStatus.mazeMap.rearrangeYOffset(start.y));
		Point correctedStop = new Point(BotStatus.mazeMap.rearrangeXOffset(stop.x), BotStatus.mazeMap.rearrangeYOffset(stop.y));

		System.out.println("Starting A*");

		//A*
		ASternDerDeinenNamenTraegt aStern = new ASternDerDeinenNamenTraegt(correctedStart, correctedStop);
		Path pathToCrossRoad = aStern.getPath();

		System.out.println("Best path found: " + pathToCrossRoad);

		pathToCrossRoad.pop();

		while (!pathToCrossRoad.empty()) {
			Direction nextDir = null;
			Point nextPoint = pathToCrossRoad.pop().add(BotStatus.mazeMap.offset);

			Point testPoint = BotStatus.currentPos;

			testPoint = new Point(testPoint.x + 1, testPoint.y);

			if (testPoint.equals(nextPoint)) {
				nextDir = Direction.EAST;
			}

			testPoint = BotStatus.currentPos;

			testPoint = new Point(testPoint.x - 1, testPoint.y);

			if (testPoint.equals(nextPoint)) {
				nextDir = Direction.WEST;
			}

			testPoint = BotStatus.currentPos;

			testPoint = new Point(testPoint.x, testPoint.y + 1);

			if (testPoint.equals(nextPoint)) {
				nextDir = Direction.NORTH;
			}

			testPoint = BotStatus.currentPos;

			testPoint = new Point(testPoint.x, testPoint.y - 1);

			if (testPoint.equals(nextPoint)) {
				nextDir = Direction.SOUTH;
			}

			int turns = BotStatus.calculateTurnesToDir(nextDir);

			System.out.println("Current dir " + BotStatus.currentDir.name());
			System.out.println("Desired dir " + nextDir.name());
			System.out.println("Turns: " + turns);

			//TODO Test Rotate
			if (turns > 0) {
				for (; turns > 0; turns--) {
					BotUtility.rotate90DegreesRight();
				}
			} else if (turns < 0) {
				turns = -1 * turns;
				for (; turns > 0; turns--) {
					BotUtility.rotate90DegreesLeft();
				}
			}

			BotUtility.moveToNextTile();

		}
	}

	public void scanTileColors() {

		LightSensor light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);

		LCD.drawString("Scann path", 0, 0);
		Button.waitForAnyPress();

		int path = light.getLightValue();

		System.out.println("Path: " + path);

		LCD.drawString("Scann checkpoint", 0, 1);
		Button.waitForAnyPress();

		light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);

		int checkpoint = light.getLightValue();

		System.out.println("Ceckpoint: " + checkpoint);

		LCD.drawString("Scann pit", 0, 2);
		Button.waitForAnyPress();

		light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);

		int pit = light.getLightValue();
		System.out.println("Pit: " + pit);

		BotStatus.blackTile = (pit + checkpoint) / 2;
		BotStatus.pathTile = (path + checkpoint) / 2;

		System.out.println("Path >= " + BotStatus.pathTile);
		System.out.println("Checkpoint > " + BotStatus.blackTile + " and < " + BotStatus.pathTile);
		System.out.println("Pit <= " + BotStatus.blackTile);

		light.setFloodlight(false);

		SensorPort.S2.i2cDisable();
	}

	public void printMapToFile(Map map) {
		File mapOut = new File("map.txt");

		try {

			if (mapOut.exists()) {
				mapOut.delete();
			}

			mapOut.createNewFile();

			BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapOut)));

			bfw.write(map.toString());

			bfw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
