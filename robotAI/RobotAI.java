package robotAI;

import robot.Command;
import robot.Robot;
import java.util.Vector;

public interface RobotAI {
	public Command nextCmd(Robot robot, Vector<Double> target);
	public boolean isReached(Robot robot);
	public double getTime();
}
