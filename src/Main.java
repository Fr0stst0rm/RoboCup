import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class Main {

	public static void main(String[] args) {
		try {
			// Creating a File object that represents the disk file.
	        PrintStream o = new PrintStream(new FileOutputStream(new File("log.txt")));
	 
	        // Store current System.out before assigning a new value
	        PrintStream console = System.out;
	 
	        // Assign o to output stream
	        System.setOut(o);
	        System.setErr(o);
	        
			new Main();
		} catch (Exception e) {
			LCD.drawString(e.toString(), 0, 0);
		}
	}

	public Main() {

		LCD.drawString("V 0.37", 0, 0);
		Button.waitForAnyPress();

		//		LCD.drawString("Scann path", 0, 0);
		//		Button.waitForAnyPress();
		//		// TODO
		//
		//		LCD.drawString("Scann checkpoint", 0, 1);
		//		Button.waitForAnyPress();
		//		// TODO
		
		LCD.drawString("Scann pit", 0, 2);
		Button.waitForAnyPress();

		LightSensor light = new LightSensor(SensorPort.S2);
		light.setFloodlight(true);

		BotStatus.blackTile = light.getLightValue() + 8;

		LCD.clear();
		LCD.drawString("Black is: " + BotStatus.blackTile, 0, 0);
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

		//Thread zum prüfen von Löchern starten
		ChasmChecker cc = new ChasmChecker();
		cc.start();

		//Start
		MapTile start = BotUtility.scanWalls();
		if ((BotStatus.currentPos.x == 0) && (BotStatus.currentPos.y == 0)) {
			start.isStart = true;
			start.wallSouth = true;
		}
		BotStatus.mazeMap.addTile(BotStatus.currentPos.x, BotStatus.currentPos.y, start);

		while (BotStatus.victimsFound < BotStatus.victimsToFind) {
			LCD.clear();
			LCD.drawString("Vics found: " + BotStatus.victimsFound, 0, 0);

			BotUtility.moveToNextTile();

			if (BotStatus.mapping) {
				MapTile tile = BotUtility.scanWalls();
				BotStatus.mazeMap.addTile(BotStatus.currentPos.x, BotStatus.currentPos.y, tile);
			} else {
				while (!BotStatus.mapping) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				MapTile chasm = BotStatus.mazeMap.getCurrentDirNexMapTile();
				chasm.visited = true;
				chasm.isChasm = true;
				chasm.wallNorth = true;
				chasm.wallEast = true;
				chasm.wallSouth = true;
				chasm.wallWest = true;

			}

			//TODO if entfernung gegenüber ungleich ==> Bot in der mitte plazieren
			BotStatus.victimsFound++;

		}

		cc.stop = true;

		LCD.drawString("Vics found: " + BotStatus.victimsFound, 0, 0);
		LCD.drawString("Stopping!", 0, 4);

		Button.waitForAnyPress();

		printMapToFile();
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
