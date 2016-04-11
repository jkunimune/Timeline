package timeline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
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
		final int vs = 1;
		
		ArrayList<Empire> empires = new ArrayList<Empire>();
		
		BufferedReader empireReader = new BufferedReader(new FileReader("Empires.txt"));	// get the list of empires
		String line = "";
		do {
			if (!line.isEmpty())
				empires.add(new Empire(Integer.parseInt(line.substring(0,2)), line.substring(3)));
			line = empireReader.readLine();
		} while (line != null);
		empireReader.close();
		
		BufferedReader areaReader = new BufferedReader(new FileReader("Timeline.txt"));		// get the sizes of all the empires
		line = "";
		do {
			if (!line.isEmpty()) {
				String name = line.substring(10);
				Empire e = null;
				for (int i = 0; i <= empires.size(); i ++) {
					if (i < empires.size()) {
						e = empires.get(i);
						if (name.equals(e.name()))
							break;
					}
					else
						System.err.println("Error: Empire \""+name+"\" not found");
				}
				e.addPoint(Integer.parseInt(line.substring(0,5)), Integer.parseInt(line.substring(6,9)));
			}
			line = areaReader.readLine();
		} while (line != null);
		areaReader.close();
		
		BufferedImage output = new BufferedImage(1000*vs,4516, BufferedImage.TYPE_INT_RGB);
		for (int t = -2500; t <= 2016; t ++) {
			ArrayList<Integer> bounds1 = new ArrayList<Integer>(1);	// empire sizes at t
			ArrayList<Color> colors = new ArrayList<Color>(1);
			bounds1.add(new Integer(0));
			colors.add(Color.BLACK);
			int x1 = 0;
			for (Empire e: empires) {
				if (e.sizeAt(t) > 0) {	// decides which empires existed around this time
					x1 += e.sizeAt(t);
					bounds1.add(new Integer(x1));
					colors.add(e.getColor());
				}
			}
			Graphics2D g = (Graphics2D)output.getGraphics();	// draws the shapes
			g.setColor(Color.WHITE);
			g.drawLine(0, t+2500, 1000*vs, t+2500);
			for (int i = 1; i < colors.size(); i ++) {
				g.setColor(colors.get(i));
				g.setStroke(new BasicStroke(1));
				g.drawLine(vs*bounds1.get(i-1), t+2500, vs*bounds1.get(i), t+2500);
			}
		}
		for (int t = -2500; t < 2016; t ++) {
			ArrayList<Integer> bounds1 = new ArrayList<Integer>(1);	// empire sizes at t-1
			ArrayList<Integer> bounds2 = new ArrayList<Integer>(1);	// empire sizes at t
			bounds1.add(new Integer(0));
			bounds2.add(new Integer(0));
			int x1 = 0;
			int x2 = 0;
			for (Empire e: empires) {
				if (e.sizeAt(t-1) > 0 || e.sizeAt(t) > 0) {	// decides which empires existed around this time
					x1 += e.sizeAt(t-1);
					bounds1.add(new Integer(x1));
					x2 += e.sizeAt(t);
					bounds2.add(new Integer(x2));
				}
			}
			Graphics2D g = (Graphics2D)output.getGraphics();	// draws the lines
			for (int i = 1; i < bounds1.size(); i ++) {
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(3));
				g.drawLine(vs*bounds1.get(i), t+2499, vs*bounds2.get(i), t+2500);
			}
		}
		
		File outputFile = new File("history.png");
		ImageIO.write(output, "png", outputFile);
		Desktop.getDesktop().open(outputFile);
	}

}
