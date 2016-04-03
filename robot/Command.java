package robot;

public class Command {
	public final double speed;
	public final double rotationSpeed;
	public final double time;
	
	public Command(double speed, double rotationSpeed, double time){
		this.speed = speed;
		this.rotationSpeed = rotationSpeed;
		this.time = time;
	}
	
	public String toString(){
		return "Speed: "+speed+" RSpeed: "+rotationSpeed+" Time: "+time;
	}
}
