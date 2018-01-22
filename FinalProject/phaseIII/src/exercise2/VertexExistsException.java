package exercise2;

/**
 * Exception class used if vertex already exists
 * @author c6luoyil
 *
 */

public class VertexExistsException extends Exception{
	
// Your code goes here
	public VertexExistsException() {
		super("The VextexExistsException occured!");
	}	
	public VertexExistsException(String message) {
		super(message);
	}
}