import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * Class that manages the key listener
 * Reads keyboard input of WASD movement keys and moves accordingly
 */

public class MoveAction extends AbstractAction
{
	private static final long serialVersionUID = -2806121074105088265L;
	private String cmd;
	
	public MoveAction(String cmd)
	{
		this.cmd = cmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("action");
		try{
			switch(cmd)
			{
				case "W":
					System.out.println("W");
					MainClass.move("forwards");
					break;
				case "S":
					System.out.println("S");
					MainClass.move("backwards");
					break;
				case "D":	
					System.out.println("D");
					MainClass.rotation("right");
					break;
				case "A":	
					System.out.println("A");
					MainClass.rotation("left");
					break;
				default: break;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
	}
}
