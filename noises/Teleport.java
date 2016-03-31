package noises;

import noises.AreaObject;
import robot.Robot;
import java.util.Vector;

/**
 * 
 * @author Alexander
 * {@literal} It moves the robot to newX, newY.
 *
 */
public class Teleport extends AreaObject{
	public final double newX;
	public final double newY;
	
	public Teleport(double xCenter, double yCenter, double radius, double newX, double newY){
		super(xCenter, yCenter, radius);
		this.newX = newX;
		this.newY = newY;
	}
	
	public Robot affect(Robot robot){
		return new Robot(robot.maxSpeed, robot.maxRotationSpeed, robot.angle,
				robot.radius, getNewPos());
	}
	
	public Vector<Double> getNewPos(){
		Vector<Double> res = new Vector<Double>();
		res.add(newX);
		res.add(newY);
		return res;
	}
}
