package Exceptions;

/*
 * Exception for the Line class
 */

@SuppressWarnings("serial")
public class IllegalConstructorException extends Exception{
	public IllegalConstructorException(String s){
		super(s);
	}
}
