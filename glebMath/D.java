package glebMath;

import glebDataTypes.*;

public class D {
	public static double x(Position obj1, Position obj2) {
		return (obj2.x - obj1.x);
	}

	public static double y(Position obj1, Position obj2) {
		return (obj2.y - obj1.y);
	}

	public static double l(Position obj1, Position obj2) {
		double dx = D.x(obj1, obj2);
		double dy = D.y(obj1, obj2);
		double dl = Math.sqrt(dx * dx + dy * dy);
		return dl;
	}

	public static double angle(Position pos1, Position pos2) {
		double angl = (Math.acos(D.x(pos1, pos2) / D.l(pos1, pos2))) * Math.signum(D.y(pos1, pos2)) % (Math.PI);
		return angl;
	}

	public static double w(double angle1, double angle2) {
		return (angle2 % Math.PI) - (angle1 % Math.PI);
	}

	public static double r(Position pos1, Position pos2, Position pos3) {
		System.out.println("hui");
		return Math.abs((pos1.y + (pos2.y - pos1.y)/(pos2.x - pos1.x)*(pos3.x - pos1.x) - pos3.y ))/ Math.sqrt(1+(pos2.y - pos1.y)/(pos2.x - pos1.x)*(pos2.y - pos1.y)/(pos2.x - pos1.x));
	}

}
