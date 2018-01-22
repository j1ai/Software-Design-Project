package finalproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SpringLayout;

import com.sun.glass.events.MouseEvent;

import exercise2.EdgeExistsException;
import exercise2.Vertex;
import finalproject.ProjectV1.ShoppingCartItem;

/**
 * The main application
 * @author all members
 *
 */
public class Shop extends JFrame {
	private JMenuBar bar;
	private RegisterListener reglistener;
	private LoginListener loginlistener;
	private LogoutListener logoutlistener;
	private BrowseListener browselistener;
	private JMenu startMenu;
	private JButton browseButton;
	private JMenuItem registerItem, loginItem, logoutItem;
	private JLabel welcome;
	private static boolean loggedIn = false;
	private boolean loggedOut = true;
	private Map<Product, Integer> productList;
	private List<JLabel> tempProd;
	private Map<JButton, Product> tempButton;
	private static boolean inShoppingCart = false;
	private User cur_user;
	private static boolean validUser = false;
	private ArrayList<JLabel> labels;
	JButton shoppingcart;
	JButton checkout;
	JButton info;
	JButton history;
	JButton addCategory;
	JButton addProduct;
	JButton addWarehouse;
	JButton maintainQuantity;
	JButton addRoute;
	JButton salesReport;
	JButton refresh;
	

	private JPanel panel;
	// private JScrollPane panel;
	protected ProjectV1 p;
	protected int sessionID;

	//menu bars
	public Shop() throws IOException {
		super("Shopping App");
		setContentPane(new JLabel(new ImageIcon("Products/bg.jpg")));
		setLayout(new BorderLayout());

		p = new ProjectV1();
		panel = new JPanel();

		// JScrollPane panel = new JScrollPane(this,
		// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		welcome = new JLabel();
		welcome.setFont(new Font("Sanserif", Font.ITALIC, 10));
		welcome.setEnabled(false);
		productList = new HashMap<Product, Integer>();
		tempButton = new HashMap<JButton, Product>();
		tempProd = new ArrayList<>();
		addProducts();

		bar = new JMenuBar();
		setJMenuBar(bar);
		startMenu = new JMenu("Start");
		bar.add(startMenu);

		registerItem = new JMenuItem("Register");
		// registerItem.setMnemonic('I');
		startMenu.add(registerItem);
		reglistener = new RegisterListener();
		registerItem.addActionListener(reglistener);

		loginItem = new JMenuItem("Log In");
		// loginItem.setMnemonic('I');
		startMenu.add(loginItem);
		loginlistener = new LoginListener();
		loginItem.addActionListener(loginlistener);

		logoutItem = new JMenuItem("Log Out");
		// logoutItem.setMnemonic(KeyEvent.VK_T);
		logoutItem.setEnabled(false);
		logoutlistener = new LogoutListener();
		logoutItem.addActionListener(logoutlistener);
		startMenu.add(logoutItem);

		browseButton = new JButton("Browse");
		bar.add(browseButton);
		browselistener = new BrowseListener();
		browseButton.addActionListener(browselistener);
		
		shoppingcart = new JButton("Shopping Cart");
		checkout = new JButton("CheckOut");
		info = new JButton("Personal information");
		history = new JButton("History of Purchase");
		bar.add(checkout);
		bar.add(shoppingcart);
		bar.add(info);
		bar.add(history);
		
		
		addCategory = new JButton("Add category");
		 addProduct = new JButton("Add Product");
		addWarehouse = new JButton("Add WareHouse");
		maintainQuantity = new JButton("Maintain Product Quantities");
		addRoute = new JButton("Add Shipping route");
		salesReport = new JButton("SalesReport");
		bar.add(addCategory);
		bar.add(addProduct);
		bar.add(salesReport);
		bar.add(addRoute);
		bar.add(maintainQuantity);
		bar.add(addWarehouse);

		labels = new ArrayList();
		this.setSize(600, 400);
		this.setVisible(true);
		
		addCategory.setVisible(false);
		addProduct.setVisible(false);
		salesReport.setVisible(false);
		addRoute.setVisible(false);
		maintainQuantity.setVisible(false);
		addWarehouse.setVisible(false);
		checkout.setVisible(false);
		shoppingcart.setVisible(false);
		info.setVisible(false);
		history.setVisible(false);
	}

