package robot;

import robot.Command;

import myExceptions.IncomingCommandException;
import math.MyMath;

import glebMath.*;
import java.util.Vector;

public class Robot {

	public final double maxRotationSpeed;
	public final double maxSpeed;
	public final double x;
	public final double y;
	public final double angle;
	public final double radius;

	public Robot(double maxRotationSpeed, double maxSpeed, double angle, double radius, double... coords) {
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.angle = MyMath.normalizeAngle(angle);
		this.radius = radius;
		this.x = coords[0];
		this.y = coords[1];
	}

	public Robot(double maxRotationSpeed, double maxSpeed, double angle, double radius, Vector<Double> coords) {
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.angle = MyMath.normalizeAngle(angle);
		this.radius = radius;
		this.x = coords.get(0);
		this.y = coords.get(1);
	}

	public Vector<Double> getPos() {
		Vector<Double> pos = new Vector<Double>();
		pos.add(x);
		pos.add(y);
		return pos;
	}

	public Robot move(Command cmd) {
		Command realCMD = new Command( Math.min(maxSpeed, cmd.speed), Math.min(maxRotationSpeed, Math.abs(cmd.rotationSpeed))*Math.signum(cmd.rotationSpeed),cmd.time);
		if (realCMD.rotationSpeed == 0) {
			Vector<Double> newCoords = new Vector<Double>();
			double newX = x + realCMD.speed * realCMD.time * Math.cos(angle);
			double newY = y + realCMD.speed * realCMD.time * Math.sin(angle);
			newCoords.add(0, newX);
			newCoords.add(1, newY);
			double newDirection = angle + realCMD.rotationSpeed * realCMD.time;
			Robot newRobot = new Robot(maxRotationSpeed, maxSpeed, newDirection, radius, newCoords);
			return newRobot;
		} else {
			double radiusCirkulation = Math.abs(realCMD.speed / realCMD.rotationSpeed);
			double startAngle = Angle.normalizer(angle - (Math.signum(realCMD.rotationSpeed) * (Math.PI / 2)));
			double centerCirculationX = (x - Math.cos(startAngle) * radiusCirkulation);
			double centerCirculationY = (y - Math.sin(startAngle) * radiusCirkulation);
			double endAngle = Angle.normalizer(startAngle + realCMD.rotationSpeed * realCMD.time);
			Vector<Double> newCoords = new Vector<Double>();
			newCoords.add(0, (centerCirculationX + Math.cos(endAngle)*radiusCirkulation));
			newCoords.add(1, (centerCirculationY + Math.sin(endAngle)*radiusCirkulation));
			double newDirection = angle + realCMD.rotationSpeed * realCMD.time;

			Robot newRobot = new Robot(maxRotationSpeed, maxSpeed, newDirection, radius, newCoords);
			return newRobot;
		}
	}

	// расчет движения по дуге
	/*private Vector<Double> getNewPos(Command cmd, double speed, double rotationSpeed, double time) {
		double rad = Math.abs(speed / rotationSpeed);
		double rotAngle = Math.abs(rotationSpeed * time);
		Vector<Double> newCoords = new Vector<Double>();
		// смещаю центр в 0 и пересчитываю
		// координаты точки
		newCoords.add(rad * Math.sin(angle) * Math.signum(rotationSpeed));
		newCoords.add((-1) * rad * Math.cos(angle) * Math.signum(rotationSpeed));
		Vector<Double> rotCoords = new Vector<Double>();
		// смещаю на вектор перемещения
		if (rotationSpeed > 0) {
			rotCoords.add(newCoords.get(0) * Math.cos(rotAngle) - newCoords.get(1) * Math.sin(rotAngle));
			rotCoords.add(newCoords.get(0) * Math.sin(rotAngle) + newCoords.get(1) * Math.cos(rotAngle));
		}
		if (rotationSpeed < 0) {
			rotCoords.add(newCoords.get(0) * Math.cos(2 * Math.PI - rotAngle)
					- newCoords.get(1) * Math.sin(2 * Math.PI - rotAngle));
			rotCoords.add(newCoords.get(0) * Math.sin(2 * Math.PI - rotAngle)
					+ newCoords.get(1) * Math.cos(2 * Math.PI - rotAngle));
		}
		// переношу центр на место
		if (rotationSpeed == 0) {
			newCoords.set(0, x + Math.cos(angle) * speed * time);
			newCoords.set(1, y + Math.sin(angle) * speed * time);
		} else {
			newCoords.set(0, rotCoords.get(0) + x + rad * Math.signum(rotationSpeed) * (-1) * Math.sin(angle));
			newCoords.set(1, rotCoords.get(1) + y + rad * Math.signum(rotationSpeed) * Math.cos(angle));
		}
		return newCoords;
	}*/

}
