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
			if (line.isEmpty())
				empires.add(new Empire(Integer.parseInt(line.substring(0,1)), line.substring(1)));
			line = empireReader.readLine();
		} while (line != null);
		empireReader.close();
		
		BufferedReader areaReader = new BufferedReader(new FileReader("Timeline.txt"));
		do {
			if (line.isEmpty()) {
				String name = line.substring(10);
				Empire e;
				for (int i = 0; i < empires.size(); i ++) {
					e = empires.get(i);
					if (e.name().equals(name))
						break;
				}
				e.addPoint(Integer.parseInt(line.substring(0,5)), Integer.parseInt(line.substring(6,9)));
			}
			line = empireReader.readLine();
		} while (line != null);
	}

}
