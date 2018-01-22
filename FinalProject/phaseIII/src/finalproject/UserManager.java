package finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * UserManager used for reading and writing all users and also creating
 * individual files for existing users.
 * 
 * @author Cathy
 *
 */
public class UserManager {

	/** A mapping of user ID and password. */
	public static Map<String, String> users;

	/** PATH for user file where userID and password is stored. */
	public final static String PATH = "users.csv";

	public UserManager() {
		users = new HashMap<String, String>();
	}

	/**
	 * Populates the users list(shopper and administration) from the file at
	 * PATH filePATH.
	 * @throws IOException 
	 */
	public void readUserFile() throws IOException {
		// FileInputStream can be used for reading raw bytes
		// (like reading an image
		new FileOutputStream(PATH, true).close();
		Scanner scanner = new Scanner(new FileInputStream(PATH));
		String[] newUser;
		while (scanner.hasNextLine()) {
			newUser = scanner.nextLine().split(",");
			// UserID,Password and their usertype either administrator or
			// shopper
			if (newUser.length == 3)
				if(!newUser[0].equals(""))
					users.put(newUser[0], newUser[1] + "," + newUser[2]);
		}
		scanner.close();
	}

	/**
	 * Writes the information of a user to file and saves it in a CSV.
	 * 
	 * @param userID
	 *            userID of the user
	 * @param password
	 *            password of the user
	 * @param userType
	 *            Administrator or Shopper
	 * @throws FileNotFoundException
	 *             if filePATH is invalid
	 */
	public void writeUser(String userID, String password, String userType) throws FileNotFoundException {

		try {
			FileWriter abc = new FileWriter(PATH, true);
			BufferedWriter bufferedWriter = new BufferedWriter(abc);
			bufferedWriter.append(userID + "," + password + "," + userType + "\n");
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to PATH");
		}
	}

	/**
	 * Reads the shopper information including their address, things in their
	 * cart and their list of invoices.
	 * 
	 * @param s
	 *            the Shopper
	 * @param inventory
	 *            the inventory
	 * @throws FileNotFoundException
	 *             if filePATH is invalid
	 * @throws NumberFormatException
	 *             if given string does not have he appropriate format
	 * @throws OutOfStockException
	 *             if item is out of stock
	 */
	public void readShopper(Shopper s, Inventory inventory) throws FileNotFoundException, OutOfStockException, NumberFormatException {

		// cart,invoice, address
		Scanner scanner = new Scanner(new FileInputStream(s.userID + ".csv"));

		String[] record;
		boolean stock = true;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			//puts address in the shopper object
			if (record[0].equals("Address")) {
				s.address = new Address(record[1], record[2], record[3], Integer.parseInt(record[4]));
			//finding the cart items
			} else if (record[0].equals("Cart")) {
				for (int i = 1; i < record.length; i++) {
					//splitting by the product ID and the quantity ordered
					String[] temp = record[i].split(":");
					//find the Product by the ID and putting it into the cart
					Product a = findProduct(Integer.parseInt(temp[0]), inventory);
					boolean result = s.addtoCart(a, Integer.parseInt(temp[1]));
					if (stock && !result)
						stock = result;
				}
			//find invoice items
			} else if (record[0].equals("Invoice")) {
				Map<Product, Integer> newInv = new HashMap<Product, Integer>();
				//record [1] and [2] is total price and ID
				//starting from [3], are the old invoice items 
				for (int i = 3; i < record.length; i++) {
					String[] temp = record[i].split(":");
					Product a = findProduct(Integer.parseInt(temp[0]), inventory);
					newInv.put(a, Integer.parseInt(temp[1]));
				}
				//adds the information read from the map into the invoice
				Invoice in = new Invoice(newInv, Double.parseDouble(record[2]), Integer.parseInt(record[1]));
				s.invoice.add(in);
			}
		}
		scanner.close();
		if (!stock)
			throw new OutOfStockException();
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
	public Product findProduct(int ID, Inventory inv) {
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

	/**
	 * Writes and updates any information about the shopper including their address,current cart items 
	 * and past invoices
	 * @param shopper
	 * @throws FileNotFoundException
	 */
	public void writeShopper(Shopper shopper) throws FileNotFoundException {
		try {
			FileWriter abc = new FileWriter(shopper.userID + ".csv");
			BufferedWriter bufferedWriter = new BufferedWriter(abc);
			String w = "";
			//first line is the shopper's address
			if (shopper.address != null)
				w += "Address," + shopper.address + "\n";
			//followed by their cart and their invoice (formated in the shopper class)
			w += shopper.cToString() + shopper.iToString();
			bufferedWriter.append(w);
			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to PATH");
		}
	}

}
