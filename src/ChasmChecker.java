public class ChasmChecker implements Runnable {

	public boolean stop = false;

	Thread th = null;

	public ChasmChecker() {
		th = new Thread(this);
	}

	public void start() {
		th.start();
	}

	public void stop() {
		stop = true;
	}
	
	@Override
	public void run() {

		BotStatus.light.setFloodlight(true);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int lightValue = 0;

		while (!stop) {
			if (BotStatus.light != null) {
				lightValue = BotStatus.light.getLightValue();
				if (lightValue <= BotStatus.blackTile) {
					System.out.println("Current light: " + lightValue + " | Black: " + BotStatus.blackTile);
					BotUtility.handleChasm();
				}
			}
		}
	}

}
