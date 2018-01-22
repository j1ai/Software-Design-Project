package finalproject;

import java.util.ArrayList;

/**
 * 
 * @author JLAI
 * This class works as a Category class that stores all the products within that Category object
 * Each Category has its own code, description, and product list as well.
 */
public class Category implements Comparable<Category>{
	protected int code;
	protected String description;
	protected ArrayList<Product> product_list;
	protected static int code_count = 1;
	protected Inventory inventory;
	
	public Category(String description, Inventory inv){
		this.code = code_count;
		this.description = description;
		product_list = new ArrayList<Product>();
		code_count += 1;
		this.inventory = inv;
	}
	
	public Category(String description, int code, Inventory inv) {
		this.code = code;
		this.description = description;
		product_list = new ArrayList<Product>();
		code_count += 1;
		this.inventory = inv;
	}
	
	/**
	 * Add that Product object to the product list of this Category
	 * @param obj
	 */
	public void addProduct(Product obj){
		product_list.add(obj);
	}
	
	/**
	 * Return the product list that stores all product within this Category
	 * @return
	 */
	public ArrayList<Product> findProduct(){
		return this.product_list;
	}
	
	/**
	 * CompareTo method for Category object that based on their description
	 */
	@Override
	public int compareTo(Category o) {
		int result = this.description.compareTo(((Category)o).description);
		return result;
	}

	@Override
	public String toString(){
		return code + "-" +description;
		
	}
}
