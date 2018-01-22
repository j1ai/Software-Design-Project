package exercise2;

// Please complete the code for the following method.
// A correctly working class gets up to 2 marks

public class EdgeExistsException extends Exception{	
	
// Your code goes here
	public EdgeExistsException() {
		super("The GraphIsFullException occurred!");
	}
	public EdgeExistsException(String message) {
		super(message);
	}
}
