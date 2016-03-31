package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import robot.Robot;
import robotAI.SimpleAI;
import world.World;
import noises.*;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Iterator;


public class God {
	
	static Vector<Double> target = new Vector<Double>();

	@Test
	public void plusPi4Sqrt50(){
		target.add(5.0);
		target.add(5.0);
		World eden = new World(new Robot(1,1,0,1,0,0), new SimpleAI(target), target,0.001);
		assertEquals(eden.go(),Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void plus3Pi4sqrt50(){
		target.set(0,-5.0);
		target.set(1,5.0);
		World earth = new World(new Robot(1,1,0,1,0,0), new SimpleAI(target), target,0.001);
		assertEquals(earth.go(),3*Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void minus3Pi4sqrt50(){
		target.set(0,-5.0);
		target.set(1,-5.0);
		World purgatory = new World(new Robot(1,1,0,1,0,0), new SimpleAI(target), target,0.001);
		assertEquals(purgatory.go(),3*Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void run(){
		target.set(0,-5.0);
		target.set(1,0.0);
		World hell = new World(new Robot(1,1,0,1,0,0), new SimpleAI(target), target,0.001);
		assertEquals(hell.go(),Math.PI+4,0.0000001);
	}
	
	@Test
	public void unfairTarget(){
		target.set(0,0.0);
		target.set(1,0.0);
		World unfair = new World(new Robot(1,1,0,1,0,0), new SimpleAI(target), target,0.001);
		assertEquals(unfair.go(),2+Math.PI+1,0.0000001);
	}
	
	@Test
	public void swampTest(){
		target.add(0,5.0);
		target.add(1,5.0);
		Swamp swampr1m05 = new Swamp(0,0,1,0.5);
		World eden = new World(new Robot(1,1,0,1,0,0), new SimpleAI(target), target,0.001);
		eden.addAnomaly(swampr1m05);
		assertEquals(eden.go(),Math.PI/4+Math.sqrt(50),0.001);
	}
	
}

