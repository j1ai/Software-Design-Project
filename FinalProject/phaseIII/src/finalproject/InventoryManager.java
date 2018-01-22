/**
 * 
 */
package finalproject;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author c5widjaj
 * This Inventory Manager class reads and writes to the inventory file where all inventories are kept.
 */
public class InventoryManager {
	
	protected static FileWriter writer;
	//path where inventory file is located
	protected final static String FILEPATH = "inventory.csv";
	
	public InventoryManager(){
	}
	
	/**
	 * Reads the inventory from files by categories and their respective products
	 * @return Inventory object
	 * @throws IOException 
	 */
	public Inventory readFromFile() throws IOException {
		new FileOutputStream(FILEPATH, true).close();
		Scanner scanner = new Scanner(new FileInputStream(FILEPATH));
		Inventory inv = new Inventory();
		String[] record;
		Category cur_cat;
		if(scanner.hasNextLine()){
			record = scanner.nextLine().split("-");
			//for just one line
			if (record.length == 2){
				Category cat = new Category(record[1], Integer.parseInt(record[0]),inv);
				cur_cat = cat;
				inv.category_list.add(cat);
				//for multiple lines 
				while(scanner.hasNextLine()) {
					record = scanner.nextLine().split("-");
					//finds the category and adds it to the category list
					if (record.length == 2) {
						Category cat1 = new Category(record[1], Integer.parseInt(record[0]),inv);
						cur_cat = cat1;
						inv.category_list.add(cat1);
					}
					else if (record.length == 0) ;
					else {
						//finds product, object gets created and is added to distribution center
						Product p = new Product(record[0], cur_cat, Double.parseDouble(record[2]), Integer.parseInt(record[1]),Integer.parseInt(record[3]), Integer.parseInt(record[5]));
						p.image = record[4];
						for (int i=6;i<record.length;i++){
							String [] dist_centre = record[i].split(",");
							p.distCenters.put(dist_centre[0], Integer.parseInt(dist_centre[1]));
							if(!inv.distCenter_list.contains(dist_centre[0])){
								inv.distCenter_list.add(dist_centre[0]);
							}
						}
						cur_cat.addProduct(p);
					}
				}
			}
		}
		scanner.close();
		return inv;
	}

	/**
	 * Writes all the inventory to file including the Category and their respective products
	 * @param inventory -> the inventory list
	 * @throws IOException
	 */
	public void writeFile(Inventory inventory) throws IOException {
		ArrayList<Category> category_list = inventory.category_list;
		 try {
			clearCsv();
			writer = new FileWriter(FILEPATH, true);
			
			for (Category c: category_list){
				//Writes all category titles
				String result = c.toString() + "\n";
				writer.append(result);
				// along with the products under the categories
				for (Product p: c.product_list){
					result = p.toString() + "\n";
					writer.append(result);
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper function used to clear the inventory file when inventory changes
	 * @throws IOException
	 */
	public static void clearCsv() throws IOException{
		writer = new FileWriter(FILEPATH, false);
		writer.flush();
		writer.close();
	}
	
	
}
	
	