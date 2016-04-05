package glebMath;

import glebDataTypes.Position;

public class Angle {
	public static double normalizer(double angle){
		double ang = angle % (2*Math.PI);
		if ((ang<= (-Math.PI))||(ang > Math.PI)){
			ang = ang - 2*(Math.PI)*Math.signum(ang);
		}
		return ang;
	}
	public static double angle(Position pos1, Position pos2) {
		if (D.y(pos1, pos2)!= 0){
			double angl = (Math.acos(D.x(pos1, pos2) / D.l(pos1, pos2))) * Math.signum(D.y(pos1, pos2));
			return angl;
		}
		else{
			return (Math.acos(D.x(pos1, pos2) / D.l(pos1, pos2)));
		}
	}
}
