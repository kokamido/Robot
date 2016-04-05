package draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import draw.Drawable;
import java.util.Vector;

public class Circle implements Drawable{
	private int x;
	private int y;
	private int rad;
	private Color color;
	
	public Circle(int x, int y, int rad, Color color){
		this.x = x;
		this.y = y;
		this.rad = rad;
		this.color = color;
	}
	
	public Circle(double x, double y, double rad, Color color){
		this.x = (int)x;
		this.y = (int)y;
		this.rad = (int)rad;
		this.color = color;
	}
	
	public void setCenter(Vector<Integer> vec){
		x = vec.get(0);
		y = vec.get(1);
	}
	public void setCenter(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Ellipse2D el = new Ellipse2D.Double(/*(Math.max(x-rad,0)*/x-rad, /*Math.max(y-rad,0)*/y-rad, 2*rad, 2*rad);
		g.setPaint(color);
		g.fill(el);
	}
}
