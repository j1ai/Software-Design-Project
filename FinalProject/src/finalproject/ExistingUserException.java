package finalproject;
/**
 * Exception class raised where a user already exists
 * @author Cathy
 *
 */
public class ExistingUserException extends Exception {

	public ExistingUserException(){
		super("User already exists");
	}
	
	public ExistingUserException(String message){
		super(message);
	}
}