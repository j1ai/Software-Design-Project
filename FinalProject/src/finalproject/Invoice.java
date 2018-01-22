package finalproject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maxim
 *
 * This class stores the information of what the Shopper buys. 
 * It contains the amount of the invoice, invoice ID, total amount spent, 
 * and a shopping cart of products and its respective quantities.
 */

public class Invoice {
	Map<Product, Integer> items = new HashMap<Product, Integer>();	
	protected int ID;
	protected double total_amount;
	protected static int serialNum = 1;
	protected static double total_spend = 0;
	
	public Invoice(Map<Product, Integer> cart, double total_price) {
		// want to assign a unique ID
		this.items = cart;
		ID = serialNum;
		serialNum++;
		this.total_amount = total_price;
		total_spend += total_amount;
	}
	
	public Invoice(Map<Product, Integer> cart, double total_price, int ID) {
		// TODO Auto-generated constructor stub
		this.items = cart;
		this.ID = ID;
		serialNum++;
		this.total_amount = total_price;
		total_spend += total_amount;
	}

	@Override
    public String toString() {
		return this.items + "\n"+"Total Paid: "+this.total_amount;
	}	
}