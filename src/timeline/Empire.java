package timeline;

import java.awt.Color;
import java.util.ArrayList;

public class Empire {
	private ArrayList<Integer> time;
	private ArrayList<Integer> area;
	private Color color;
	private String name;
	
	
	public Empire(int region, String newName) {
		time = new ArrayList<Integer>();
		area = new ArrayList<Integer>();
		color = colorByRegion(region);
		name = newName;
	}
	
	
	private Color colorByRegion(int region) {
		switch (region) {
		case 0:
			return new Color(255,000,000);	// sub-saharan africa
		case 1:
			return new Color(255,000,000);	// northern africa
		case 2:
			return new Color(255,127,000);	// europe
		case 3:
			return new Color(255,255,000);	// middle-east
		case 4:
			return new Color(192,255,000);	// india
		case 5:
			return new Color(000,255,063);	// east asia
		case 6:
			return new Color(000,255,255);	// central asia
		case 7:
			return new Color(000,127,255);	// arctic
		case 8:
			return new Color(000,000,255);	// north america
		case 9:
			return new Color(127,000,255);	// central america
		case 10:
			return new Color(255,000,255);	// south america
		default:
			return null;
		}
	}


	public void addPoint(int year, int land) {
		time.add(new Integer(year));
		area.add(new Integer(land));
	}
	
	
	public Color getColor() {
		return color;
	}
	
	
	public String name() {
		return name;
	}
	
	
	public int sizeAt(int year) {
		if (time.size() <= 0)
			return 0;
		if (year <= time.get(0))
			return 0;
		if (year >= time.get(time.size()-1))
			return 0;
		
		int i = 1;
		while (time.get(i) < year)
			i ++;
		return map(year, time.get(i-1),time.get(i), area.get(i-1),area.get(i));
	}
	
	
	public static int map(int x, int x0, int x1, int y0, int y1) {
		return y0 + (x-x0)*(y1-y0)/(x1-x0);
	}
	
	
	@Override
	public String toString() {
		return name+": born in "+time.get(0)+", died in "+time.get(time.size()-1);
	}
}
