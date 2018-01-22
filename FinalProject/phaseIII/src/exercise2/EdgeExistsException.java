package exercise2;
/**
 * Exception used when and edge already exists
 * @author c6luoyil
 *
 */

public class EdgeExistsException extends Exception{	
	
// Your code goes here
	public EdgeExistsException() {
		super("The GraphIsFullException occurred!");
	}
	public EdgeExistsException(String message) {
		super(message);
	}
}
