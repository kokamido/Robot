package noises;

import noises.AreaObject;
import robot.Robot;

public class Wall extends AreaObject {
	
	public Wall(double xCenter, double yCenter, double radius){
		super(xCenter, yCenter,radius);
	}

	public Robot affect(Robot robot){
		return new Robot(robot.maxRotationSpeed, 0,
				robot.angle, robot.radius, robot.getPos());
	}
}
