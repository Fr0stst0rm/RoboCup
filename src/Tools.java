import java.util.LinkedList;

public class Tools {
	
	public static String stringBuilder(LinkedList<Character> list) {
		String str = "";
		for (Character character : list) {
			str += character;
		}
		return str;
	}

}
