package exercise2;

/**
 * Exception raised when the graph is full
 * @author c6luoyil
 *
 */

public class GraphIsFullException extends Exception{	
	
// Your code goes here
	public GraphIsFullException() {
		super("The GraphIsFullException occurred!");
	}
	public GraphIsFullException(String message) {
		super(message);
	}
}
