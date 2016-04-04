package draw;

import javax.swing.*;
import java.awt.*;
import draw.Screen;

public class DrawTest {
	public static void main(String[] args) throws InterruptedException{
		JFrame window = new JFrame("Robot");
 		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1024, 768);
		window.setLocationRelativeTo(null);
		Screen scr = new Screen();
		Circle c0 = new Circle(100,100,100,Color.BLACK);
		Drawable c1 = new RobotPicture(new robot.Robot(0, 0, 0, 1, 0,0), 10);
		scr.addDrawable(c0);
		scr.addDrawable(c1);
		//c0.setVisible(true);
		//c1.setVisible(true);
		window.add(scr);
		window.setVisible(true);
		while(true){
			for(int i = 100; i<=200;i++){
				double time = System.currentTimeMillis();
				while(System.currentTimeMillis()<time+40){}
				c0.setCenter(i, 100);
			}
			for(int i = 200; i>=100;i=i-1){
				double time = System.currentTimeMillis();
				while(System.currentTimeMillis()<time+40){}
				c0.setCenter(i, 100);	
			}
		}
	//	window.add(c1);
	}
}
