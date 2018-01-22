package finalproject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import exercise2.*;

public class TestGetDeliveryRoute {
	public static void main(String[] args) throws IOException, NumberFormatException, OutOfStockException, EdgeExistsException{
		Graph g = new Graph();
		Project p = new Project();
		p.addUser("1", "pass", false);
		p.addUser("2", "pass", true);
		String dis1 = "A";
		String dis2 = "B";
		String dis3 = "C";
		String dis4 = "D";
		ArrayList<String> distCenters = new ArrayList<String>();
		distCenters.add(dis2);
		distCenters.add(dis3);
		distCenters.add(dis4);
		int num = p.login("2", "pass");	
		p.addRoute(dis1, dis2, 10, num);
		p.addRoute(dis2, dis3, 1, num);
		p.addRoute(dis1, dis3, 5, num);
		p.addRoute(dis4, dis3, 6, num);
		p.addRoute(dis1, dis4, 3, num);
		p.addRoute(dis2, dis4, 5, num);
		
		Vertex v = new Vertex(dis1);
		LinkedList<Vertex> path = null;
		HashMap<Integer, LinkedList<Vertex>> checker = new HashMap<Integer, LinkedList<Vertex>>();
/*		for (String distCen: distCenters){
			Vertex disVer = Graph.findvertex(distCen);
			System.out.println(disVer);
			newg.dijkstraAlghoithm(disVer);
			System.out.println("pop");
			path = newg.getPath(v);
			System.out.println(path);
			if (path != null){
				System.out.println("test");
				int total_distance = 0;
				for (int i =0;i<path.size();i++){
					Vertex first = path.get(i);
					if (path.getLast()!= first){
						Vertex next = path.get(i+1);
						total_distance += newg.getDistance(first, next);
					}
					else{
						checker.put(total_distance, path);
					}
				}
			}
		}*/
		for (String distCen: distCenters){
			DijkstraAlgorithm d = new DijkstraAlgorithm(g);
			Vertex disVer = Graph.findvertex(distCen);
			d.execute(disVer);
			path = d.getPath(v);
			int total_distance = 0;
			if (path!=null){
				for (int i =0;i<path.size();i++){
					Vertex first = path.get(i);
					if (path.getLast()!= first){
						Vertex next = path.get(i+1);
						total_distance += Graph.getDistance(first, next);
					}
					else{
						checker.put(total_distance, path);
					}
				}
			}
		}
		System.out.println(checker);
		
		
	}
}
