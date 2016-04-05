package tests;

import static org.junit.Assert.*;
import org.junit.*;
import robot.Robot;
import robotAI.*;
import world.World;
import noises.*;
import java.util.Vector;


public class God {
	
	static Vector<Double> target = new Vector<Double>();

	@BeforeClass
	public static void oneTimeSetUp(){
		target.add(5.0);
		target.add(5.0);
	}
		/*
	@Test
	public void plusPi4Sqrt50(){
		target.set(0, 5.0);
		target.set(1, 5.0);	
		World eden = new World(new Robot(1,1,0,1,0,0), new EasyAIwithNoises(target), target,0.001);
		assertEquals(eden.go(),Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void plus3Pi4sqrt50(){
		target.set(0,-5.0);
		target.set(1,5.0);
		World earth = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001);
		assertEquals(earth.go(),3*Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void minus3Pi4sqrt50(){
		target.set(0,-5.0);
		target.set(1,-5.0);
		World purgatory = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001);
		assertEquals(purgatory.go(),3*Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void run(){
		target.set(0,-5.0);
		target.set(1,0.0);
		World hell = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001);
		assertEquals(hell.go(),Math.PI+4,0.0000001);
	}
	
	@Test
	public void unfairTarget(){
		target.set(0,0.0);
		target.set(1,0.0);
		World unfair = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001);
		assertEquals(unfair.go(),2+Math.PI+1,0.0000001);
	}
	
	@Test
	public void swampTest(){
		target.set(0,5.0);
		target.set(1,0.0);
		Swamp swampr1m05 = new Swamp(0,0,1,0.5);
		World eden = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001);
		eden.addAnomaly(swampr1m05);
		assertEquals(eden.go(),6,0.01);
	}
	*/
	@Test
	public void wallTest(){
		target.set(0,9.0);
		target.set(1,0.0);
		Wall wallr1 = new Wall(4,0,1);
	//	Wall wallr3 = new Wall(6,2,2);
		World eden = new World(new Robot(1,1,Math.PI/2,1,0,0), new EasyAIwithNoises(target), target,0.001);
		eden.addAnomaly(wallr1);
		//eden.addAnomaly(wallr3);
		assertEquals(eden.go(),6,0.01);
	}
	
}

