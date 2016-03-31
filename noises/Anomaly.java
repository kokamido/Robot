package noises;

import robot.Robot;

public interface Anomaly {
	Robot affect(Robot robot);
	boolean isActive(Robot robot);
}
