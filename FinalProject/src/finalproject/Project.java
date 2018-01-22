package finalproject;

import java.io.FileNotFoundException;
import exercise2.*;
import stack.*;
import support.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is intended to be your API
 * @author Ilir
 *
 */
public class Project {
	protected Inventory inv;
	protected Graph<String> graph = new Graph<String>();
	protected Map<Integer, Shopper> customer = new HashMap<Integer, Shopper>();
	protected UserManager userManager;
	protected InventoryManager inventoryManager;
	
	public Project() throws IOException{
		inventoryManager = new InventoryManager();		
		userManager = new UserManager();
		inv = inventoryManager.readFromFile();
	}
	/**
	 * This method must add a new shopper user or administrator user.
	 * @param userID
	 * @param password
	 * @param admin -> if true, add an administrator user, otherwise add a shopper user
	 * @return -> true if operation successful, false otherwise
	 * @throws FileNotFoundException 
	 */
	public boolean addUser(String userID, String password, boolean admin) throws FileNotFoundException{
		// Your code goes here.
		try {
			userManager.readUserFile();
			if(UserManager.users.containsKey(userID))
				throw new ExistingUserException();
			else {
				if (admin) {
					userManager.writeUser(userID, password, "Admin");
				}
				else {
					userManager.writeUser(userID, password, "Shopper");
					Shopper u = new Shopper(userID, password);
					userManager.writeShopper(u);
				}
				return true;
			}
		} catch (ExistingUserException e) {
			return false;
		}
	}
	
	/**
	 * Authenticates a user and creates an active work session
	 * @param userID 
	 * @param password
	 * @return -> SessionID if authentication successful, -1 otherwise.
	 * @throws FileNotFoundException 
	 * @throws OutOfStockException 
	 * @throws NumberFormatException 
	 */
	public int login(String userID, String password) throws FileNotFoundException, NumberFormatException{
		// Your code goes here.
		// Replace the statement below with the correct code.
		try{
			userManager.readUserFile();
			if (UserManager.users.containsKey(userID)){
				String[] login_info = UserManager.users.get(userID).split(",");
				if (login_info[0].equals(password)){
					if (login_info[1].equals("Shopper")){
						Shopper user1 = new Shopper(userID, password);
						userManager.readShopper(user1, inv);
						return user1.sessionID;
					}
					else{
						User user1 = new Administrator(userID, password);
						return user1.sessionID;
					}
				}
				else
					throw new InvalidPassException();
			}
			else
				throw new UserDNEException();
		}catch(UserDNEException|InvalidPassException e){
			return -1;
		}
	}
	
	/**
	 * Makes sessionID unavailable for connection
	 * @param sessionID
	 * @throws FileNotFoundException 
	 */
	public void logout(int sessionID) throws FileNotFoundException {
		// your code goes here
		if (User.allUser.containsKey(sessionID)){
			if (User.allUser.get(sessionID).type.equals("Shopper")){
				Shopper s = (Shopper) User.allUser.get(sessionID);
				userManager.writeShopper(s);
			}
			User.allUser.remove(sessionID);	
		}
	}
	
	/**
	 * This method must add a new category in your application.
	 * @param catName -> The name of the category to be added.
	 * @param sessionID -> A session ID that belongs to an authenticated administrator
	 * @return -> The ID of the category you created if successful, -1 if not successful.
	 * @throws IOException 
	 */
	public int addCategory(String catName, int sessionID) throws IOException {
		// Your code goes here.
		// Replace the statement below with the correct code.
		try{
			if (User.allUser.containsKey(sessionID)){
				User u = User.allUser.get(sessionID);
				int ID = ((Administrator)u).AddCategory(inv, catName);
				inventoryManager.writeFile(inv);
				return ID;
			}
			return -1;
		}catch(ClassCastException|ExistingCatException e){
			
			return -1;
		}
	}
	
	/**
	 * Adds a distribution center to your application.
	 * If the given distribution center exists, or sesionID invalid, do nothing.
	 * @param city -> The city where distribution center must be based.
	 * @param sessionID -> A session ID that belongs to an authenticated administrator
	 * @throws IOException 
	 */
	public void addDistributionCenter(String city, int sessionID) throws IOException {
		// Your code goes here
		try{
			User u = User.allUser.get(sessionID);
			((Administrator)u).addDistributionCenter(inv, city);
			inventoryManager.writeFile(inv);
		}catch(ClassCastException e){
		}
	}
	
