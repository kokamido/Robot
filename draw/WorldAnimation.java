package draw;

import draw.Animation;
import draw.Screen;
import draw.ScreenResolution;
import draw.Circle;
import noises.AreaObject;
import robot.Robot;
import draw.RobotPicture;
import java.awt.Color;
import java.util.HashSet;

public class WorldAnimation implements Animation{
	private HashSet<AreaObject> relief;
	private Robot robot;
	private int scale;
	private int targetX;
	private int targetY;
	
	public WorldAnimation(HashSet<AreaObject> relief, Robot robot, int scale, double x, double y){
		this.relief = relief;
		this.robot = robot;
		this.scale = scale;
		targetX = (int)x;
		targetY = (int)y;
	}
	
	public void animate(Screen screen){
		screen.clear();
		for(AreaObject i : relief){
			if((Math.abs(i.xCenterCoord-robot.x)-i.radius<
					ScreenResolution.getSize("width")/scale)&&
					(Math.abs(i.yCenterCoord-robot.y)-i.radius<
							ScreenResolution.getSize("height")/scale)){
				screen.addDrawable(new Circle((i.xCenterCoord-robot.x)*scale+ScreenResolution.getSize("width")/2,
						(i.yCenterCoord-robot.y)*scale+ScreenResolution.getSize("height")/2,
						i.radius*scale, getColor(i)));
			}
		}
		screen.addDrawable(new RobotPicture(robot, scale));
		screen.addDrawable(new Circle((targetX - robot.x)*scale+ScreenResolution.getSize("width")/2,
				(targetY-robot.y)*scale+ScreenResolution.getSize("height")/2, 5, Color.ORANGE));
	}
	
	public void setRobot(Robot robot){
		this.robot = robot;
	}
	
	private Color getColor(AreaObject obj){
		if(obj.getType().equals("Swamp")){
			return Color.GREEN;
		}
		else if(obj.getType().equals("Wall")){
			return Color.GRAY;
		}
		else{
			return Color.RED;
		}
	}
}
