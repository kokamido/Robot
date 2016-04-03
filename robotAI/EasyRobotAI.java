package robotAI;

import robot.Command;
import glebDataTypes.Position;
import robot.Robot;
import java.util.Vector;

public class EasyRobotAI extends robotAI.SimpleAI {

	private double time;
	
	public EasyRobotAI(Vector<Double> target){
		super(target);
	}
	
	public double getTime(){
		return time;
	}
	
	public Command nextCmd(Robot robot, Vector<Double> target){
		return getCommand(robot, glebToMe.Converter.positionToGleb(target));
	}
	
	public Command getCommand(Robot robot, Position finish){
		double eps = 0.000005;
		double dx = finish.x - robot.x ;
		double dy = finish.y - robot.y ;
		double dl = Math.sqrt(dx*dx + dy*dy);
		double dw = math.MyMath.normalizeAngle((Math.acos(dx/dl)*Math.signum(dy) - robot.angle))/* % (2*Math.PI)*/;		
		if (dy == 0){
			dw = math.MyMath.normalizeAngle((Math.acos(dx/dl) - robot.angle))/* % (2*Math.PI)*/;
		}
	/*	Command command = new Command();*/
		if (dl < robot.radius){
			/*command.angularSpeed = 0;
			command.speed = robot.getMaxSpeed();
			command.time = 2*robot.getRadiusOfRobot()/ robot.getMaxSpeed();*/
			time+=2*robot.radius/robot.maxSpeed;
			return new Command(robot.maxSpeed, 0, 2*robot.radius/robot.maxSpeed);
		}
		if (Math.abs (dl*Math.sin(dw)) < eps && Math.cos(dw) > 0){
			/*command.angularSpeed = 0;
			command.speed = robot.getMaxSpeed();
			command.time =  (dl - robot.getRadiusOfRobot())/ robot.getMaxSpeed();*/
			time+=(dl -robot.radius)/robot.maxSpeed;
			return new Command(robot.maxSpeed, 0, (dl -robot.radius)/robot.maxSpeed);
		}
		else {
/*			command.angularSpeed = robot.getMaxAngularSpeed()*Math.signum(dw);
			command.speed = 0;
			command.time = dw / command.angularSpeed;*/
			time+=dw/robot.maxRotationSpeed;
			return new Command(0 ,robot.maxRotationSpeed*Math.signum(dw),
					Math.abs(dw/robot.maxRotationSpeed));
		}
	}
}
