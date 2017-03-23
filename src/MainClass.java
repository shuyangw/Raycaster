import structures.Line;
import structures.Vector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MainClass extends JComponent
{
	// Setup fields
	static MainClass comp;
	static LinkedList<Line> lines = new LinkedList<Line>();
	static Vector playerPos;
	static Vector playerDir;
	static Vector cameraDir;
	
	//Defines the dimensions of the window 
	static int width = 1200;
	static int height = 800;
	static double rotSpeed = 0.1;

	//map array
	static int[][] map;
	
	static JFrame frame;

	public void addLine(boolean isVert, int x1, int x2, int x3, int x4, Color color) throws Exception
	{
		lines.add(new Line(isVert, x1, x2, x3, x4, color));
		repaint();
	}

	public static void clearLines()
	{
		lines.clear();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		try
		{
			for (Line line : lines)
			{
				g.setColor(line.color);
				g.drawLine(line.x1, line.y1, line.x2, line.y2);
			}
		}
		catch (Exception e)
		{
			//DO NOTHING
		}
		finally
		{
			for (Line line : lines)
			{
				g.setColor(line.color);
				g.drawLine(line.x1, line.y1, line.x2, line.y2);
			}
		}

	}

	/*
	 * 	Draws a simple map with outer edges as walls and an empty interior
	 * 	EXAMPLE:
	 * 	When setupBasicMap(10,10) is called, the following map is created:
	 * 		{   { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
 	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
 	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	 *			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	 *			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
	 * 
	 */
	private static void setupBasicMap(int horizLength, int height)
	{
		map = new int[horizLength][height];
		for(int i = 0; i < horizLength; i++)
		{
			for(int j = 0; j < height; j++){
				//Runs if our loop reaches a border of the desired map, puts a wall, denoted by a positive integer
				if(i == 0 || j == 0 || i == horizLength-1 || j == height-1)
				{
					map[i][j] = 1;
				}
				//Otherwise fills map with empty space
				else
				{
					map[i][j] = 0;
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		setupFrame();
//		promptInputs();
		basicSetup();
		setupKeyMap();
//		setupBasicMap(20,20);
		playerPos = new Vector(2, 2); // start position of player
		playerDir = new Vector(1.0 / Math.sqrt(2), 1.0 / Math.sqrt(2)); // direction vector
		cameraDir = new Vector(0, 0.66); // camera plane vector
		
//		setupBG();
		
		// GameLoop
		while (true)
		{
			for (int i = 0; i < width; ++i)
			{
				cameraDir.isPerp(playerDir);
				boolean didHit = false;
				double wallDist;
				double camX = 2 * i / (double) width - 1;

				Vector rayPos = new Vector(playerPos.x, playerPos.y);
				Vector rayDir = new Vector(playerDir.x + cameraDir.x * camX, playerDir.y + cameraDir.y * camX);

				int currPosX = (int) rayPos.x;
				int currPosY = (int) rayPos.y;

				double dX = Math.sqrt(1 + (rayDir.y * rayDir.y) / (rayDir.x * rayDir.x));
				double dY = Math.sqrt(1 + (rayDir.y * rayDir.x) / (rayDir.y * rayDir.y));

				Vector sideDistance = new Vector();
				Vector move = new Vector();
				int side = 0;
				if (rayDir.x < 0)
				{
					move.x = -1;
					sideDistance.x = (rayPos.x - currPosX) * dX;
				} 
				else
				{
					move.x = 1;
					sideDistance.x = (currPosX + 1.0 - rayPos.x) * dX;
				}
				if (rayDir.y < 0)
				{
					move.y = -1;
					sideDistance.y = (rayPos.y - currPosY) * dY;
				} 
				else
				{
					move.y = 1;
					sideDistance.y = (currPosY + 1.0 - rayPos.y) * dY;
				}

				while (!didHit)
				{
					if (sideDistance.x < sideDistance.y)
					{
						sideDistance.x += dX;
						currPosX += move.x;
						side = 0;
					}
					else
					{
						sideDistance.y += dY;
						currPosY += move.y;
						side = 1;
					}

					if (map[currPosX][currPosY] > 0)
					{
						didHit = true;
					}
				}

				if (side == 0)
				{
					wallDist = (currPosX - rayPos.x + (1 - move.x) / 2) / rayDir.x;
				}
				else
				{
					wallDist = (currPosY - rayPos.y + (1 - move.y) / 2) / rayDir.y;
				}

				int lineHeight = (int) (height / wallDist);
				int drawStart = ((-1) * lineHeight) / 2 + height / 2;
				if (drawStart < 0)
				{
					drawStart = 0;
				}
				int drawEnd = lineHeight / 2 + height / 2;
				if (drawEnd >= height)
				{
					drawEnd = height - 1;
				}

				Color currColor;
				switch (map[currPosX][currPosY])
				{
				case 1:
					currColor = Color.RED;
					break;
				case 2:
					currColor = Color.ORANGE;
					break;
				default:
					currColor = Color.YELLOW;
					break;
				}

				if (side == 1)
				{
					currColor = currColor.darker();
				}
				comp.addLine(true, i, i, drawStart, drawEnd, currColor);
			}
		}
	}

	//Sets up window
	private static void setupFrame()
	{
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("this is so bad i don't even lmao");

		comp = new MainClass();
		comp.setPreferredSize(new Dimension(width, height));
		
		frame.getContentPane().add(comp, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	//Method for prompting input dimensions of map
	@SuppressWarnings("unused")
	private static void promptInputs()
	{
		Object result = JOptionPane.showInputDialog(frame, "Enter map dimensions in the form \"length,height\"");
		System.out.println(result.toString());
		//Check if input is valid
		String inputStr = result.toString().replaceAll(" ", "");
//		while(inputStr.length() != 3)
//		{
//			result = JOptionPane.showInputDialog(frame, "Enter again map dimensions in the form \"length,height\"");
//		}
		
		int length = Integer.parseInt(Character.toString(inputStr.charAt(0)));
		int height = Integer.parseInt(Character.toString(inputStr.charAt(2)));
		setupBasicMap(length, height);
	}
	
	private static void basicSetup()
	{
		setupBasicMap(15,15);
	}
	
	//Uses Java KeyBindings to setup key mappings
	private static void setupKeyMap()
	{
	    InputMap im = comp.getInputMap();
	    ActionMap am = comp.getActionMap();

	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");

	    am.put("D", new MoveAction("D"));
	    am.put("A", new MoveAction("A"));
	    am.put("W", new MoveAction("W"));
	    am.put("S", new MoveAction("S"));
	}
	
	//Handles rotation
	public static void rotation(String dir) throws Exception
	{
		if(dir.equalsIgnoreCase("right"))
		{
			double oldXDir = playerDir.x;
			playerDir.x = playerDir.x*Math.cos(rotSpeed)-playerDir.y*Math.sin(rotSpeed);
			playerDir.y = oldXDir*Math.sin(rotSpeed)+playerDir.y*Math.cos(rotSpeed);
			
			double oldXPlane = cameraDir.x;
			cameraDir.x = cameraDir.x*Math.cos(rotSpeed)-cameraDir.y*Math.sin(rotSpeed);
			cameraDir.y = oldXPlane*Math.sin(rotSpeed)+cameraDir.y*Math.cos(rotSpeed);
		}
		else if(dir.equalsIgnoreCase("left"))
		{
			double oldXDir = playerDir.x;
			playerDir.x = playerDir.x*Math.cos((-1)*rotSpeed)-playerDir.y*Math.sin((-1)*rotSpeed);
			playerDir.y = oldXDir*Math.sin((-1)*rotSpeed)+playerDir.y*Math.cos((-1)*rotSpeed);
			
			double oldXPlane = cameraDir.x;
			cameraDir.x = cameraDir.x*Math.cos((-1)*rotSpeed)-cameraDir.y*Math.sin((-1)*rotSpeed);
			cameraDir.y = oldXPlane*Math.sin((-1)*rotSpeed)+cameraDir.y*Math.cos((-1)*rotSpeed);
		}
		
		clearLines();
	}
	
	public static void move(String dir) throws Exception
	{
		if(dir.equalsIgnoreCase("forwards"))
		{
			if(map[(int)(playerPos.x+playerDir.x)][(int)(playerPos.y)] <= 0)
			{
				playerPos.x += playerDir.x;
			}
			if(map[(int)(playerPos.x)][(int)(playerPos.y+playerDir.y)] <= 0)
			{
				playerPos.y += playerDir.y;
			}
		}
		else if(dir.equalsIgnoreCase("backwards"))
		{
			if(map[(int)(playerPos.x-playerDir.x)][(int)(playerPos.y)] <= 0)
			{
				playerPos.x -= playerDir.x;
			}
			if(map[(int)(playerPos.x)][(int)(playerPos.y-playerDir.y)] <= 0)
			{
				playerPos.y -= playerDir.y;
			}
		}
		
		clearLines();
	}
	
	//Unused method to draw a gray background
		//Significantly decreases rendering time
//		private static void setupBG() throws Exception
//		{
//			Color col = Color.DARK_GRAY;
//			for(int i = 0; i < height/2; i++)
//			{
//				comp.addLine(false, 0, width-1-200, i, i, col);
//			}
//			col = Color.LIGHT_GRAY;
//			for(int i = height/2; i < height; i++)
//			{
//				comp.addLine(false, 0, width-1-200, i, i, col);
//			}
//		}
}
