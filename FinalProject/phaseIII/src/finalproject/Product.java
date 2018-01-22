package finalproject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
/**
 * Individual product class that belongs into categories and in the inventory
 * @author Cathy
 *
 */
public class Product implements Comparable<Product> {

	protected int ID;
	protected String description;
	protected Category category;
	protected double price;
	protected int quantity = 0;
	protected int temp;
	protected String image = null;
	protected Map<String, Integer> distCenters;
	protected static int ID_count = 1;
	public int sold;

	/**
	 * Constructor for Product class , includes the description, category and price.
	 * In addition,the Distribution Center is set up 
	 * @param description -> the description of the product
	 * @param category -> the category of the product
	 * @param price -> the price of the product
	 */
	public Product(String description, Category category, double price) {
		this.ID = ID_count;
		ID_count += 1;
		this.description = description;
		this.category = category;
		this.price = price;
		this.distCenters =new HashMap<String, Integer>();
		for (int i = 0; i < category.inventory.distCenter_list.size(); i++){
			String d = category.inventory.distCenter_list.get(i);
			distCenters.put(d, 0);
		}
	}
	/**
	 * Another constructor for Product class, including the description, category, price, ID and quantity;
	 * @param description -> the description of the product
	 * @param category -> the category of the product
	 * @param price -> the price of the product
	 * @param ID -> the ID of the product
	 * @param quantity -> the quantity of the product
	 */
	public Product(String description, Category category, double price, int ID, int quantity, int sold) {
		this.ID = ID;
		ID_count += 1;
		this.description = description;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.sold = sold;
		this.distCenters =new HashMap<String, Integer>();
	}

	/**
	 * toString method where the description, ID, price, quantity, image, and the respective distribution
	 * center information is given.
	 */
	@Override
	public String toString() {
		String result = description + "-" + ID + "-"+ price+ "-" + quantity+ "-" + image + "-"+ sold;
		for (Map.Entry<String, Integer> distCenters : distCenters.entrySet())
		{
		    String dis = distCenters.getKey();
		    String temp = dis +","+ distCenters.getValue();
		    result +=  "-"+ temp;
		}
		return result;
	}

	/**
	 * compareTo method comparing the current quantity with the product given
	 */
	@Override
	public int compareTo(Product o) {
		return this.quantity - ((Product) o).quantity;
	}
	
	/**
	 * Given the quantity of the customer requires, if the distribution center has 
	 * availability larger than the backorder, the distribution center will be added to result
	 * @param backorder -> the amount ordered by the customer
	 * @return -> an ArrayList of strings
	 */
	/*	public ArrayList<String>checkAvailableQuantity(int backorder){
		ArrayList<String> result = new ArrayList<String>();
		for (Map.Entry<String, Integer> distCenters : distCenters.entrySet()){
		    int available = distCenters.getValue();
		    if (backorder <= available){
		    	result.add(distCenters.getKey());
		    }
		}
		return result;
	}*/

	/**
	 * Deducts the quantity by the number of backorders
	 * @param backorder
	 */
	public void deductQuantity(int backorder) {
		quantity -= backorder;	
	}
	
	/**
	 * Helper function for readShopper, helps to find the Product given the product ID.
	 * 
	 * @param ID
	 *            product ID
	 * @param inv
	 *            inventory
	 * @return the Product
	 */
	public static Product findProduct(int ID, Inventory inv) {
		//given all products find the product that has the same ID as the given ID
		ArrayList<Product> product_list = inv.allProduct();
		Product result = null;
		for (int i = 0; i < product_list.size(); i++) {
			Product p = product_list.get(i);
			if (p.ID == ID)
				result = p;
		}
		return result;
	}
}