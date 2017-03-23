package structures;

public class Vector
{
	public double x;
	public double y;
	
	public Vector()
	{
		this.x = 0;
		this.y = 0;
	}
	
	//Vector constructor with values
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Rotates counterclockwise by deg degrees
	public void rotate(double deg)
	{
		//Converts degrees to radians
		double degAsRadians = Math.toRadians(deg);
		
		double newX;
		double newY;
		
		/*	Multiplies the current vector by the 2D rotation matrix:
		 * 			[cos(a) -sin(a)]
		 * 			[sin(a)  cos(a)]
		 * 		for any R^2 space and real a
		 */
		newX = Math.cos(degAsRadians)*x + (-1)*Math.sin(degAsRadians)*y;
		newY = Math.sin(degAsRadians)*x + Math.cos(degAsRadians)*y;
		
		this.x = newX;
		this.y = newY;
	}
	
	//Employs the magnitude formula: sqrt(x^2 + y^2);
	public double getMag()
	{
		return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
	}
	
	//Finds the angle between the components with the formula: Angle = Arctan(y/x)
	public double getDir()
	{
		return Math.atan(y/x);
	}
	
	//Performs vector multiplication with another vector v
	public double mult(Vector v)
	{
		return this.x * v.x + this.y * v.y;
	}
	
	public boolean isPerp(Vector v)
	{
		if(this.mult(v) == 0){
			return true;
		}
		else
		{
			//DO NOT DELETE LINE BELOW, NEED FOR SOME REASON WHY JAVA WHY
			v.print();
			//^DO NOT TOUCH CURSED CODE WTFFF THERE NEEDS TO BE A UNIT TIME OPERATION THERE FOR SOME REASON
			return false;
		}
	}
	
	public Vector getUnit()
	{
		return new Vector(this.x/this.getMag(), this.y/this.getMag());
	}
	
	public void print()
	{
		System.out.println("x: "+this.x+", y: "+this.y);
	}
}
