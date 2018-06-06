import java.io.IOException;
import java.io.OutputStream;

public class SysLogStream extends OutputStream {
	
	String errorLine = "";

	@Override
	public void write(int b) throws IOException {
		if((char)b != '\n') {
			errorLine += (char)b ;
		} else {
			Tools.logln(errorLine);
			errorLine = "";
		}
	}

}
