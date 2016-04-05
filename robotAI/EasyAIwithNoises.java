package robotAI;

import glebDataTypes.Position;
import glebToMe.Converter;
import robot.Robot;
import noises.*;
import glebMath.*;
import java.util.Vector;

public class EasyAIwithNoises extends SimpleAI {
	private double eps = 0.1/* 0.00001 */;
	private Wall lastWall = null;

	public EasyAIwithNoises(Vector<Double> target) {
		super(target);
	}

	public void setWall(Wall wall) {
		lastWall = wall;
	}

	public Wall getWall() {
		return lastWall;
	}

	public robot.Command nextCmd(Robot robot, Vector<Double> target) {
		return getCommand(robot, Converter.positionToGleb(target));
	}

	public robot.Command getCommand(Robot robot, Position finish) {
		robot.Command cmd;
		if (lastWall == null) {
			System.out.println("да ну нах");
			RobotAI easyAI = new EasyRobotAI(super.target);
			cmd = easyAI.nextCmd(robot, Converter.positionToMe(finish));
			return cmd;
		}

		System.out.println("бл€, ебана€ стена");

		double dr = D.r(Converter.positionToGleb(robot.getPos()), finish, Converter.positionToGleb(lastWall.getPos()))
				- robot.radius - lastWall.radius;

		if ((dr > 0) || (Math.abs(D.w(D.angle(Converter.positionToGleb(robot.getPos()), finish),

				D.angle(Converter.positionToGleb(robot.getPos()),
						Converter.positionToGleb(lastWall.getPos())))) > Math.PI / 2)) {
			System.out.println("пох, не мешает");
			RobotAI easyAI = new EasyRobotAI(super.target);
			cmd = easyAI.nextCmd(robot, Converter.positionToMe(finish));
		} else {
			System.out.println("€ €йцами пезднулс€!");
			double dw = Angle.normalizer((Angle.normalizer(robot.angle)
					- D.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos()))));
			System.out.println(
					D.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos())));
			System.out.println(dw + " " + robot.angle + " "
					+ D.angle(Converter.positionToGleb(robot.getPos()), Converter.positionToGleb(lastWall.getPos())));
			if ((dw >= Math.PI / 2) && (dw < Math.PI / 2 + eps)) {

				double radiuscirculation = robot.radius + eps + lastWall.radius;
			/*	if (D.l(Converter.positionToGleb(robot.getPos()),
						Converter.positionToGleb(lastWall.getPos())) < eps+radiuscirculation){*/
					return new robot.Command (robot.maxSpeed, 0, 2*eps);
				}
				/*

				double endAngle = Math
						.acos(radiuscirculation / D.l(Converter.positionToGleb(lastWall.getPos()), finish));
				double triangleAB = radiuscirculation; 
				double triangleBC = D.l(finish,
										Converter.positionToGleb(lastWall.getPos()));
				double triangleAC = D.l(Converter.positionToGleb(robot.getPos()),
										Converter.positionToGleb(lastWall.getPos()));
				double cosA = (triangleBC*triangleBC + 
						triangleAC*triangleAC - triangleAB*triangleAB)
					/(2*triangleBC*triangleAC);
				if (cosA > 1){
					cosA = 1.0;
				}
				if (cosA <-1){
					cosA = -1.0;
				}
				double startAngle = Math.acos(cosA);
				System.out.println(startAngle/Math.PI);
				System.out.println((triangleBC*triangleBC + 
						triangleAC*triangleAC - triangleAB*triangleAB)
					/(2*triangleBC*triangleAC));
				System.out.println(endAngle/Math.PI);
				
				System.out.println("ќбъезжай теперь, бл€дь");
				if (robot.maxSpeed / robot.maxRotationSpeed > radiuscirculation) {
					System.out.println("сука");
					
					cmd = new robot.Command(robot.maxRotationSpeed * radiuscirculation, robot.maxRotationSpeed,
							-D.w(endAngle, startAngle) / robot.maxRotationSpeed);
					System.out.println(startAngle);
					return cmd;
				} else {
					

					cmd = new robot.Command(robot.maxSpeed, -radiuscirculation / robot.maxSpeed,
							D.w(endAngle, startAngle) * robot.maxSpeed / (radiuscirculation));
				}
				*/
			else {
				cmd = new robot.Command(0, robot.maxRotationSpeed* Math.signum(Angle.normalizer((Math.PI / 2 - dw))),
						Math.abs(Angle.normalizer((Math.PI / 2 - dw+ eps/2))) / (robot.maxRotationSpeed));

			}

		}

		return cmd;
	}

}
