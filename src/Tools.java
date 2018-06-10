
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class Tools {

	static File logFile = new File("sysout.log");

	public static String stringBuilder(LinkedList<Character> list) {
		String str = "";
		for (Character character : list) {
			str += character;
		}
		return str;
	}

	/**
	 * Logs the message to the program, the console and a log file with the default log level LOG_USE and a new line
	 *
	 * @param msg
	 *            The message to log
	 * @return returns the written string
	 */
	public static String logln(String msg) {

		try {

			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile,true)));

			bfw.write(msg);
			bfw.newLine();

			bfw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return msg;
	}

}
