package finalproject;
/**
 * This is an exception class used when a category already exists.
 * @author Cathy
 *
 */
public class ExistingCatException extends Exception {
	
	public ExistingCatException(){
		super("Category already exists!");
	}
	
	public ExistingCatException(String message){
		super(message);
	}
}
