package noises;

import noises.AreaObject;
import robot.Robot;

/**
 * 
 * @author Alexander
 * @param speedReduction Speed multiplier, must be < 1.
 *{@literal} Robot's maxSpeed is reduced, when it is going across the swamp.
 *
 */
public class Swamp extends AreaObject{
	
	//Множитель скорости при движении по болоту
	public final double speedReduction;
	
	public Swamp(double xCenter, double yCenter, double radius, double speedReduction){
		super(xCenter, yCenter,radius);
		if(speedReduction<1){
			this.speedReduction = speedReduction;
		}
		else{
			this.speedReduction = 1;
		}
	}
	
	public Robot affect(Robot robot){
		return new Robot(robot.maxRotationSpeed, robot.maxSpeed*speedReduction,
				robot.angle, robot.radius, robot.getPos());
	}
	
}
