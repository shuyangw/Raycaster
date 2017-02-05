package structures;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DisplayWindow extends JFrame{
	private Container c;
	
	public DisplayWindow(String titleText, int width, int height){
		super(titleText);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(width, height));
		c = this.getContentPane();
	}
	
	public void  addPanel(JPanel p){
		c.add(p);
	}
	
	public void showFrame(){
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
