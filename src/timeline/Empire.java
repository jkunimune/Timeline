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
		color = Color.getHSBColor(region/11f, 1f, 1f);
		name = newName;
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
		if (year < time.get(0))
			return 0;
		if (year >= time.get(time.size()-1))
			return 0;
		
		int i = 0;
		while (time.get(i) < year)
			i ++;
		return map(year, time.get(i-1),time.get(i), area.get(i-1),area.get(i));
	}
	
	
	public static int map(int x, int x0, int x1, int y0, int y1) {
		return y0 + (x-x0)*(y1-y0)/(x1-x0);
	}
}
