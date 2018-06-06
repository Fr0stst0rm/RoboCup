import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		LCD.drawString("V 0.39", 0, 0);
		Button.waitForAnyPress();

		scanTileColors();

		LCD.clear();
		LCD.drawString("Black is < " + BotStatus.blackTile, 0, 0);
		//LCD.drawString("Checkpoint is < " + BotStatus.blackTile, 0, 1);
		LCD.drawString("Path is > " + BotStatus.pathTile, 0, 1);
		LCD.drawString("Press button to\nstart ...", 0, 4);
		Button.waitForAnyPress();

		for (int i = 3; i >= 1; i--) {
			LCD.clear();
			LCD.drawString("" + i, 0, 0);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		MapTile tile = new MapTile();
		tile.isStart = true;
		tile.wallEast = true;
		tile.wallSouth = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 0, tile);

		RelativeDirection lastRelativeDirection = RelativeDirection.FORWARD;

		while (BotStatus.victimsFound < BotStatus.victimsToFind) {
			Direction nexDir = BotStatus.convertRelativeDirection(lastRelativeDirection);
			
			//TODO Turn bot
			
			BotUtility.moveToNextTile();
			
			tile = BotUtility.scanWalls();
			
			BotStatus.mazeMap.addTile(BotStatus.currentPos, tile);
			
			if(tile.isDeadEnd()) {
				
				//Find last unchecked crossroad
				Point start = BotStatus.currentPos;
				Point stop = null;
				do{
					stop = BotStatus.pathToStart.pop();
				} while(!BotStatus.mazeMap.hasUnvisitedNeighboring(stop));
				
				if(stop.equals(new Point(0, 0))) {
					LCD.drawString("Something went wrong!", 0, 0);
					LCD.drawString("Bot is back", 0, 1);
					LCD.drawString("on start tile", 0, 2);
					return;
				}
				
				Point correctedStart = new Point( BotStatus.mazeMap.rearrangeXOffset(stop.x),BotStatus.mazeMap.rearrangeYOffset(stop.y));
				Point correctedStop  = new Point( BotStatus.mazeMap.rearrangeXOffset(start.x),BotStatus.mazeMap.rearrangeYOffset(start.y));
				
				//A*
				ASternDerDeinenNamenTraegt aStern = new ASternDerDeinenNamenTraegt(correctedStart,correctedStop);
				Path pathToCrossRoad = aStern.getPath();
				
				while(!pathToCrossRoad.empty()) {
					Point nextPoint = pathToCrossRoad.pop().add(BotStatus.mazeMap.offset);
					
				}
				
 				//Get next relative Dir
				BotStatus.mazeMap.getMapTile(BotStatus.currentPos);
			}
			
			
		}

	}
	
	public void scanTileColors() {
		LCD.drawString("Scann path", 0, 0);
		Button.waitForAnyPress();

		LightSensor light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);

		int path = light.getLightValue();
		
		LCD.drawString("Scann checkpoint", 0, 1);
		Button.waitForAnyPress();

		light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);
		
		int checkpoint = light.getLightValue();

		LCD.drawString("Scann pit", 0, 2);
		Button.waitForAnyPress();

		light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);

		int pit = light.getLightValue();
		
		BotStatus.blackTile = (pit + checkpoint) / 2 ;
		BotStatus.pathTile = (path + checkpoint) / 2 ;
	}

	private void buildMap() {
		MapTile tile = new MapTile();
		tile.isStart = true;
		tile.wallEast = true;
		tile.wallSouth = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 0, tile);

		tile = new MapTile();
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-1, 1, tile);

		tile = new MapTile();
		tile.wallSouth = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-2, 1, tile);

		tile = new MapTile();
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(1, 1, tile);

		tile = new MapTile();
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(2, 1, tile);

		tile = new MapTile();
		tile.wallSouth = true;
		tile.wallEast = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(3, 1, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 1, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-2, 2, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-1, 2, tile);

		tile = new MapTile();
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 2, tile);

		tile = new MapTile();
		tile.visited = true;

		BotStatus.mazeMap.addTile(1, 2, tile);

		tile = new MapTile();
		tile.wallNorth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(2, 2, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(3, 2, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-2, 3, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-1, 3, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 3, tile);

		tile = new MapTile();
		tile.visited = true;

		BotStatus.mazeMap.addTile(1, 3, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(2, 3, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.wallNorth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(3, 3, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-2, 4, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.wallNorth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-1, 4, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.wallEast = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 4, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(1, 4, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallNorth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(2, 4, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(3, 4, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-2, 5, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-1, 5, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.wallEast = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 5, tile);

		tile = new MapTile();
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(1, 5, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallNorth = true;
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(2, 5, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(3, 5, tile);

		tile = new MapTile();
		tile.wallNorth = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-2, 6, tile);

		tile = new MapTile();
		tile.wallNorth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(-1, 6, tile);

		tile = new MapTile();
		tile.wallNorth = true;
		tile.wallEast = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(0, 6, tile);

		tile = new MapTile();
		tile.wallNorth = true;
		tile.wallWest = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(1, 6, tile);

		tile = new MapTile();
		tile.wallNorth = true;
		tile.wallSouth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(2, 6, tile);

		tile = new MapTile();
		tile.wallEast = true;
		tile.wallNorth = true;
		tile.visited = true;

		BotStatus.mazeMap.addTile(3, 6, tile);

	}

	public void printMapToFile() {
		File mapOut = new File("map.txt");

		try {

			if (mapOut.exists()) {
				mapOut.delete();
			}

			mapOut.createNewFile();

			BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapOut)));

			bfw.write(BotStatus.mazeMap.toString());

			bfw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
