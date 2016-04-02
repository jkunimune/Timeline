package timeline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TimelineGenerator {

	public static void main(String[] args) throws IOException {
		ArrayList<Empire> empires = new ArrayList<Empire>();
		
		BufferedReader empireReader = new BufferedReader(new FileReader("Empires.txt"));	// get the list of empires
		String line = "";
		do {
			if (!line.isEmpty())
				empires.add(new Empire(Integer.parseInt(line.substring(0,1)), line.substring(2)));
			line = empireReader.readLine();
		} while (line != null);
		empireReader.close();
		
		BufferedReader areaReader = new BufferedReader(new FileReader("Timeline.txt"));		// get the sizes of all the empires
		line = "";
		do {
			if (!line.isEmpty()) {
				String name = line.substring(10);
				Empire e = null;
				for (int i = 0; i < empires.size(); i ++) {
					e = empires.get(i);
					if (name.equals(e.name()))
						break;
				}
				e.addPoint(Integer.parseInt(line.substring(0,5)), Integer.parseInt(line.substring(6,9)));
			}
			line = areaReader.readLine();
		} while (line != null);
		areaReader.close();
		
		System.out.println(empires);
		
		BufferedImage output = new BufferedImage(1000,4016, BufferedImage.TYPE_INT_RGB);
		for (int t = -2000; t < 2016; t ++) {
			ArrayList<Integer> bounds1 = new ArrayList<Integer>(1);	// empire sizes at t-1
			ArrayList<Integer> bounds2 = new ArrayList<Integer>(1);	// empire sizes at t
			ArrayList<Color> colors = new ArrayList<Color>(1);
			bounds1.add(new Integer(0));
			bounds2.add(new Integer(0));
			colors.add(Color.BLACK);
			int x1 = 0;
			int x2 = 0;
			for (Empire e: empires) {
				if (e.sizeAt(t-1) > 0 || e.sizeAt(t) > 0) {	// decides which empires existed around this time
					x1 += e.sizeAt(t);
					bounds1.add(new Integer(x1));
					x2 += e.sizeAt(t+1);
					bounds2.add(new Integer(x2));
					colors.add(e.getColor());
				}
			}
			Graphics2D g = (Graphics2D)output.getGraphics();
			g.setColor(Color.WHITE);
			g.drawLine(0, t+2000, 999, t+2000);
			for (int i = 1; i < colors.size(); i ++) {
				g.setColor(colors.get(i));
				g.setStroke(new BasicStroke(1));
				g.drawLine(bounds2.get(i-1), t+2000, bounds2.get(i), t+2000);
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(3));
				g.drawLine(bounds1.get(i), t+1999, bounds2.get(i), t+2000);
			}
		}
		File outputFile = new File("history.jpg");
		ImageIO.write(output, "jpg", outputFile);
		Desktop.getDesktop().open(outputFile);
	}

}
