package Tests;

import static org.junit.Assert.*;

import org.junit.Test;
import structures.Vector;
	
public class VectorTests {
	double err = 0.001; //Error term for asserting doubles
	
	@Test
	public void rotation() {
		Vector testVec1 = new Vector(5,0);
		assertEquals(0, testVec1.getDir(), err);
		assertEquals(5, testVec1.getMag(), err);
		
		testVec1.rotate(90);
		assertEquals(0, testVec1.x, err);
		assertEquals(5, testVec1.y, err);
		
		testVec1.rotate(-90);
		assertEquals(5, testVec1.x, err);
		assertEquals(0, testVec1.y, err);
		
		testVec1.rotate(45);
		assertEquals(Math.sqrt(25.0/2.0), testVec1.x, err);
		assertEquals(Math.sqrt(25.0/2.0), testVec1.y, err);
		assertEquals(Math.toRadians(45), testVec1.getDir(), err);
		assertEquals(5.0, testVec1.getMag(), err);
	}
	
	@Test
	public void idk(){
		Vector vec = new Vector(1,1);
		vec.getUnit().print();
	}

}
