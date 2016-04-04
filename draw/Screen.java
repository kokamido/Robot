package draw;

import javax.swing.*;
import java.util.LinkedList;
import java.awt.*;
import draw.Drawable;

public class Screen extends JComponent {
	
	private LinkedList<Drawable> toDraw = new LinkedList<Drawable>();
	
	public void addDrawable(Drawable d){
		toDraw.add(d);
	}
	
	public void clear(){
		toDraw = new LinkedList<Drawable>();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);		
		Graphics2D g2d=(Graphics2D)g;	
		try{							//!!!
			for(Drawable i : toDraw){
				i.draw(g2d);
			}
		}
		catch(Throwable t){}
		super.repaint();
	  }	
}
	

