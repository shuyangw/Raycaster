package structures;

import java.awt.Color;

import Exceptions.IllegalConstructorException;

public class Line {
	public boolean isVert;
	public int x1;
	public int x2;
	public int y1;
	public int y2;
	public Color color;
	
	public Line(boolean isVert, int x1, int x2, int y1, int y2, Color color) throws Exception{
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
		System.out.println("x1: "+this.x1+", y1: "+this.y1+" , x2:"+this.x2+" , y2: "+this.y2);
	}
}
