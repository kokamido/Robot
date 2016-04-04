package noises;

import noises.NoiseGenerator;
import robot.Command;
import java.util.Random;

public class ENoise implements NoiseGenerator{
	
	private double amplitude;
	private Random rand = new Random(System.currentTimeMillis());
	
	public ENoise(double eps){
		amplitude = eps;
	}
	
	public Command changeCmd(Command cmd){
		return new Command(cmd.speed+amplitude*rand.nextInt(100000)/100000,
				cmd.rotationSpeed+amplitude*rand.nextInt(100000)/100000,
				cmd.time+amplitude*rand.nextInt(100000)/100000);
	}
}
