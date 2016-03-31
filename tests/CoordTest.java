package tests;

import robot.Robot;
import static org.junit.Assert.*;

import org.junit.Test;
import robot.Command;

public class CoordTest {

	@Test
	public void testMove0(){
		Robot robot = new Robot(1,1,Math.PI/2.0,1,0,0);
		Command cmd = new Command(1,1,Math.PI);
		assertEquals(robot.move(cmd).getPos().get(0),-2,0.001);
		assertEquals(robot.move(cmd).getPos().get(1),0,0.001);
	}

	@Test
	public void testMove1(){
		Robot robot = new Robot(1,1,Math.PI/2.0,1,0,0);
		Command cmd = new Command(1,-1,Math.PI);
		assertEquals(robot.move(cmd).getPos().get(0),2,0.001);
		assertEquals(robot.move(cmd).getPos().get(1),0,0.001);
	}

	@Test
	public void testMove2(){
		Robot robot = new Robot(1,1,Math.PI/2.0,1,0,0);
		Command cmd = new Command(1,-0.1,5*Math.PI);
		assertEquals(robot.move(cmd).getPos().get(0),10,0.001);
		assertEquals(robot.move(cmd).getPos().get(1),10,0.001);
	}
	
	@Test
	public void testMove3(){
		Robot robot = new Robot(1,1,Math.PI/4.0,1,1,1);
		Command cmd = new Command(1,-1,Math.PI);
		assertEquals(robot.move(cmd).getPos().get(0),1+Math.sqrt(2),0.001);
		assertEquals(robot.move(cmd).getPos().get(1),1-Math.sqrt(2),0.001);
	}

	@Test
	public void testMove4(){
		Robot robot = new Robot(1,1,Math.PI/4.0,1,1,1);
		Command cmd = new Command(1,1,Math.PI);
		assertEquals(robot.move(cmd).getPos().get(0),1-Math.sqrt(2),0.001);
		assertEquals(robot.move(cmd).getPos().get(1),1+Math.sqrt(2),0.001);
	}
	
	@Test
	public void testMove5(){
		Robot robot = new Robot(1,1,0,1,0,0);
		Command cmd = new Command(1,0,Math.PI);
		assertEquals(robot.move(cmd).getPos().get(0),Math.PI,0.001);
		assertEquals(robot.move(cmd).getPos().get(1),0.0,0.001);
	}
	
	@Test
	public void testMove6(){
		Robot robot = new Robot(1,1,0,1,0,0);
		Command cmd = new Command(1,0,0.0008);
		assertEquals(robot.move(cmd).getPos().get(0),0.0008,0.000001);
		assertEquals(robot.move(cmd).getPos().get(1),0.0,0.001);
	}

}
