package tests;

import robotAI.RobotAI;
import java.util.Vector;
import robot.Command;
import robot.Robot;
import robotAI.SimpleAI;

public class TestAI extends SimpleAI implements RobotAI{

	public TestAI(Vector<Double> target) {
		super(target);
	}

	@Override
	public Command nextCmd(Robot robot, Vector<Double> target) {
		return new Command(1,1,1);
	}
}
