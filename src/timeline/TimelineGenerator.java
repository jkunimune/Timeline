package timeline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TimelineGenerator {

	public static void main(String[] args) throws IOException {
		ArrayList<Empire> empires = new ArrayList<Empire>();
		
		BufferedReader empireReader = new BufferedReader(new FileReader("Empires.txt"));
		String line = "";
		do {
			line = empireReader.readLine();
			empires.add(new Empire(Integer.parseInt(line.substring(0,1)), line.substring(1)));
		} while (line != null);
		empireReader.close();
	}

}
