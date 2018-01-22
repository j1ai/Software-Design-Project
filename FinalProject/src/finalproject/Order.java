package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class is used to aid the function placeOrder in project.java where the product ID is needed
 * @author Cathy
 *
 */
public class Order {
	protected static int ID_count = 1;
	protected static HashMap<Integer, Order> orderList = new HashMap<Integer, Order>();
	protected int ID;
	protected String city;
	protected ArrayList<String> distCenters;
	protected Product product;
	protected int quantity;
	protected ArrayList<String> route;
	
	public Order(String city, ArrayList<String> distCenters, Product product, int quantity){
		this.city = city;
		this.distCenters = distCenters;
		this.product = product;
		this.quantity = quantity;
		this.ID = ID_count;
		ID_count++;
		orderList.put(ID, this);
	}
}
