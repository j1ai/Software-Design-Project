package finalproject;
/**
 * Exception class used when a product already exists.
 * @author Cathy
 *
 */
public class ExistingProdException extends Exception {
	
	public ExistingProdException(){
		super("Product already exists");
	}
	
	public ExistingProdException(String message){
		super(message);
	}
}
