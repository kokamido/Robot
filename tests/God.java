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

	@Test
	public void plusPi4Sqrt50(){
		target.set(0, 5.0);
		target.set(1, 5.0);	
		World eden = new World(new Robot(1,1,0,1,0,0), new EasyAIwithNoises(target), target,0.001,2);
		assertEquals(eden.go(),Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void plus3Pi4sqrt50(){
		target.set(0,-5.0);
		target.set(1,5.0);
		World earth = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001,2);
		assertEquals(earth.go(),3*Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void minus3Pi4sqrt50(){
		target.set(0,-5.0);
		target.set(1,-5.0);
		World purgatory = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001,2);
		assertEquals(purgatory.go(),3*Math.PI/4+Math.sqrt(50)-1,0.0000001);
	}
	
	@Test
	public void run(){
		target.set(0,-5.0);
		target.set(1,0.0);
		World hell = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001,2);
		assertEquals(hell.go(),Math.PI+4,0.0000001);
	}
	
	@Test
	public void unfairTarget(){
		target.set(0,0.0);
		target.set(1,0.0);
		World unfair = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001,2);
		assertEquals(unfair.go(),2+Math.PI+1,0.0000001);
	}
	
	@Test
	public void swampTest(){
		target.set(0,5.0);
		target.set(1,0.0);
		AreaObject j0 = new Jumper(3,0,1,10);
		Swamp swampr1m05 = new Swamp(0,0,1,0.5);
		World eden = new World(new Robot(1,1,0,1,0,0), new EasyRobotAI(target), target,0.001,2);
		eden.addAnomaly(swampr1m05);
		eden.addAnomaly(j0);
		assertEquals(eden.go(),6,0.01);
	}

	@Test
	public void wallTest(){
		target.set(0,8.0);
		target.set(1,0.0);
		Wall wallr1 = new Wall(6,0,0.8);
		Wall wallr3 = new Wall(6,2,2);
		Wall wallr4 = new Wall(9,-3,1);
		Swamp sw = new Swamp(0,0,9,0.8);
		Teleport tp0 = new Teleport(8,7,1,6,-10);
		Jumper j0 = new Jumper(6,-7.5,1,5);
		NoiseGenerator noise = new ENoise(0.02);
		World eden = new World(new Robot(1.02122121231,1,1,1,0,0), new EasyAIwithNoises(target), target,0.001, 	2);
		eden.addNoise(noise);
		eden.addAnomaly(j0);
		eden.addAnomaly(tp0);
		eden.addAnomaly(wallr1);
		eden.addAnomaly(wallr3);
		eden.addAnomaly(wallr4);
		eden.addAnomaly(sw);
		assertEquals(eden.go(),6,0.01);
	}

}