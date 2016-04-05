package world;

import robot.Robot;
import robot.Command;
import noises.*;
import robotAI.*;
import draw.WorldAnimation;
import math.MyMath;
import draw.Screen;
import draw.ScreenResolution;
import java.util.Vector;

import javax.swing.JFrame;

import java.util.HashSet;
import java.util.Iterator;

public class World {
	private Robot robot;
	private RobotAI robotAI;
	private Vector<Double> target;
	private HashSet<AreaObject> anomaly = new HashSet<AreaObject>();
	private HashSet<NoiseGenerator> noises = new HashSet<NoiseGenerator>();
	private WorldAnimation animation;
	private JFrame window = new JFrame("Robot");
	private Screen screen = new Screen();
	private int speed;
	public final double epsilon;
	
	public World(Robot robot, RobotAI robotAI, Vector<Double> target,double epsilon, int speed){
		this.robot = robot;
		this.robotAI = robotAI;
		this.target = target;
		this.epsilon = epsilon;
		this.speed = speed;
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(ScreenResolution.getSize("width"), ScreenResolution.getSize("height"));
		window.setLocationRelativeTo(null);
		window.add(screen);
		window.setVisible(true);
	}
	
	public double go(){
		animation = new WorldAnimation(anomaly, robot, 20, target.get(0),target.get(1));
		animation.animate(screen);
		draw(screen,40);
		while(!robotAI.isReached(robot)){
			Command cmd = robotAI.nextCmd(robot, target);
			robot = processPath(robot, cmd);
			/*if(robotAI instanceof EasyAIwithNoises){
				System.out.println(((EasyAIwithNoises)robotAI).getWall());
			}*/
		}
		return robotAI.getTime();
	}
	
	public void addAnomaly(AreaObject anom){
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
			//
			double dw = cmd.time*cmd.rotationSpeed;
			//
			int intervalNum = (int)Math.floor(distance / epsilon);
			double timeExcess = cmd.time;
			double timeStep = 0;
			if(distance>dw){
				intervalNum = (int)Math.floor(distance / epsilon);
				timeExcess = cmd.time;
				timeStep = 0;
				if(intervalNum>0){
					timeExcess = cmd.time*(distance - intervalNum*epsilon)/distance;
					timeStep = (cmd.time-timeExcess)/intervalNum;
				}
			}
			else{
				intervalNum = (int)Math.floor(dw / epsilon);
				timeExcess = cmd.time;
				timeStep = 0;
				if(intervalNum>0){
					timeExcess = cmd.time*(dw - intervalNum*epsilon)/dw;
					timeStep = (cmd.time-timeExcess)/intervalNum;
				}
			}
			Command bufCommand;
			Robot bufRobot = robot;
			for(int i = 0; i < intervalNum; i++){
				bufCommand = new Command(cmd.speed, cmd.rotationSpeed, timeStep);
				Robot buffRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed, 
						bufRobot.angle, robot.radius, bufRobot.getPos());
				buffRobot = buffRobot.move(bufCommand);
				buffRobot = affect(buffRobot);
				if(MyMath.distance(buffRobot.getPos(), bufRobot.getPos())<=2*epsilon){
				bufRobot = new Robot(buffRobot.maxRotationSpeed, buffRobot.maxSpeed, 
						bufRobot.angle,bufRobot.radius, bufRobot.getPos());
				}
				else{
					bufRobot = new Robot(buffRobot.maxRotationSpeed, buffRobot.maxSpeed, 
							bufRobot.angle,bufRobot.radius, buffRobot.getPos());
				}
				animation.setRobot(bufRobot);
				bufRobot = bufRobot.move(bufCommand);
				bufRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  bufRobot.angle,
						robot.radius, bufRobot.getPos());
				if(i % 4 == 0){
					draw(screen, speed);
					window.repaint();
				}
			}
			bufCommand = new Command(cmd.speed, cmd.rotationSpeed, timeExcess);
			Robot buffRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed, 
					bufRobot.angle, robot.radius, bufRobot.getPos());
			buffRobot = buffRobot.move(bufCommand);
			buffRobot = affect(buffRobot);
			bufRobot = new Robot(buffRobot.maxRotationSpeed, buffRobot.maxSpeed, 
					bufRobot.angle,bufRobot.radius, bufRobot.getPos());
			animation.setRobot(bufRobot);
			bufRobot = bufRobot.move(bufCommand);
			bufRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  bufRobot.angle,
					robot.radius, bufRobot.getPos());
			animation.setRobot(bufRobot);
			draw(screen, speed);
		/*	if(timeExcess*cmd.rotationSpeed>0.01){
				double num = timeExcess*cmd.rotationSpeed/0.01;
				for(int i = 0; i<num; i++){
					bufCommand = new Command(cmd.speed, cmd.rotationSpeed, timeExcess/num);
					Robot buffRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed, 
							bufRobot.angle, robot.radius, bufRobot.getPos());
					buffRobot = buffRobot.move(bufCommand);
					buffRobot = affect(buffRobot);
					bufRobot = new Robot(buffRobot.maxRotationSpeed, buffRobot.maxSpeed, 
							bufRobot.angle,bufRobot.radius, bufRobot.getPos());
					animation.setRobot(bufRobot);
					bufRobot = bufRobot.move(bufCommand);
					bufRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  bufRobot.angle,
							robot.radius, bufRobot.getPos());
					animation.setRobot(bufRobot);
					draw(screen, speed);
				}
				return bufRobot;
			}*/
			return bufRobot;
		}
		else{
			double step = cmd.time/200;
			Robot bufRobot = robot;
			animation.setRobot(bufRobot);
			for(int i = 0; i<200; i++){
				bufRobot = bufRobot.move(new Command(0, cmd.rotationSpeed, step));
				if(i % 3 == 0){
					animation.setRobot(bufRobot);
					draw(screen,speed);
					window.repaint();
				}
			}
			return robot.move(cmd);
		}
	}
	
	private Robot affect(Robot robot){
		Robot epsRobot = new Robot(robot.maxRotationSpeed, robot.maxSpeed,  robot.angle,
				robot.radius+epsilon, robot.getPos());
		Robot res = robot;
		Iterator<AreaObject> anomalyIter = anomaly.iterator();
		while(anomalyIter.hasNext()){
			AreaObject buf = anomalyIter.next();
			if (buf.isActive(epsRobot)){
				res = buf.affect(res);
				if((robotAI instanceof EasyAIwithNoises)&&(buf.getType().equals("Wall"))){
					((EasyAIwithNoises) robotAI).setWall((Wall)buf);
				}
				else if(buf.getType().equals("Teleport")){
					((EasyAIwithNoises) robotAI).setWall(null);
				}
			}
		}
		return res;
	}
	
	private Command noiseCommand(Command cmd){
		Command res = cmd;
		for(NoiseGenerator i : noises){
			res = i.changeCmd(res);
		}
		return res;
	}
	
	private void draw(Screen screen, int step){
		animation.animate(screen);
		double time = System.currentTimeMillis();
		while(time+step>System.currentTimeMillis()){}
	}
}
