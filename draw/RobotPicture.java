package draw;

import robot.Robot;
import draw.Drawable;
import draw.ScreenResolution;
import java.awt.Color;
import java.awt.Graphics2D;

import draw.Circle;

public class RobotPicture implements Drawable {
	private Drawable robot;
	private Drawable nose;
	
	public RobotPicture(Robot robot, int scale){
		this.robot = new Circle(ScreenResolution.getSize("width")/2, 
				ScreenResolution.getSize("height")/2, 
				robot.radius*scale, Color.BLUE); 
		nose = new Circle(ScreenResolution.getSize("width")/2+Math.cos(robot.angle)*
				robot.radius*scale,
				ScreenResolution.getSize("height")/2+Math.sin(robot.angle)*robot.radius*scale,
				0.2*scale*robot.radius, Color.RED);
	}
	
	public void draw(Graphics2D g){
		robot.draw(g);
		nose.draw(g);
	}
}
