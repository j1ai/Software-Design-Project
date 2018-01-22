package finalproject;

import java.util.ArrayList;
import java.util.Collections;
/**
 * 
 * @author JLAI
 * This class works as an Inventory for the program, which store all the Categories in a list and all existing 
 * Distribution Center in list as well.
 * This class also allows user to filter the merchandise based on Category and price-range, which can be used
 * as a method for the GUI, when the user want to browse the merchandise.
 * In addition, there are two sort methods in this class that return the list of sorted Product by Category or its
 * own quantity.
 * 
 */
public class Inventory {
	protected ArrayList<Category> category_list;
	protected ArrayList<String> distCenter_list;
	
	public Inventory(){
		category_list = new ArrayList <Category>();
		distCenter_list = new ArrayList <String>();
	}
	
	/**
	 * Filter the all Products based on their Categories and price-range
	 * @param category -> Category of the product
	 * @param max_price -> Maximum price range for the products
	 * @param min_price -> Minimum price range for the products
	 * @return -> an ArrayList of Products that fits the criteria
	 */
	public ArrayList<Product> filterMerchandise (Category category, int max_price,int min_price){
		ArrayList<Product> product_list = new ArrayList<Product>();
		ArrayList<Product> products;
		if (category == null)
			products = allProduct();
		else
			products = category.findProduct();
		for (Product p: products){
			if (p.price<= max_price && p.price >= min_price){
				product_list.add(p);
			}
		}
		return product_list;
	}
	
	/**
	 * Sorting all Products based on their available quantity
	 * @return -> an sorted ArrayList of Products based on their available quantity
	 */
	public ArrayList<Product> sortByQuantity(){
		ArrayList<Product> product_list = allProduct();
		Collections.sort(product_list);
		return (ArrayList<Product>) product_list;
	}
	
	/**
	 * Sorting all Products based on their own categories
	 * @return -> an sorted ArrayList of Products based on their own Categories
	 */
	public ArrayList<Product> sortByCategory(){
		ArrayList<Category> temp_list = this.category_list;
		Collections.sort(temp_list);
		ArrayList<Product> product_list = new ArrayList<Product>();
		for (Category c: temp_list){
			for (Product p: c.product_list){
				product_list.add(p);
			}
		}
		return product_list;
	}
	
	/**
	 * Returning all products in the Inventory
	 * @return -> An ArrayList of Products in the Inventory
	 */
	public ArrayList<Product> allProduct(){
		ArrayList<Product> product_list = new ArrayList<Product>();
		for (Category c: category_list){
			for (Product p: c.product_list){
				product_list.add(p);
			}
		}
		return product_list;
	}
	
	public int findID(String cat) {
		int r = -1;
		for (Category c: category_list) {
			if (c.description.equals(cat)) 
				r = c.code; System.out.println("pass");
		}
		return r;
	}
	
	public Product findProduct(int ID) {
		Product r = null;
		for (Product p: allProduct()) {
			if (p.ID == ID) r = p;
		}
		return r;
	}
	public int findProductID(String s) {
		int r = 0;
		for (Product p: allProduct()) {
			if (p.description.equals(s)) r = p.ID;
		}
		return r;
	}
}
