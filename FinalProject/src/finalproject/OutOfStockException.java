package finalproject;
/**
 * Exception class raised when item is out of stock.
 * @author Cathy
 *
 */
public class OutOfStockException extends Exception {
	public OutOfStockException(){
		super("Item out of stock");
	}
	
	public OutOfStockException(String message){
		super(message);
	}
}
