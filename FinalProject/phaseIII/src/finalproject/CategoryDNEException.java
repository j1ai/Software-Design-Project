package finalproject;
/**
 * This is an exception class used when a category does not exist when searched.
 * @author Cathy
 *
 */
public class CategoryDNEException extends Exception {
	
	public CategoryDNEException(){
		super("Category does not exist");
	}
	
	public CategoryDNEException(String message){
		super(message);
	}
}
