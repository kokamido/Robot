package robotAI;

import robot.Command;
import math.MyMath;
import robot.Robot;
import java.util.Vector;

public class SimpleAI implements RobotAI {
	protected Vector<Double> target = new Vector<Double>();
	protected double time;

	public SimpleAI(Vector<Double> target){
		this.target = target;
	}

	public Command nextCmd(Robot robot, Vector<Double> target){
		double angle = calcAngle(robot);
		if(MyMath.distance(target, robot.getPos())>=robot.radius){
			if(Math.abs(angle-robot.angle)<0.00000001){
				time+=MyMath.distance(getNosePos(robot),target)/robot.maxSpeed;
				return new Command(robot.maxSpeed,0,(MyMath.distance(robot.getPos(),target)-robot.radius)/robot.maxSpeed);
			}
			else{
				time+=Math.abs(angle-robot.angle)/robot.maxRotationSpeed;
				return new Command(0,robot.maxRotationSpeed*Math.signum(angle-robot.angle),Math.abs(angle-robot.angle)/robot.maxRotationSpeed);
			}
		}
		else{
			time+=2*robot.radius/robot.maxSpeed;
			return new Command(robot.maxSpeed, 0, 2*robot.radius/robot.maxSpeed);
		}
	}
	
	public boolean isReached(Robot robot){
		if(MyMath.distance(getNosePos(robot), target)<0.0001){
			System.out.println("Total time: "+time);
			return true;
		}
		else{
			return false;
		}
	}
	
	public double getTime(){
		return time;
	}
		
	protected Vector<Double> getNosePos(Robot robot){
		Vector<Double> nosePos = new Vector<Double>();
		nosePos.add(robot.getPos().get(0)+robot.radius*Math.cos(robot.angle));
		nosePos.add(robot.getPos().get(1)+robot.radius*Math.sin(robot.angle));
		return nosePos;
	}
	
	protected double calcAngle(Robot robot){
		Vector<Double> pos = new Vector<Double>();
		pos.add(robot.getPos().get(0));
		pos.add(robot.getPos().get(1));
		for(int i = 0; i<pos.size();i++){
			pos.set(i, target.get(i)-pos.get(i));	
		}
		Vector<Double> oX = new Vector<Double>();
		oX.add(1.0);
		oX.add(0.0);
		double sign = 1;
		if (target.get(1)<0){sign = -sign;}
		return sign*MyMath.normalizeAngle(Math.acos(MyMath.scalarProduct(pos, oX)/MyMath.vectorAbs(pos)));
	}
	
}