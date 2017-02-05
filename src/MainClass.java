import structures.Vector;
import structures.LineDetails;
import structures.DisplayWindow;
import structures.DoDraw;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import java.io.Console;

@SuppressWarnings("serial")
public class MainClass extends JComponent{
	static Color red;
	
	final static int[][] map = 
		{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,0,0,1,1,1,1,0,0,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,0,0,1,1,1,1,0,0,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1}
		};
	static int height = 640;
	static int width = 480;
	
	public static void main(String[] args) throws Exception {
		
		DoDraw draw = new DoDraw();	 
		draw.setBackground(new Color(0,0,0));
		setupFrame(draw);
		
		Vector playerPos = new Vector(1,1); //start position of player
		Vector playerDir = new Vector(1,1); //direction vector
		Vector cameraDir = new Vector(0,1); //camera plane vector
		
		//GameLoop
		while(true){
			for(int i = 0; i < width; i++){
				red = new Color(255,0,0);
				boolean didHit = false;
				double wallDist;
				double camX = 2*i / (double)width - 1;
				
				Vector rayPos = new Vector(playerPos.x, playerPos.y);
				Vector rayDir = new Vector(playerDir.x + cameraDir.x * camX, playerDir.y + cameraDir.y * camX);
				
				int currPosX = (int)rayPos.x;
				int currPosY = (int)rayPos.y;
				
				double dX = Math.sqrt(1 + (rayDir.y*rayDir.y) / (rayDir.x*rayDir.x));
			    double dY = Math.sqrt(1 + (rayDir.y*rayDir.x) / (rayDir.y*rayDir.y));
			
			    Vector sideDistance = new Vector();
			    Vector move = new Vector();
			    int side = 0;
			    if(rayDir.x < 0){
			    	move.x = -1;
			    	sideDistance.x = (rayPos.x - currPosX)*dX;
			    }
			    else{
			    	move.x = 1;
			    	sideDistance.x = (currPosX + 1.0 - rayPos.x)*dX;
			    }
			    if(rayDir.y < 0){
			    	move.y = -1;
			    	sideDistance.y = (rayPos.y - currPosY)*dY;
			    }
			    else{
			    	move.y = 1;
			    	sideDistance.y = (currPosY + 1.0 - rayPos.y)*dY;
			    }

			    
			    while(!didHit){
			    	if(sideDistance.x < sideDistance.y){
			    		sideDistance.x += dX;
			    		currPosX += move.x;
			    		side = 0;
			    	}
			    	else{
			    		sideDistance.y += dY;
			    		currPosY += move.y;
			    		side = 1;
			    	}
			    	
			    	if(map[currPosX][currPosY] > 0){
			    		didHit = true;
			    	}
			    }
			    
			    if(side == 0){
			    	wallDist = (currPosX - rayPos.x + (1 - move.x)/2)/rayDir.x;
			    }
			    else{
			    	wallDist = (currPosY - rayPos.y + (1 - move.y)/2)/rayDir.y;
			    }
			    
			    
			    int lineHeight = (int)(height/wallDist);
			    int drawStart = ((-1)*lineHeight)/2 +height/2;
			    if(drawStart < 0){
			    	drawStart = 0;
			    }
			    int drawEnd = lineHeight/2 + height/2;
			    if(drawEnd > height){
			    	drawEnd = height-1;
			    }
			    
			    if(side == 1){
			    	red = red.darker();
			    }
			    draw.receiveLine(new LineDetails(true, i, i, drawStart, drawEnd, red));
			} 
			draw.repaint();
		}
	}
	
	static void setupFrame(JPanel panel){
		DisplayWindow display = new DisplayWindow("Raycaster", 640, 480);
		display.add(panel);
		display.showFrame();
	}
}
