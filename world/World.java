package world;

import robot.Robot;
import robot.Command;
import noises.*;
import robotAI.*;
import java.util.Vector;
import java.util.HashSet;
import java.util.Iterator;

public class World {
	private Robot robot;
	private RobotAI robotAI;
	private Vector<Double> target;
	private HashSet<Anomaly> anomaly = new HashSet<Anomaly>();
	private HashSet<NoiseGenerator> noises = new HashSet<NoiseGenerator>();
	public final double epsilon;
	
	public World(Robot robot, RobotAI robotAI, Vector<Double> target,double epsilon){
		this.robot = robot;
		this.robotAI = robotAI;
		this.target = target;
		this.epsilon = epsilon;
	}
	
	public double go(){
//		robotAI = new SimpleAI(target);
		while(!robotAI.isReached(robot)){
			Command cmd = robotAI.nextCmd(robot, target);
			robot = processPath(robot, cmd);
			System.out.println(cmd+" "+robot.getPos());
			if(robotAI instanceof EasyAIwithNoises){
				System.out.println(((EasyAIwithNoises)robotAI).getWall());
			}
		}
		return robotAI.getTime();
	}
	
	public void addAnomaly(Anomaly anom){
		anomaly.add(anom);
	}
	
	public void addNoise(NoiseGenerator noise){
		noises.add(noise);
	}
	
	private Robot processPath(Robot robot, Command cmdClear){
		Command cmd = noiseCommand(cmdClear);
		if(cmd.speed != 0){	
			//дроблю путь на кривые, длиной не превышающие эпсилон и еду по ним
			//аффектя робота на каждом шаге
			double distance = cmd.time*cmd.speed;
			int intervalNum = (int)Math.floor(distance / epsilon);
			double timeExcess = cmd.time;
			double timeStep = 0;
			if(intervalNum>0){
				timeExcess = cmd.time*(distance - intervalNum*epsilon)/distance;
				timeStep = (cmd.time-timeExcess)/intervalNum;
			}
			Command bufCommand;
			Robot bufRobot = robot;
			for(int i = 0; i < intervalNum; i++){
				bufCommand = new Command(cmd.speed, cmd.rotationSpeed, timeStep);
				/*bufRobot = affect(bufRobot);
				bufRobot = bufRobot.move(bufCommand);*/
				Robot buffRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  robot.angle,
						robot.radius, bufRobot.getPos());
				buffRobot = buffRobot.move(bufCommand);
				buffRobot = affect(buffRobot);
				bufRobot = new Robot(buffRobot.maxRotationSpeed, buffRobot.maxSpeed, 
						buffRobot.angle,buffRobot.radius, bufRobot.getPos());
//				if(bufRobot.maxSpeed<1){System.out.println(bufRobot.maxSpeed);}
				bufRobot = bufRobot.move(bufCommand);
				bufRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  robot.angle,
						robot.radius, bufRobot.getPos());
			}
			bufCommand = new Command(cmd.speed, cmd.rotationSpeed, timeExcess);
			bufRobot = affect(bufRobot);
			bufRobot = bufRobot.move(bufCommand);
			bufRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  robot.angle,
					robot.radius, bufRobot.getPos());
			return bufRobot;
		}
		else
			return robot.move(cmd);
	}
	
	private Robot affect(Robot robot){
		Robot epsRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  robot.angle,
				robot.radius+epsilon, robot.getPos());
		Robot res = robot;
		Iterator<Anomaly> anomalyIter = anomaly.iterator();
		while(anomalyIter.hasNext()){
			Anomaly buf = anomalyIter.next();
			if (buf.isActive(epsRobot)){
				res = buf.affect(res);
				if((robotAI instanceof EasyAIwithNoises)&&(buf instanceof Wall)){
					((EasyAIwithNoises) robotAI).setWall((Wall)buf);
				}
			}
		}
		return res;
	}
	
	private Command noiseCommand(Command cmd){
		Command res = cmd;
		Iterator<NoiseGenerator> noiseIter = noises.iterator();
		while(noiseIter.hasNext()){
			res = noiseIter.next().changeCmd(res);
		}
		return res;
	}
}
