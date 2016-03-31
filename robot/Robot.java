package robot;

import robot.Command;
import myExceptions.IncomingCommandException;
import math.MyMath;
import java.util.Vector;

public class Robot {
	
	
	public final double maxRotationSpeed;
	public final double maxSpeed;
	public final double x;
	public final double y;
	public final double angle;
	public final double radius;
	
	public Robot(double maxRotationSpeed, double maxSpeed, double angle, double radius, double... coords){
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.angle = MyMath.normalizeAngle(angle);
		this.radius = radius;
		this.x = coords[0];
		this.y = coords[1];
	}
	
	public Robot(double maxRotationSpeed, double maxSpeed, double angle, double radius, Vector<Double> coords){
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.angle = MyMath.normalizeAngle(angle);
		this.radius = radius;
		this.x = coords.get(0);
		this.y = coords.get(1);
	}
	
	public Vector<Double> getPos(){
		Vector<Double> pos = new Vector<Double>();
		pos.add(x);
		pos.add(y);
		return pos;
	}
	
	public Robot move(Command cmd){
		//Проверка корректности скорости
		double speed = Math.min(cmd.speed, maxSpeed);
		try{
			if (cmd.speed<0){
				throw new IncomingCommandException("The specified speed is less than zero.");
			}
		}
		catch(IncomingCommandException e){
			System.out.println(e.getMessage());
		}
		
		double rotationSpeed = Math.signum(cmd.rotationSpeed)*
				Math.min(Math.abs(cmd.rotationSpeed), maxRotationSpeed);
		
		//Проверка коректности времени
		double time = 0;
		try{
			time = cmd.time;
			if (cmd.time<0){
				throw new IncomingCommandException("The specified command time is less than a zero: "+cmd.time);
			}
		}
		catch(IncomingCommandException e){
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//Cчитаю новые координаты
		double newAngle = MyMath.normalizeAngle(angle + time*rotationSpeed);
		Vector<Double> newPos = new Vector<Double>();
		newPos.add(0.0);
		newPos.add(0.0);
		if(rotationSpeed == 0){
			newPos.set(0, x+speed*time*Math.cos(angle));
			newPos.set(1, y+speed*time*Math.sin(angle));
		}
		else{
			newPos = getNewPos(cmd,speed,rotationSpeed,time);
		}
		return new Robot(maxRotationSpeed, maxSpeed, newAngle, radius, newPos.get(0), newPos.get(1));
	}
	
	//расчет движения по дуге
	private Vector<Double> getNewPos(Command cmd, double speed, double rotationSpeed,double time){
		double rad = Math.abs(speed/rotationSpeed);
		double rotAngle = Math.abs(rotationSpeed*time);
		Vector<Double> newCoords = new Vector<Double>();
		//смещаю центр в 0 и пересчитываю координаты точки
		newCoords.add(rad*Math.sin(angle)*Math.signum(rotationSpeed));
		newCoords.add((-1)*rad*Math.cos(angle)*Math.signum(rotationSpeed));
		Vector<Double> rotCoords = new Vector<Double>();
		//смещаю на вектор перемещения
		if(rotationSpeed>0){
			rotCoords.add(newCoords.get(0)*Math.cos(rotAngle)-newCoords.get(1)*Math.sin(rotAngle));
			rotCoords.add(newCoords.get(0)*Math.sin(rotAngle)+newCoords.get(1)*Math.cos(rotAngle));
		}
		if(rotationSpeed<0){
			rotCoords.add(newCoords.get(0)*Math.cos(2*Math.PI-rotAngle)-newCoords.get(1)*Math.sin(2*Math.PI-rotAngle));
			rotCoords.add(newCoords.get(0)*Math.sin(2*Math.PI-rotAngle)+newCoords.get(1)*Math.cos(2*Math.PI-rotAngle));
		}
		//переношу центр на место
		if(rotationSpeed == 0){
			newCoords.set(0, x+Math.cos(angle)*speed*time);
			newCoords.set(1, y+Math.sin(angle)*speed*time);
		}
		else{
			newCoords.set(0, rotCoords.get(0)+x+rad*Math.signum(rotationSpeed)*(-1)*Math.sin(angle));
			newCoords.set(1, rotCoords.get(1)+y+rad*Math.signum(rotationSpeed)*Math.cos(angle));	
		}
		return newCoords;
	}
	 
}
