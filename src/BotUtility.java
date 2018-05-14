import lejos.nxt.Motor;

public class BotUtility {
	
	public static void rotateSensor90DegreesRight() throws InterruptedException {
		Motor.B.setSpeed(200);
		int rot = 650; // Degree
		Motor.B.rotate(rot);
	}
	
	public static void rotateSensor90DegreesLeft() throws InterruptedException {
		Motor.B.setSpeed(200);
		int rot = -652; // Degree
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
		Motor.A.setSpeed(120);
		Motor.C.setSpeed(150);
		int rot = 500; // Degree
		Motor.A.rotate(-rot,true);
		Motor.C.rotate(rot);
		Motor.A.stop();
		Motor.C.stop();
		//TODO
	}
	
	public static void rotate90DegreesRight() {
		//TODO
	}
	
	//TODO SensorObserver

}
