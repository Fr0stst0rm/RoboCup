import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class Test {

	public static void main(String[] args) {
		try {
			new Test();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Test() throws InterruptedException {
		LCD.drawString("Scann path", 0, 0);
		Button.waitForAnyPress();
		//TODO
		LCD.drawString("Scann checkpoint", 0, 1);
		Button.waitForAnyPress();
		//TODO
		LCD.drawString("Scann pit", 0, 2);
		Button.waitForAnyPress();
		//TODO
		LCD.clear();
		LCD.drawString("Press button to\nstart ...", 0, 0);
		Button.waitForAnyPress();

		for (int i = 1; i <= 5; i++) {
			LCD.clear();
			LCD.drawString("" + i, 0, 0);

			Thread.sleep(1000);
		}

		//		Motor.A.forward();
		//		Motor.C.forward();
		
		Motor.B.setSpeed(100);
		int rot = 650; // Degree
		Motor.B.rotate(rot);
		Thread.sleep(1000);
		Motor.B.rotate(-2*rot);
		Thread.sleep(1000);
		Motor.B.rotate(rot);
		
		//Button.waitForAnyPress();
	}

}
