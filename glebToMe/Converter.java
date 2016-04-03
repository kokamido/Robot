package glebToMe;

import java.util.Vector;
import glebDataTypes.Position;
import glebDataTypes.Command;

public abstract class Converter {
	public static robot.Command CommandToGleb(Command cmd){
		return new robot.Command(cmd.speed, cmd.angularSpeed, cmd.time);
	}
	
	public static Position positionToGleb(Vector<Double> vec){
		return new Position(vec.get(0), vec.get(1));
	}
	
	public static Vector<Double> positionToMe(Position pos){
		Vector<Double> res = new Vector<Double>();
		res.add(pos.x);
		res.add(pos.y);
		return res;
	}
}
