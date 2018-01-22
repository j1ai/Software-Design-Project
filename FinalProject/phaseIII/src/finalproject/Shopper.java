package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author JLAI
 *This class works as an authenticated Shopper. Each Shopper has its own shopping cart, list of Invoice,
 *and its own address.
 *The shopping cart is a HashMap, which its key is the Product object and the value is the quantity that this
 *Shopper orders.
 *The list of Invoice is an ArrayList of Invoice that stores all the previous purchase for this Shopper as a Invoice 
 *class objects.
 *Each Shopper also has its own userId and password in order to login to the program.
 */
public class Shopper extends User{
	protected Map<Product, Integer> cart = new HashMap<Product, Integer>();
	protected ArrayList<Invoice> invoice = new ArrayList<Invoice>();
	protected Address address;
	
	public Shopper(String userID, String password) {
		super(userID, password);	
		this.type = "Shopper";
	}
	
	public int addAddress(String street, String city, String name) {
		this.address = new Address(street, city, name);
		return address.ID;
	}
	/**
	 * Adding the required Product with the order quantity to this Shopper's own shopping cart
	 * @param product -> Product class Object which the Shopper wish to purchase
	 * @param backorder -> Amount of the Product that this Shopper wish to purchase
	 * @return an ArrayList of String that stores all Distribution Centers that are able to provide that
	 * amount of quantity needed by this Shopper, otherwise return an empty ArrayList
	 */
	public boolean addtoCart(Product product,int backorder){
		if (product.quantity-product.temp >= backorder){
			if (cart.containsKey(product))
				cart.put(product, cart.get(product)+backorder);
			else
				cart.put(product, backorder);
			product.temp += backorder;
			return true;
		}
		return false;
	}
	
	public void removeFromCart(Product product){
		int q = cart.get(product);
		cart.remove(product);
		product.temp -= q;
	}
	/**
	 * Methods for this Shopper to filter the wanted merchandise based on Category and price-range
	 * @param inventory -> the Inventory that store all the products, which the method(filterMerchadise)
	 * inside this Inventory object is used as filtering
	 * @param category -> the Category of merchandise that the Shopper is looking for
	 * @param max_price -> Maximum price range of merchandises that the Shopper is looking for
	 * @param min_price -> Minimum price range of merchandises that the Shopper is looking for
	 * @return -> Return an ArrayList of products that fits within this criteria
	 */
	public ArrayList<Product> filterMerchandise(Inventory inventory,Category category,int max_price,int min_price){
		return inventory.filterMerchandise(category, max_price, min_price);
	}
	
	/**
	 * Allows the Shoppers to view the purchase in its own shopping cart
	 * @return the HashMap of shopping cart of this Shopper
	 */
	public Map<Product, Integer> viewPurchase() {
		Map<Product, Integer> result = new HashMap<Product, Integer>();
		for(Invoice i :invoice){
			for(Map.Entry<Product, Integer> s: i.items.entrySet()){
				if (result.containsKey(s.getKey()))
					result.put(s.getKey(), result.get(s.getKey())+s.getValue());
				else
					result.put(s.getKey(), s.getValue());
			}
		}
		return result;
	}
	
	public Map<Product, Integer> getShoppingCart(){
		return cart;
	}
	
	/**
	 * Add the shopping cart of this Shopper as an Invoice object to the list of Invoice of this Shopper
	 */
	public void addInvoice(double total){
		Invoice i = new Invoice(cart,total);
		this.invoice.add(i);
	}
	
	/**
	 * View the history of Invoice/Purchases by this Shopper
	 * @return An ArrayList of Invoice objects that stores the Products and amounts this Shopper purchased
	 */
	public ArrayList<Invoice> viewInvoice() {
		return invoice;
	}
	
	/**
	 * Checking out method for the products that this Shopper has placed inside its shopping cart
	 */
/*	public void checkOut(){
		addInvoice();
		cart.clear();
	}*/

		/**
	 * Prints the string of what is in the shopping cart
	 * @return result contains the products and their quantity from the shopping cart 
	 */
	public String cToString() {
		String result = "Cart";
		for (Map.Entry<Product, Integer> c : cart.entrySet()){
		    int dis = c.getKey().ID;
		    String temp = "," + dis +":"+ c.getValue();
		    result += temp;
		}
		result +="\n";
		return result;
	}
	
	/**
	 * Prints the string of what is in the invoice
	 * @return 
	 */
	public String iToString() {
		String result = "";
		for (Invoice i: invoice){
			result += "Invoice,";
			result += i.ID + ",";
			result += i.total_amount;
			for(Map.Entry<Product,Integer> ca: i.items.entrySet()){
				Product p = ca.getKey();
				int quantity = ca.getValue();
				result += "," + p.ID + ":" + quantity;
			}
			result += "\n";
			//result += "Total paid: " + i.total_amount + "\n";
		}
		return result;
	}
}