	/**
	 * Adds a new Customer to your application; the customer record that belongs 
	 * to a newly added shopper user that has no customer record on the system.
	 * @param custName -> The name of the customer
	 * @param city -> The city of the customer address
	 * @param street -> The street address of the customer
	 * @param sessionID -> A valid sessionID that belongs to an authenticated shopper user.
	 * @return -> The added customer ID
	 */
	public int addCustomer(String custName, String city, String street, int sessionID) {
		Shopper s;
		if (User.allUser.containsKey(sessionID) && 
			User.allUser.get(sessionID).type.equals("Shopper")){
				s = (Shopper) User.allUser.get(sessionID);
				if (s.address == null){
					int custID = s.addAddress(street, city, custName);
					customer.put(custID,s);
					return custID;	
				}
		}
		return -1;
	}
	
	/**
	 * Adds a new Product to your application
	 * @param prodName -> The product name
	 * @param category -> The product category.
	 * @param price -> The product sales price
	 * @param sessionID -> A session ID that belongs to an authenticated administrator
	 * @return -> Product ID if successful, -1 otherwise.
	 * @throws IOException 
	 */
	public int addProduct(String prodName, int category, double price, int sessionID) throws IOException {
		// Your code goes here.
		// Replace the statement below with the correct code.
		try{
			if (User.allUser.containsKey(sessionID)){
				Category c1 = null;
				User u = User.allUser.get(sessionID);
				for(Category c:inv.category_list)
					if (c.code == category){
						c1 = c;
					}
				if (c1 == null)
					throw new CategoryDNEException();
				inventoryManager.writeFile(inv);
				return ((Administrator)u).addProduct(inv, prodName, c1, price);
			}
			return -1;
		}catch(CategoryDNEException|ClassCastException|ExistingProdException e){
			return -1;
		}
	}
	
	/**
	 * Computes the available quantity of prodID in a specific distribution center.
	 * @param prodID
	 * @param center
	 * @return -> Available quantity or -1 if prodID or center does not exist in the database
	 */
	public int prodInquiry(int prodID, String center) {
		// Your code goes here.
		// Replace the statement below with the correct code.
		ArrayList<Product> plist = inv.allProduct();
		Product real_product = null;
		for (Product p: plist){
			if (p.ID == prodID){
				real_product = p;
			}
		}
		if (real_product != null){
			for (Map.Entry<String, Integer> dicenter : real_product.distCenters.entrySet())
			{
			    String dis = dicenter.getKey();
			    if (dis == center){
			    	return dicenter.getValue();
			    }
			}
		}
		return -1;
		
	}
	
	/**
	 * Updates the stock quantity of the product identified by prodID
	 * @param prodID -> The product ID to be updated
	 * @param distCentre -> Distribution Center (in effect a city name)
	 * @param quantity -> Quantity to add to the existing quantity
	 * @param sessionID -> A session ID that belongs to an authenticated administrator
	 * If currently the product 112 has quantity 100 in Toronto,
	 * after the statement updateQuantity(112, "Toronto", 51)
	 * same product must have quantity 151 in the Toronto distribution center. 
	 * @return -> true if the operation could be performed, false otherwise.
	 * @throws IOException 
	 */
	public boolean updateQuantity(int prodID, String distCentre, int quantity, int sessionID) throws IOException {
		// Your code goes here.
		// Replace the statement below with the correct code.
		try{
			if (User.allUser.containsKey(sessionID)){
				User u = User.allUser.get(sessionID);
				if (inv.distCenter_list.contains(distCentre)){
					ArrayList<Product> product_list = inv.allProduct();
					Product product = null;
					for (Product p: product_list)
						if (p.ID == prodID)
							product = p;
					if (product != null){
						((Administrator)u).updateQuantity(product, distCentre, quantity);
						inventoryManager.writeFile(inv);
						return true;
					}
				}
			}
			return false;
		}catch(ClassCastException e){
			return false;
		}
	}
	
	/**
	 * Adds two nodes cityA, cityB to the shipping graph
	 * Adds a route (an edge to the shipping graph) from cityA to cityB with length distance
	 * If the nodes or the edge (or both) exist, does nothing
	 * @param cityA 
	 * @param cityB
	 * @param distance -> distance (in km, between cityA and cityB)
	 * @param sessionID -> A session ID that belongs to an authenticated administrator
	 * @throws VertexExistsException 
	 * @throws GraphIsFullException 
	 */
	public void addRoute(String cityA, String cityB, int distance, int sessionID){
		
		System.out.println(User.allUser.containsKey(sessionID));
		if (User.allUser.containsKey(sessionID) && 
				User.allUser.get(sessionID).type.equals("Admin")){
						
				if (Graph.vertices_name.contains(cityA)&& !Graph.vertices_name.contains(cityB)){
					Vertex v = Graph.addVertex(cityB);
					Vertex va = Graph.findvertex(cityA);
					try {
						Graph.addEdge(va,v,distance);
						
					} catch (EdgeExistsException e) {
					}
				}
				else if (Graph.vertices_name.contains(cityB)&&!Graph.vertices_name.contains(cityA)){
					Vertex v = Graph.addVertex(cityA);
					Vertex vb = Graph.findvertex(cityB);
					try {
						Graph.addEdge(v,vb,distance);
						
					} catch (EdgeExistsException e) {
					}
				}
				else if (!Graph.vertices_name.contains(cityB)&&!Graph.vertices_name.contains(cityA)){
					Vertex va = Graph.addVertex(cityA);
					Vertex vb = Graph.addVertex(cityB);
					try {
						Graph.addEdge(va,vb,distance);
						
					} catch (EdgeExistsException e) {
					}
				}
				else if (Graph.vertices_name.contains(cityA)&& Graph.vertices_name.contains(cityB)) {
						Edge e1 = new Edge(Graph.findvertex(cityA), Graph.findvertex(cityB), distance);
						Edge e2 = new Edge(Graph.findvertex(cityB), Graph.findvertex(cityA), distance);
						if (!(Graph.edges.contains(e1) || Graph.edges.contains(e2))) {
							Vertex va = Graph.findvertex(cityA);
							Vertex vb = Graph.findvertex(cityB);
							try {Graph.addEdge(va,vb,distance);
								} 
							catch (EdgeExistsException e) { }
							}
						}
					}
		}
	
