import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {

	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) {
			LCD.drawString(e.toString(), 0, 0);
		}
	}

	public Main() {

		LCD.drawString("V 0.29", 0, 0);
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

		while (BotStatus.victimsFound < BotStatus.victimsToFind) {
			LCD.clear();
			LCD.drawString("Vics found: "+ BotStatus.victimsFound, 0, 0);
			
			BotUtility.moveToNextTile();
			BotUtility.scanWalls();

			//		BotUtility.rotate90DegreesLeft();
			//		BotUtility.rotate90DegreesRight();
			//		BotUtility.rotate90DegreesRight();
			//		BotUtility.rotate90DegreesLeft();

			//TODO if entfernung gegenüber ungleich ==> Bot in der mitte plazieren
			BotStatus.victimsFound++;

		}
		
		LCD.drawString("Vics found: "+ BotStatus.victimsFound, 0, 0);
		LCD.drawString("Stopping!", 0, 4);
		
		Button.waitForAnyPress();

	}

}
