package finalproject;
/**
 * Exception class raised when the password entered is incorrect
 * @author Cathy
 *
 */
public class InvalidPassException extends Exception {
	public InvalidPassException(){
		super("The password you entered is incorrect");
	}
	
	public InvalidPassException(String message){
		super(message);
	}
}
