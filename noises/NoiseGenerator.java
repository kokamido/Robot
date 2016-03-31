package noises;

import robot.Command;

public interface NoiseGenerator {
	Command changeCmd(Command cmd);
}
