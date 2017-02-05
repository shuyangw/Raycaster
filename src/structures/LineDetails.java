package structures;

import Exceptions.IllegalConstructorException;
import java.awt.Color;

public class LineDetails{
	public boolean isVert;
	public int x1;
	public int x2;
	public int y1;
	public int y2;
	public Color color;
	
	public LineDetails(){
		this.x1 = 0;
		this.x2 = 0;
		this.y1 = 0;
		this.y2 = 0;
		this.color = new Color(0,0,0);
	}
	
	public LineDetails(boolean isVert, int x1, int x2, int y1, int y2, Color color) throws Exception{
		if(isVert && x1 != x2){
			throw new IllegalConstructorException("Boolean in constructor contradicts input x1 and x2 coordinates");
		}
		
		if(isVert || x1 == x2){
			this.x1 = x1;
			this.x2 = x1;
		}
		else{
			this.x1 = x1;
			this.x2 = x2;
		}
		
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
	}
	
	public void print(){
		System.out.println();
	}
}