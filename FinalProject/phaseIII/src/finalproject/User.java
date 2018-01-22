package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * The user class is a general class where all users including the Administrator and Shopper will inherit from
 * @author Cathy
 *
 */
public class User {
	protected String userID;
	protected String password;
	protected String type;
//	protected Inventory inventory;
	protected static HashMap<Integer, User> allUser = new HashMap <Integer, User>();
	protected static int counterID = 1;
	protected int sessionID = -1;
	
	/**
	 * The constructor including the userID , password 
	 * Also this is where the sessionID is made
	 * @param userID -> the userID
	 * @param password -> the user's password
	 */
	public User(String userID, String password) {
		this.userID = userID;
		this.password = password;
		this.sessionID = counterID;
		counterID ++;
		allUser.put(sessionID, this);
	}
	
//	public void login(Inventory inventory)
	
	/**
	 * When a user logs out, their sessionID gets removed
	 */
	public void logout(){
		allUser.remove(this.sessionID);
		this.sessionID = -1;
	}
	
	/**
	 * A user can browseMerchanse from the inventory
	 * @param inventory -> the inventory list
	 * @return Arraylist of the products
	 */
	public static ArrayList<Product> browseMerchandise(Inventory inventory,String cat,int max_price,int min_price){
		//ArrayList<Product> result = new ArrayList<Product>();
		//ArrayList<Product> products = inventory.allProduct();
		//for (int i= 0; i <= products.size(); i++){
		//	Product p = products.get(i);
		//	if (p.quantity > 0)
		//		result.add(p);
		//}
		//return result;
		Category category = null;
			for (Category c: inventory.category_list){
				if (c.description.equals(cat)){
					category = c;
				}
			}
		ArrayList<Product> result = inventory.filterMerchandise(category, max_price, min_price);
		return result;
	}
	
}