	/**
	 * Adding all products in inventory list and adding their pictures with the respective product
	 */
	private void addProducts() {
		List<Product> products = p.inv.allProduct();
		for (Product p : products) {
			productList.put(p, p.quantity);
		}
		drawProductImage(productList);
	}

	/**
	 * Login class, when user is authenticated, they are either directed into the Shopper or admin frame
	 *
	 */
	private class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				User u = isAuthenticated();
				if (u != null) {
					loggedIn = true;
					loginItem.setEnabled(false);
					logoutItem.setEnabled(true);
					bar.add(add(Box.createHorizontalGlue()));
					bar.add(welcome);
					for (JLabel l : tempProd)
						l.setToolTipText("Add to shopping cart!");
					revalidate();
					if (u.type.equals("Shopper")) {
						ShopperFrame(u);
					} else {
						AdminFrame(u);
					}

				}
			} catch (NumberFormatException | OutOfStockException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Logout class where the user logs them self out
	 *
	 */
	private class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (cur_user != null) {
				try {
					p.logout(cur_user.sessionID);
					cur_user = null;
					loginItem.setEnabled(true);
					logoutItem.setEnabled(false);
					addCategory.setVisible(false);;
					addProduct.setVisible(false);
					salesReport.setVisible(false);
					addRoute.setVisible(false);
					maintainQuantity.setVisible(false);
					addWarehouse.setVisible(false);
					checkout.setVisible(false);
					shoppingcart.setVisible(false);
					info.setVisible(false);
					history.setVisible(false);
					
					refresh();
					validUser = false;
					validate();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Refresh used for when new products are added and the screen gets refreshed with new products
	 */
	public void refresh() {
		panel.removeAll();
		welcome.setText("");
		addProducts();
		revalidate();
	}

	/**
	 * Register class where a  new User register's themselves
	 *
	 */
	class RegisterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			JTextField check = new JTextField();
			Object[] message = { "Username:", username, "Password:", password, "Are you Admin? (Yes or No)", check };
			int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (username.getText().equals("") || password.equals(""))
					JOptionPane.showMessageDialog(null, "Please input username and password.");
				if (check.getText().equals("Yes")) {
					try {
						boolean x = p.addUser(username.getText(), new String(((JPasswordField) password).getPassword()),
								true);
						if (!x)
							JOptionPane.showMessageDialog(null, "username taken.");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (check.getText().equals("No")) {
					try {
						boolean y = p.addUser(username.getText(), new String(((JPasswordField) password).getPassword()),
								false);
						if (!y)
							JOptionPane.showMessageDialog(null, "username taken.");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please input 'Yes' or 'No' for 'Are you Admin?'");
				}
			}
		}
	}

	/**
	 * Displays the images for every product in the product list
	 * @param prodList -> the list of all the products
	 */
	public void drawProductImage(Map<Product, Integer> prodList) {
		//labels.clear();
		JLabel prod;
		//tempProd.clear();
		for (Map.Entry<Product, Integer> item : prodList.entrySet()) {
			Product p = item.getKey();
			ImageIcon prodImage = new ImageIcon(p.image);
			prod = new JLabel();
			JButton temp = new JButton();
			temp.setIcon(prodImage);
			temp.addActionListener(new ButtonAction());
			//labels.add(prod);
			// prod.setText("Quantity: ");
			// prod.setHorizontalTextPosition(JLabel.CENTER);
			// prod.setVerticalTextPosition(JLabel.BOTTOM);
			// prod.add(temp);
			panel.add(temp);
			add(panel,BorderLayout.CENTER);
			tempProd.add(prod);
			tempButton.put(temp, p);
		}
	}

	/**
	 * Browse all products according to category, minimum and max price
	 * @author Cathy
	 *
	 */
	class BrowseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int min;
			int max;
			JTextField category = new JTextField();
			JTextField maxp = new JTextField();
			JTextField minp = new JTextField();
			Object[] message = { "Category:", category, "Min Price:", minp, "Max price:", maxp };
			int option = JOptionPane.showConfirmDialog(null, message, "Search", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (category.getText().equals("") || minp.equals("") || maxp.equals(""))
					JOptionPane.showMessageDialog(null, "Please fill in information!");
				try {
					min = Integer.parseInt(minp.getText());
					max = Integer.parseInt(maxp.getText());
					if (min >= 0) {
						tempProd.clear();
						ArrayList<Product> browsed = User.browseMerchandise(p.inv, category.getText(), max, min);
						Map<Product, Integer> prodList = new HashMap<Product, Integer>();
						for (Product p : browsed) {
							prodList.put(p, p.quantity);
						}
						panel.removeAll();
						drawProductImage(prodList);
						revalidate();
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please fill in Number for Price!");
					// not an integer!
				}
			}
		}
	}

	/**
	 * Authenticates a user when they are login in
	 * @return Session ID
	 * @throws NumberFormatException
	 * @throws OutOfStockException
	 * @throws IOException
	 */
	private User isAuthenticated() throws NumberFormatException, OutOfStockException, IOException {
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = { "Username:", username, "Password:", password };
		// boolean result = true;
		User result = null;
		int sessionID;
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			if (username.getText().equals("") || password.equals(""))
				JOptionPane.showMessageDialog(null, "Please input username and password.");

			sessionID = p.login(username.getText(), new String(((JPasswordField) password).getPassword()));
			if (sessionID != -1) {
				
				welcome.setText("Welcome, " + username.getText() + "!  ");
				this.sessionID = sessionID;
				result = User.allUser.get(sessionID);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong Password/Username");
				// result = false;
			}
		}
		return result;
	}

	/**
	 * Confirms the shopper has logged out
	 * @return
	 */
	public boolean hasLoggedOut() {
		return loggedOut;
	}

	/**
	 * When the User logs in as shopper, this frame is executed
	 * @param u -> the user
	 */
	public void ShopperFrame(User u) {
		validUser = true;
		Shopper s = (Shopper) u;
		cur_user = s;
		checkout.setVisible(true);
		shoppingcart.setVisible(true);
		info.setVisible(true);
		history.setVisible(true);

		//the shopping cart
		shoppingcart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Map<Product, Integer> cart = s.getShoppingCart();
				ShoppingCartFrame sf = new ShoppingCartFrame(cart, s);
				for (JLabel l : tempProd)
					l.setToolTipText("The item is in your shopping cart");

			}

		});

		//history of purchases
		history.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				OrderReport o = new OrderReport(s);
			}
			
		});

		//checkout
		checkout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (s.address != null){
					if (p.customer.containsKey(s.address.ID)){
						try {
							if (s.cart.size()!=0){
								int orderID = p.checkout(s.address.ID, s.sessionID);
								if (orderID != 0){
									JOptionPane.showMessageDialog(null, "Thank you. You have checked out.");
									JOptionPane.showMessageDialog(null, Order.orderList.get(orderID).toString());
								}
								else
									JOptionPane.showMessageDialog(null, "Check out failed.");
							}
							else
								JOptionPane.showMessageDialog(null, "Your shopping cart is empty.");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "Customer information not added");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Customer information not added");
				}

			}

		});

		//user register's their own personal information for purchases
		info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField name = new JTextField();
				JTextField city = new JTextField();
				JTextField street = new JTextField();
				Object[] message = { "Name:", name, "Street:", street, "City:", city, };
				int option = JOptionPane.showConfirmDialog(null, message, "Personal Information",
						JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (name.getText().equals("") || city.getText().equals("") || street.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please fill in information!");
					} else {
						int success = p.addCustomer(name.getText(), city.getText(), street.getText(), sessionID);
						if (success == -1)
							JOptionPane.showMessageDialog(null, "Data already exists");
						else
							JOptionPane.showMessageDialog(null, "Thank You!");
					}
				}
			}

		});

	}

	/**
	 * Confirms whether a string is a integer
	 * @param s string
	 * @return true is integer, false otherwise
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	/**
	 * class used while adding products to cart
	 *
	 */
	class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource();
			Product p = null;
			for (Map.Entry<JButton, Product> item : tempButton.entrySet()) {
				if (pressed.equals(item.getKey())) {
					p = item.getValue();
				}
			}
			if (p != null) {
				if (validUser != true) {
					JOptionPane.showMessageDialog(null, "Product: "+ p.description +"\n" + "Price: " + p.price + "\n" +" Available Quantity: " + (p.quantity-p.temp) +"\n");
				} else {
					JOptionPane.showMessageDialog(null, "Product: "+ p.description +"\n" + "Price: " + p.price + "\n" +" Available Quantity: " + (p.quantity-p.temp) +"\n");
					String str = JOptionPane.showInputDialog("Type in the amount you wish to add to cart");
					int option = JOptionPane.showConfirmDialog(null, str+"Amount added to Shopping Cart", "Add to Cart", JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						if (!isInteger(str)) {
							JOptionPane.showMessageDialog(null, "Wrong Input!!!");
							JOptionPane.showMessageDialog(null, "Product: "+ p.description +"\n" + "Price: " + p.price + "\n" +" Available Quantity: " + (p.quantity-p.temp) +"\n");
						}
						else{
							boolean result = ((Shopper) cur_user).addtoCart(p, Integer.parseInt(str));
							if (!result) {
								JOptionPane.showMessageDialog(null,
										"The available amount is not sufficient for your order!");
							}
						}
					}
				}

			}
		}

	}

	/**
	 * New frame just for the shopper's shopping cart
	 * 
	 * @author JLAI
	 *
	 */
	public class ShoppingCartFrame extends JFrame {
		JPanel pan;
		public Map<Product, Integer> cart;
		public Map<JButton, Product> ShoppingCarttempButton;
		public Shopper s;

		public ShoppingCartFrame(Map<Product, Integer> cart, Shopper s) {
			this.cart = cart;
			this.s = s;
			pan = new JPanel();
			ShoppingCarttempButton = new HashMap<JButton, Product>();
			drawProductImage2(cart);
			this.setSize(600, 400);
			this.setVisible(true);
		}

		public void drawProductImage2(Map<Product, Integer> prodList) {
			JLabel prod2;
			//tempProd.clear();
			for (HashMap.Entry<Product, Integer> item : prodList.entrySet()) {
				Product p = item.getKey();
				ImageIcon prodImage = new ImageIcon(p.image);
				JButton temp = new JButton();
				prod2 = new JLabel();
				temp.setIcon(prodImage);
				temp.addActionListener(new Action());
				prod2.add(temp);
				prod2.setToolTipText("Product: "+ p.description + " Price: " + p.price + " Available Quantity: " + p.quantity);
				// prod.setText("Quantity: ");
				// prod.setHorizontalTextPosition(JLabel.CENTER);
				// prod.setVerticalTextPosition(JLabel.BOTTOM);
				// prod.add(temp);
				pan.add(temp);
				add(pan);
				ShoppingCarttempButton.put(temp, p);		
			}
			revalidate();
		}
		
		//Admin's frame of updating quantities of products
		class Action implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton press = (JButton) e.getSource();
				Product p1 = null;
				for (Map.Entry<JButton, Product> item : ShoppingCarttempButton.entrySet()) {
					if (press.equals(item.getKey())) {
						p1 = item.getValue();
					}
				}
				if (p1 != null) {
					JOptionPane.showMessageDialog(null, "Product: "+ p1.description +"\n" + "Price: " + p1.price + "\n" +"Quantity: " + cart.get(p1) +"\n");
					String st = JOptionPane.showInputDialog("Remove item or Update Quantity (Type R or U)");
					if (st!= null){
						if (st.equals("R")) {
							s.removeFromCart(p1);
							JOptionPane.showMessageDialog(null, "Item has been removed from the cart");
						}
						else if (st.equals("U")) {
							st = JOptionPane.showInputDialog("Type in your desired Quantity");
							if (!isInteger(st)) {
								JOptionPane.showMessageDialog(null, "Wrong Input!!!");
							}
							else{
								int num = Integer.parseInt(st);
								num = num - cart.get(p1);
								s.addtoCart(p1, num);
								JOptionPane.showMessageDialog(null, "Desired Quantity has been updated.");
								JOptionPane.showMessageDialog(null, "Product: "+ p1.description +"\n" + "Price: " + p1.price + "\n" +"Quantity: " + num+ "\n");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Wrong Input!!!");
						}
					}
				}
			}
		}
	}

	/**
	 * The administrator frame when an administrator logs in
	 * @param u -> the user
	 */
	public void AdminFrame(User u) {
		Administrator admin = (Administrator) u;
		cur_user = admin;
		validUser = false;

		//adding new categories
		addCategory.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField catName = new JTextField();
				Object[] message = { "Category name:", catName};
				int option = JOptionPane.showConfirmDialog(null, message, "Add Category", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION){
					String result = catName.getText();
					try {
						int num = p.addCategory(result, admin.sessionID);
						if (num == -1){
							JOptionPane.showMessageDialog(null, "Category is already existed or the input is invalid.");
						}
						else
							JOptionPane.showMessageDialog(null, "New Category has been added.");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		//adding new products
		addProduct.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField prodname = new JTextField();
				JTextField category = new JTextField();
				JTextField price = new JTextField();
				JTextField imgpath = new JTextField();
				Object[] message = { "Product name:", prodname, "Category:", category, "Price:", price , "Image Path:", imgpath};
				int option = JOptionPane.showConfirmDialog(null, message, "Add Product", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (category.getText().equals("") || prodname.getText().equals("") || price.getText().equals("") || imgpath.getText().equals("") 
							|| !isInteger(price.getText())) {
						JOptionPane.showMessageDialog(null,"Invalid Input");
						option = JOptionPane.showConfirmDialog(null, message, "Add Product", JOptionPane.OK_CANCEL_OPTION);
						}
					else{
						int result;
						try {
							int code = p.inv.findID(category.getText());
							if (code != -1) {
								result = p.addProduct(prodname.getText(), code, Double.parseDouble(price.getText()), admin.sessionID);}
							else { result = code; }
							if (result == -1){
								JOptionPane.showMessageDialog(null, "Invalid Input");
							}
							else{
								p.inv.findProduct(result).image = imgpath.getText();
								p.inventoryManager.writeFile(p.inv);
								JOptionPane.showMessageDialog(null, "Product has been added");
								refresh();
							}
						} catch (NumberFormatException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
			}
			}
		});

		//add routes
		addRoute.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField cityA = new JTextField();
				JTextField cityB = new JTextField();
				JTextField distance = new JTextField();
				Object[] message = { "City A:", cityA, "City B:", cityB, "Distance:", distance };
				int option = JOptionPane.showConfirmDialog(null, message, "Add Route", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (cityA.getText().equals("") | cityB.equals("") | distance.equals("")) {
						JOptionPane.showMessageDialog(null,"Invalid Input");
						option = JOptionPane.showConfirmDialog(null, message, "Add Route", JOptionPane.OK_CANCEL_OPTION);
					}
					else{
						try {
							p.addRoute(cityA.getText(), cityB.getText(), Integer.parseInt(distance.getText()), admin.sessionID);
							JOptionPane.showMessageDialog(null,"New route added.");
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (EdgeExistsException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null,"The Route already existed.");
						}
					}
			}
		}
	});
		
		//updating quantities of products
		maintainQuantity.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField prod = new JTextField();
				JTextField distCentre = new JTextField();
				JTextField quantity = new JTextField();
				Object[] message = { "Product Name", prod, "Distribution Centre:", distCentre, "Quantity", quantity };
				int option = JOptionPane.showConfirmDialog(null, message, "Maintain Product Quantities", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (prod.getText().equals("") || distCentre.equals("") || quantity.equals("")) {
						JOptionPane.showMessageDialog(null,"Invalid Input");
						option = JOptionPane.showConfirmDialog(null, message, "Maintain Product Quantities", JOptionPane.OK_CANCEL_OPTION);
					}
					else{
						try {
							int prodID = p.inv.findProductID(prod.getText());
							boolean flag = p.updateQuantity(prodID, distCentre.getText(), Integer.parseInt(quantity.getText()), admin.sessionID);
							if (flag){
								JOptionPane.showMessageDialog(null, "Product has been updated");
							}
							else{
								JOptionPane.showMessageDialog(null, "Invalid Input");
							}
						} catch (NumberFormatException | IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Invalid Input");
						}
					}
			}
		}
	});

		// Method to check whether this city exists
		addWarehouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField city = new JTextField();
				Object[] message = { "City:", city };
				int option = JOptionPane.showConfirmDialog(null, message, "Add Warehouse", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (city.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Invalid Input");
						option = JOptionPane.showConfirmDialog(null, message, "Add Warehouse",
								JOptionPane.OK_CANCEL_OPTION);
					} else {
						try {
							p.addDistributionCenter(city.getText(), admin.sessionID);
							JOptionPane.showMessageDialog(null,"New Warehouse added.(the warehouse may exist before added)");
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (CityDNEException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null,"The city do not exist.");
						}

					}
				}
			}
		});
		
		//generating a sales report
		salesReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SalesReport s = new SalesReport(admin,p.inv);
			}
			
		});

		// Generate sales report method

		revalidate();
		addCategory.setVisible(true);
		addProduct.setVisible(true);
		salesReport.setVisible(true);
		addRoute.setVisible(true);
		maintainQuantity.setVisible(true);
		addWarehouse.setVisible(true);

		// private void mouseClicked(MouseEvent e){
		// for (JLabel image: tempProd){
		// if (e.getSource() == image){

		// }
		// }
		// }

	}
	
	//sales report method
	public class SalesReport extends JFrame{
		private JLabel label;
		private JPanel panell;
		private Administrator admin;
		private Inventory inv;
		public SalesReport(Administrator admin, Inventory inv){
			this.admin = admin;
			this.inv = inv;
			panell = new JPanel();
			HashMap<Product, Integer> result = this.admin.salesReport(this.inv);
//			String message = "";
			label = new JLabel("======Sales Report===== \n");
			panell.add(label);
			for (HashMap.Entry<Product,Integer> cart : result.entrySet()){
				String message = "";
				message += "Product ID: " + cart.getKey().ID;
				message += "  Product Name: " + cart.getKey().description;
				message += "  Quantity: " + cart.getValue();
				message += "  Total: " + cart.getKey().price * cart.getValue();
  				message += "================== \n";
				label = new JLabel(message);
				panell.add(label);
			}
//		label = new JLabel(message);
	//		panell.add(label);
			add(panell);
			this.setSize(600, 400);
			this.setVisible(true);
		}
	}
	
	//order report for the shopper
	public class OrderReport extends JFrame{
		private JLabel lab;
		private JPanel pane2;
		private Shopper shopper;
		public OrderReport(Shopper shopper){
			this.shopper = shopper;
			pane2 = new JPanel();
			for (Invoice i : shopper.invoice){
				String result = "";
				result += "Invoice: " + i.iToString();
				lab = new JLabel(result);
				pane2.add(lab);
			}
			add(pane2);
			this.setSize(600, 400);
			this.setVisible(true);
			}

		}
	}
