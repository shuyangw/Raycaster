import structures.Line;
import structures.Vector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainClass extends JComponent
{
	// Setup fields
	static MainClass comp;
	static LinkedList<Line> lines = new LinkedList<Line>();
	static Vector playerPos;
	static Vector playerDir;
	static Vector cameraDir;
	static int width = 800;
	static int height = 600;
	static double rotSpeed = 3.0; // ??? TODO

	final static int[][] map = 
		{   { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	public void addLine(boolean isVert, int x1, int x2, int x3, int x4, Color col) throws Exception
	{
		lines.add(new Line(isVert, x1, x2, x3, x4, col));
		repaint();
	}

	public void clearLines()
	{
		lines.clear();
		repaint();
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

	public static void main(String[] args) throws Exception
	{
		setupFrame();
		playerPos = new Vector(1, 1); // start position of player
		playerDir = new Vector(1.0 / Math.sqrt(2), 1.0 / Math.sqrt(2)); // direction
																		// vector
		cameraDir = new Vector(0, 0.66); // camera plane vector

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
					currColor = Color.YELLOW;
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

	static void setupFrame()
	{
		JFrame frame = new JFrame();
//		JPanel panel = new JPanel();
		frame.getContentPane().setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		comp = new MainClass();
		comp.setPreferredSize(new Dimension(width, height));
		frame.getContentPane().add(comp, BorderLayout.CENTER);
//		frame.getContentPane().add(panel);
//		panel.addKeyListener(new KeyListener()
//		{
//			@Override
//			public void keyPressed(KeyEvent e) 
//			{
//				for(int i = 0; i < 10; i++) System.out.println("THING IS PRINTED");
//				switch(e.getKeyCode())
//				{
//					case KeyEvent.VK_W:
//						if(map[(int)(playerPos.x+playerDir.x)][(int)(playerPos.y)] <= 0)
//						{
//							playerPos.x += playerDir.x;
//						}
//						if(map[(int)(playerPos.x)][(int)(playerPos.y+playerDir.y)] <= 0)
//						{
//							playerPos.y += playerDir.y;
//						}
//						break;
//					case KeyEvent.VK_S:	
//						if(map[(int)(playerPos.x-playerDir.x)][(int)(playerPos.y)] <= 0)
//						{
//							playerPos.x -= playerDir.x;
//						}
//						if(map[(int)(playerPos.x)][(int)(playerPos.y-playerDir.y)] <= 0)
//						{
//							playerPos.y -= playerDir.y;
//						}
//						break;
//					case KeyEvent.VK_D:	
//						double oldXDir = playerDir.x;
//						playerDir.x = playerDir.x*Math.cos((-1)*rotSpeed)-playerDir.y*Math.sin((-1)*rotSpeed);
//						playerDir.y = oldXDir*Math.sin((-1)*rotSpeed)+playerDir.y*Math.cos((-1)*rotSpeed);
//						
//						double oldXPlane = cameraDir.x;
//						cameraDir.x = cameraDir.x*Math.cos(rotSpeed)-cameraDir.y*Math.sin(rotSpeed);
//						cameraDir.y = oldXPlane*Math.sin(rotSpeed)+cameraDir.y*Math.cos(rotSpeed);
//					case KeyEvent.VK_A:	
//					default: break;
//				}
//			}
//			
//			@Override
//			public void keyReleased(KeyEvent arg0)
//			{
//				// UNUSED
//			}
//
//			@Override
//			public void keyTyped(KeyEvent arg0)
//			{
//				// UNUSED
//			}
//
//		});
		
		frame.pack();
		frame.setVisible(true);
	}
}
