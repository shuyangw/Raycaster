package structures;

import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DoDraw extends JPanel{
	private LineDetails cL;
	
	public void receiveLine(LineDetails line){
		this.cL = line;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(cL.color);
		System.out.println("x1: "+cL.x1+", x2: "+cL.x2+", y1: "+cL.y1+", y2: "+cL.y2+"");
		g.drawLine(cL.x1, cL.y1, cL.x1, cL.y2);
	}
}