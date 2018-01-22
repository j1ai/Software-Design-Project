package finalproject;
/**
 * Exception class raised where the user specified does not exist
 * @author Cathy
 *
 */
public class UserDNEException extends Exception{

	public UserDNEException(){
		super("User does not exist");
	}
	
	public UserDNEException(String message){
		super(message);
	}
}