	/**
	 * Attempts an order in behalf of custID for quantity units of the prodID
	 * @param custID -> The customer ID
	 * @param prodID -> The product ID
	 * @param quantity -> The desired quantity
	 * @param sessionID -> A valid sessionID that belongs to an authenticated shopper user.
	 * @return -> The orderID if successful, -1 if not.
	 */
	public int placeOrder(int custID, int prodID, int quantity, int sessionID) {
		// Your code goes here.
		// Replace the statement below with the correct code.
		ArrayList<Product> plist = inv.allProduct();
		Product real_product = null;
		if (User.allUser.containsKey(sessionID) && 
				User.allUser.get(sessionID).type.equals("Shopper")){
			for (Product p: plist)
				if (p.ID == prodID){
					real_product = p;
				}
			if (real_product != null){
				if(customer.containsKey(custID)){
					Shopper s = customer.get(custID);
					ArrayList<String> result = s.addtoCart(real_product, quantity);
					if (!result.isEmpty()){
						Order o = new Order(s.address.city, result, real_product, quantity);
						String distCenter =	getDeliveryRoute(o.ID, sessionID).get(0);
						real_product.quantity -= quantity;
						real_product.distCenters.put(distCenter, real_product.distCenters.get(distCenter)-quantity);
						return o.ID;
					}
				}
			}
		}
		return -1;
	}
    
	/**
	 * Returns the best (shortest) delivery route for a given order 
	 * @param orderID -> The order ID we want the delivery route
	 * @param sessionID -> A valid sessionID that belongs to an authenticated shopper user.
	 * @return -> The actual route as an array list of cities, null if not successful
	 */
	public ArrayList<String> getDeliveryRoute(int orderID, int sessionID) {
		// Your code goes here.
		// Replace the statement below with the correct code.
		ArrayList<String> result = new ArrayList<String>();
		if (User.allUser.containsKey(sessionID) && 
				User.allUser.get(sessionID).type.equals("Shopper")){
			
			if (Order.orderList.containsKey(orderID)){
				Order o = Order.orderList.get(orderID);
				Vertex v = Graph.findvertex(o.city);
				if (v != null){
					LinkedList<Vertex> path = null;
					for (String distCen: o.distCenters){
						Vertex disVer = Graph.findvertex(distCen);
						Graph.dijkstraAlghoithm(disVer);
						path = Graph.getPath(v);
					}
					Vertex x;
					for (int i=0; i<((LinkedList<Vertex>) path).size()-1;i++) {
						result.add(path.get(i).getCity());
					o.route = result;
					}
				}
			}
		}
		return result;
	}
	
	/** 
	 * Computes the invoice amount for a given order.
	 * Please use the fixed price 0.01$/km to compute the shipping cost 
	 * @param orderID
	 * @param sessionID -> A valid sessionID that belongs to an authenticated shopper user.
	 * @return
	 */
	public double invoiceAmount(int orderID, int sessionID) {
		// Your code goes here.
		// Replace the statement below with the correct code.
		if (User.allUser.containsKey(sessionID) && 
				User.allUser.get(sessionID).type.equals("Shopper")){
			Order order = Order.orderList.get(orderID);
			ArrayList<String>route = order.route;
			int distance = 0;
			for (int i = 0; i < route.size()-1;i++){
				Vertex start = Graph.findvertex(route.get(i));
				Vertex destination = Graph.findvertex(route.get(i+1));
				distance += Graph.getDistance(start, destination);
			}
			double shippingCost = distance * 0.01;
			double cost = order.product.price * order.quantity;
			return shippingCost+cost;
		}
		return -1;
	}
}
