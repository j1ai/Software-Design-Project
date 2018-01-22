package finalproject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestClass {

	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		Inventory inv = new Inventory();
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
		
		
		
		
		
		
	}

}
