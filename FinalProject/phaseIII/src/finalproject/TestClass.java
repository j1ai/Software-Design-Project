package finalproject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import exercise2.EdgeExistsException;
import exercise2.Graph;

public class TestClass {

	public static void main(String[] args) throws IOException, ClassNotFoundException, NumberFormatException, OutOfStockException, EdgeExistsException {
		
		// TODO Auto-generated method stub
		/*Inventory inv = new Inventory();
		inv.distCenter_list.add("Toronto");
		Category cat = new Category("Shoes", inv);
		Product pro = new Product("NMD", cat, 126.0);
		pro.imagePath = "";
		pro.quantity = 5;
		cat.product_list.add(pro);
		
		inv.category_list.add(cat);
		Category cat2 = new Category("Hat", inv);
		Product pro2 = new Product("Stuss", cat2, 12.0);
		pro2.imagePath = "";
		pro2.quantity = 3;
		cat2.product_list.add(pro2);
		inv.category_list.add(cat2);
		
		Product pro3 = new Product("assD", cat, 36.0);
		pro3.imagePath = "";
		pro3.quantity = 1;
		cat.product_list.add(pro3);
		pro3.distCenters.put("Toronto", pro3.quantity);
		InventoryManager m;
		try {
			m = new InventoryManager();
			m.writeFile(inv);
			inv = null;
			System.out.println(inv);
			inv = m.readFromFile();
			System.out.println(inv.category_list);
			
			System.out.println(inv.distCenter_list);
			for (Category c: inv.category_list) {
				for (Product p: c.product_list) {
					System.out.println(p);
				}
			}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Project p1 = new Project();
		boolean max = p1.addUser("Max", "1234", false);
		boolean leo = p1.addUser("Leo", "4321", true);
		boolean justin = p1.addUser("Justin", "2332", false);
		boolean max2 = p1.addUser("Max", "1111", false);
		int s1 = p1.login("max", "1111");
		
		*/
		
		Graph g = new Graph();
		Project p = new Project();
		p.addUser("3", "pass", false);
		p.addUser("2", "pass", true);
		String dis1 = "A";
		String dis2 = "B";
		String dis3 = "C";
		String dis4 = "D";
		String home = "G";
		p.inv.distCenter_list.add(dis1);
		p.inv.distCenter_list.add(dis2);
		p.inv.distCenter_list.add(dis3);
		p.inv.distCenter_list.add(dis4);
		int num = p.login("2", "pass");	
		p.addRoute(dis1, dis2, 10, num);
		p.addRoute(dis2, dis3, 1, num);
		p.addRoute(dis1, dis3, 5, num);
		p.addRoute(dis4, dis3, 6, num);
		p.addRoute(dis1, dis4, 3, num);
		p.addRoute(dis2, dis4, 5, num);
		p.addRoute(dis2, "E", 30, num);
		p.addRoute(dis4, "F", 6, num);
		p.addRoute("E", "G", 5, num);
		p.addRoute("F", "G", 30, num);
		int num2 = p.login("1", "pass");
		System.out.println(User.allUser);
		Category cat = new Category("Shoes", p.inv);
		Product pro = new Product("NMD", cat, 126.0);
		ArrayList<Product> prod = new ArrayList<Product>();
		prod.add(pro);
		ArrayList<Integer> q = new ArrayList<Integer>();
		q.add(5);
		Order o = new Order(home,p.inv.distCenter_list, prod, q);
		System.out.println(p.getDeliveryRoute(o.ID, num2));
		System.out.println(p.invoiceAmount(o.ID, num2));
	}
}
