package robotAI;


import glebDataTypes.Position;
import glebToMe.Converter;
import robot.Robot;
import noises.*;
import glebMath.*;
import java.util.Vector;

public class EasyAIwithNoises extends SimpleAI {
	private double eps =0.1/* 0.00001*/;
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
	
	public robot.Command nextCmd(Robot robot, Vector<Double> target){
		return getCommand(robot, Converter.positionToGleb(target));
	}
	
	public robot.Command getCommand(Robot robot, Position finish) {
		robot.Command cmd;
		if (lastWall == null){
			System.out.println("да ну нах");
			RobotAI easyAI = new EasyRobotAI(super.target);
			cmd = easyAI.nextCmd(robot, Converter.positionToMe(finish));
			return cmd;
		}
	
		System.out.println("бля, ебаная стена");

	
	
		double dr = D.r(Converter.positionToGleb(robot.getPos()), finish, Converter.positionToGleb(lastWall.getPos())) - robot.radius - lastWall.radius;
		
		if ((dr > 0) || (Math.abs(D.w(D.angle(Converter.positionToGleb(robot.getPos()), finish),
			
				D.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos())))) > Math.PI / 2)) {
			System.out.println("пох, не мешает");
			RobotAI easyAI = new EasyRobotAI(super.target);
			cmd = easyAI.nextCmd(robot, Converter.positionToMe(finish));
		} else {
			System.out.println("я яйцами пезднулся!");
			double dw = (robot.angle - D.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos())));
		
			if ((dw >= Math.PI / 2) /*&& (dw < Math.PI / 2 + eps)*/) {

				double radiuscirculation = robot.radius + eps + lastWall.radius;
				
				double endAngle = Math.acos(radiuscirculation / D.l(Converter.positionToGleb(lastWall.getPos()), finish));
				System.out.println(D.l(Converter.positionToGleb(lastWall.getPos()), finish));
				double startAngle = Math.acos((D.l(Converter.positionToGleb(robot.getPos()), finish) * D.l(Converter.positionToGleb(robot.getPos()), finish)
						- D.l(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))
								* D.l(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))
						- D.l(finish, Converter.positionToGleb(lastWall.getPos())) * D.l(finish, Converter.positionToGleb(lastWall.getPos())))
						/ (-2 * D.l(finish, Converter.positionToGleb(lastWall.getPos()))
								* D.l(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))));
				System.out.println("Объезжай теперь, блядь");
				if (robot.maxSpeed / robot.maxRotationSpeed > radiuscirculation) {
					System.out.println("сука");
					/*cmd.rotationSpeed = robot.maxRotationSpeed;
					cmd.speed = cmd.rotationSpeed * radiuscirculation;*/
					cmd = new robot.Command(robot.maxRotationSpeed* radiuscirculation,
							robot.maxRotationSpeed,
							D.w(endAngle, startAngle) / robot.maxRotationSpeed);
				} else {
					/*cmd.speed = robot.maxSpeed;
					cmd.rotationSpeed = radiuscirculation / cmd.speed;*/
					System.out.println(endAngle);
					cmd = new robot.Command(robot.maxSpeed,radiuscirculation /robot.maxSpeed,
							D.w(endAngle, startAngle) *robot.maxSpeed/ (radiuscirculation ));
				}
				/*cmd.time = d.w(endAngle, startAngle) / cmd.rotationSpeed;*/

			} else {
				/*cmd.speed = 0;
				cmd.rotationSpeed = Math.signum(Math.PI / 2 - dw) * robot.maxRotationSpeed;
				cmd.time = (Math.PI / 2 - dw) / cmd.rotationSpeed;*/
				System.out.println("Вертел я вас");
				cmd = new robot.Command(0,
						  robot.maxRotationSpeed,
						Math.abs(Angle.normalizer((Math.PI / 2 + eps - dw))) /( robot.maxRotationSpeed));
			
			}

		}
		
		return cmd;
	}
	
}
