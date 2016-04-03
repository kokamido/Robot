package glebMath;


import glebDataTypes.Position;
import robot.Robot;

public class Distance {
	public static double distance(Robot robot, Position pos) {
		Position face_robot = new Position(robot.radius * Math.cos(robot.angle) + robot.x,
				robot.radius * Math.sin(robot.angle) + robot.y);
		double dx = face_robot.x - pos.x;
		double dy = face_robot.y - pos.y;
		double dl = Math.sqrt(dx * dx + dy * dy);
		return dl;
	}
}
