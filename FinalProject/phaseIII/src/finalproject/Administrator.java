package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Administrator class extends user where it performs different task required
 * @author Cathy
 *
 */

public class Administrator extends User{
	/**
	 * The constructor where UserID and password is instantiated
	 * @param userID -> the userID
	 * @param password -> the user password
	 */
	public Administrator(String userID, String password){
		super(userID, password);
		this.type = "Admin";
	}
	
	/**
	 * The administrator is able to add a new category to the Inventory list with a string description
	 * @param inventory -> the inventory list
	 * @param description -> the description of the Category
	 * @return The category code
	 * @throws ExistingCatException -> exception raised with the category already exist
	 */
	public int AddCategory(Inventory inventory, String description) throws ExistingCatException{
		if (description != null && description.length() != 0){
			for (Category c: inventory.category_list)
				if (c.description.equals(description)){
	//				System.out.println("existed");
					throw new ExistingCatException();
				}
			Category c = new Category(description,inventory);
			inventory.category_list.add(c);
			return c.code;
		}
		return -1;
	}
	
	/**
	 * Adding new distribution centers given the Inventory and city
	 * @param inventory -> the Inventory list
	 * @param city -> the city where distribution center is located
	 */
	public void addDistributionCenter(Inventory inventory, String city){
		if (!inventory.distCenter_list.contains(city)){
			inventory.distCenter_list.add(city);
			ArrayList<Product> products = inventory.allProduct();
			for (int i= 0; i < products.size(); i++){
				Product p = products.get(i);
				p.distCenters.put(city, 0);
			}
		}
	}
	/**
	 * Adds a new product given the inventory list, product name, category and price
	 * @param inventory -> the inventory list
	 * @param prodName -> the product name
	 * @param category -> the category of the product
	 * @param price -> the price of the product
	 * @return the product ID
	 * @throws ExistingProdException -> Exception raised when the product already exists
	 */
	public int addProduct(Inventory inventory, String prodName, Category category, double price)throws ExistingProdException{
		for (Product p: inventory.allProduct())
			if (p.description.equals(prodName))
				throw new ExistingProdException();
		Product p = new Product(prodName, category, price);
		category.product_list.add(p);
		return p.ID;
	}
	
	/**
	 * Updates the quantity of the product
	 * @param p -> the product
	 * @param distCenter -> the distribution center 
	 * @param quantity -> the quantity needs to be updated
	 */
	public void updateQuantity(Product p, String distCenter, int quantity){
		p.distCenters.put(distCenter, p.distCenters.get(distCenter) + quantity);
		p.quantity += quantity;
	}
	
	/**
	 * Changes the description of the product
	 * @param p -> the product
	 * @param description -> the description of the product
	 */
	public void changeDescription(Product p, String description){
		p.description = description;
	}
	
	/**
	 * Changes the price of the product
	 * @param p -> the product
	 * @param price -> the price
	 */
	public void changePrice(Product p, int price){
		p.price = price;
	}
	
	public HashMap<Product, Integer> salesReport(Inventory inv){
		HashMap<Product, Integer> result = new HashMap<Product, Integer>();
		ArrayList<Product> proList = inv.allProduct();
		for (Product p: proList){
			result.put(p, p.sold);
		}
		return result;
	}
}