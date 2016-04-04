package tests;

import java.util.Vector;
import robot.Robot;
import robotAI.EasyAIwithNoises;
import world.World;

public class ROLLING {
	public static void main(String[] args){
		Vector<Double> target = new Vector<Double>();
		target.add(5.0);
		target.add(5.0);
		World eden = new World(new Robot(1,1,0,1,0,0), new TestAI(target), target,0.001);
		eden.go();
		}
	}

