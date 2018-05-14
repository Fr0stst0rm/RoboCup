import lejos.nxt.Motor;

public class BotUtility {
	
	void rotateSensor90DegreesRight() throws InterruptedException {
		Motor.B.setSpeed(100);
		int rot = 650; // Degree
		Motor.B.rotate(rot);
	}
	
	void rotateSensor90DegreesLeft() throws InterruptedException {
		Motor.B.setSpeed(100);
		int rot = -650; // Degree
		Motor.B.rotate(rot);
	}
	
	void driveToNextTile() {
		//TODO
	}
	
	void handleChasm() {
		//TODO
	}
	
	void handleVictim() {
		//TODO
	}
	
	void scanForWalls() {
		//TODO
	}
	
	void rotate90DegreesLeft() {
		Motor.A.setSpeed(100);
		int rotA = -650; // Degree
		Motor.A.rotate(rotA);
		Motor.C.setSpeed(100);
		int rotC = 650; // Degree
		Motor.C.rotate(rotC);
		//TODO
	}
	
	void rotate90DegreesRight() {
		//TODO
	}
	
	//TODO SensorObserver

}
