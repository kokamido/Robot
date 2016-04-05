package noises;

import noises.AreaObject;
import robot.Robot;
import java.util.Vector;

public class Jumper extends AreaObject {
	
	private double time;
	
	public Jumper(double x, double y, double rad, double time){
		super(x,y,rad);
		this.time = time;
	}
	
	public String getType(){
		return "Jumper";
	}
	
	public Robot affect(Robot robot){
		Vector<Double> res = new Vector<Double>();
		res.add(robot.x+time*robot.maxSpeed*Math.cos(robot.angle));
		res.add(robot.y+time*robot.maxSpeed*Math.sin(robot.angle));
		return new Robot(robot.maxRotationSpeed, robot.maxSpeed, robot.angle, robot.radius,res);
	}
}
