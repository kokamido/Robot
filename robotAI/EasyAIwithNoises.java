package robotAI;


import glebDataTypes.Position;
import glebToMe.Converter;
import robot.Robot;
import noises.*;
import glebMath.*;
import java.util.Vector;

public class EasyAIwithNoises extends SimpleAI {
	private double eps = 0.00001;
	private Wall lastWall = null;

	public EasyAIwithNoises(Vector<Double> target){
		super(target);
	}
	
	public void setWall(Wall wall){
		lastWall = wall;
	}
	

	public Wall getWall(){
		return lastWall;
	}
	public robot.Command getCommand(Robot robot, Position finish) {
		robot.Command cmd;
		double dr = d.r(Converter.positionToGleb(robot.getPos()), finish, Converter.positionToGleb(lastWall.getPos())) - robot.radius - lastWall.radius;
		if ((dr > 0) || (Math.abs(d.w(d.angle(Converter.positionToGleb(robot.getPos()), finish),
				d.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos())))) > Math.PI / 2)) {
			RobotAI easyAI = new EasyRobotAI(super.target);
			cmd = easyAI.nextCmd(robot, Converter.positionToMe(finish));
		} else {
			double dw = (robot.angle - d.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos())));
			if ((dw >= Math.PI / 2) && (dw < Math.PI / 2 + eps)) {

				double radiuscirculation = robot.radius + eps + lastWall.radius;
				double endAngle = Math.acos(radiuscirculation / d.l(Converter.positionToGleb(lastWall.getPos()), finish));
				double startAngle = Math.acos((d.l(Converter.positionToGleb(robot.getPos()), finish) * d.l(Converter.positionToGleb(robot.getPos()), finish)
						- d.l(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))
								* d.l(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))
						- d.l(finish, Converter.positionToGleb(lastWall.getPos())) * d.l(finish, Converter.positionToGleb(lastWall.getPos())))
						/ (-2 * d.l(finish, Converter.positionToGleb(lastWall.getPos()))
								* d.l(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))));
				if (robot.maxSpeed / robot.maxRotationSpeed > radiuscirculation) {
					/*cmd.rotationSpeed = robot.maxRotationSpeed;
					cmd.speed = cmd.rotationSpeed * radiuscirculation;*/
					cmd = new robot.Command(robot.maxRotationSpeed* radiuscirculation,
							robot.maxRotationSpeed,
							d.w(endAngle, startAngle) / robot.maxRotationSpeed);
				} else {
					/*cmd.speed = robot.maxSpeed;
					cmd.rotationSpeed = radiuscirculation / cmd.speed;*/
					cmd = new robot.Command(robot.maxSpeed,radiuscirculation /robot.maxSpeed,
							d.w(endAngle, startAngle) / (radiuscirculation /robot.maxSpeed));
				}
				/*cmd.time = d.w(endAngle, startAngle) / cmd.rotationSpeed;*/

			} else {
				/*cmd.speed = 0;
				cmd.rotationSpeed = Math.signum(Math.PI / 2 - dw) * robot.maxRotationSpeed;
				cmd.time = (Math.PI / 2 - dw) / cmd.rotationSpeed;*/
				cmd = new robot.Command(0,
						Math.signum(Math.PI / 2 - dw) * robot.maxRotationSpeed,
						(Math.PI / 2 - dw) /(Math.signum(Math.PI / 2 - dw) * robot.maxRotationSpeed));
			}

		}
		return cmd;
	}
	
}
