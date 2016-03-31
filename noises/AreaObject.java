package noises;

import noises.Anomaly;
import robot.Robot;
import math.MyMath;
import java.util.Vector;

public abstract class AreaObject implements Anomaly {
	public final double xCenterCoord;
	public final double yCenterCoord;
	public final double radius;
	
	public AreaObject(double xCenter, double yCenter, double radius){
		xCenterCoord = xCenter;
		yCenterCoord = yCenter;
		this.radius = radius;
	}
	
	public boolean crossOverRobot(Robot robot){
		return(MyMath.distance(robot.getPos(),getPos())<=(robot.radius+radius));
	}
	
	abstract public Robot affect(Robot robot);
	
	private Vector<Double> getPos(){
		Vector<Double> res = new Vector<Double>();
		res.add(xCenterCoord);
		res.add(yCenterCoord);
		return res;
	}
}
