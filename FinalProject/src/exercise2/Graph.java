package exercise2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import stack.*;
import support.LLNode;

public class Graph<T> {
	public static final int NULL_EDGE = 0;
	public static final int DEFAULT_WEIGHT = 1;
	protected static int numVertices;
	public static ArrayList<String> vertices_name;
	public static ArrayList<Vertex> vertices;
	public static ArrayList<Edge> edges;
	protected static Set<Vertex> marks; 
	protected static Set<Vertex> unmarks; 
	protected static HashMap<Vertex,Vertex> vertexLink;
	protected static HashMap<Vertex,Integer> distance;
	

	public Graph(){
		vertices_name = new ArrayList<String>();
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
	}
	
	public boolean isEmpty()
	// Returns true if this graph is empty; otherwise, returns false.
	{
		return (numVertices == 0);
	}
	
	public static void dijkstraAlghoithm(Vertex start){
		marks = new HashSet<Vertex>();
		unmarks = new HashSet<Vertex>();
		distance = new HashMap<Vertex,Integer>();
		vertexLink = new HashMap<Vertex,Vertex>();
		distance.put(start, 0);
		unmarks.add(start);
		while(!unmarks.isEmpty()){
			Vertex v = closetVertex(unmarks);
			marks.add(v);
			unmarks.remove(v);
			MinDistance(v);
		}
	}

	// Please complete the code for the following method.
	// A correctly working method gets up to 2 marks
	// A quality implementation gets up to 2 marks
	public static Vertex addVertex(String vertex){
		// If graph is full, it throws GraphIsFullException
		// If vertex is already in this graph, it throws VertexExistsException
		// Otherwise adds vertex to this graph.
		//
		// Your code goes here
		Vertex v = new Vertex(vertex);
		vertices_name.add(vertex);
		vertices.add(v);
		numVertices ++;
		return v;
	}
	
	public static Vertex findvertex(String city){
		for (Vertex v: vertices){
			if (v.getCity() == city){
				return v;
			}
		}
		return null;
	}
	
	// Please complete the code for the following method.
	// A correctly working method gets up to 2 marks
	// A quality implementation gets up to 2 marks
	public static void addEdge(Vertex fromVertex, Vertex toVertex, int distance)throws EdgeExistsException{
	// Adds an edge with the specified weight from fromVertex to toVertex.
         // Your code goes here
		Edge cur = new Edge(fromVertex,toVertex,distance);
		if (!edges.contains(cur)){
				edges.add(cur);
		}
		else{
			throw new EdgeExistsException();
		}
	}


	// Please complete the code for the following method.
	// A correctly working method gets up to 1 mark

	public static boolean isMarked(Vertex vertex)
	// Returns true if vertex is marked; otherwise, returns false.
	{
		return marks.contains(vertex);
		
	}

	public boolean in(T vertex, ArrayList<Set<T>> list) {
		for (Set<T> set: list) {
			if (set.contains(vertex)) {
				return true;
			}
		}
		return false;
	}
		
	public static int getDistance(Vertex start,Vertex destination){
		for(Edge e: edges){
			if (e.start.equals(start)&&e.destionation.equals(destination)){
				return e.distance;
			}
		}
		return -1;
	}
	
	public static ArrayList<Vertex> neighbours(Vertex start){
		ArrayList<Vertex> neighList = new ArrayList<Vertex>();
		for(Edge e: edges){
			if (e.start.equals(start) && !isMarked(e.destionation)){
				neighList.add(e.destionation);		
			}
		}
		return neighList;
	}
	
	public static int shortestDistance(Vertex destination){
		int result = distance.get(destination);
		if (result == 0){
			return -1;
		}
		return result;
	}
	
	public static Vertex closetVertex(Set<Vertex> v){
		Vertex closest = null;
		for(Vertex ver: v){
			if (closest != null){
				if (shortestDistance(ver)<shortestDistance(closest)){
					closest = ver;
				}
			}
			else{
				closest = ver;
			}
		}
		return closest;
	}
	
	public static void MinDistance(Vertex v){
		ArrayList<Vertex> neigh = neighbours(v);
		for(Vertex cur: neigh){
			if (shortestDistance(cur)> shortestDistance(v)+
					+getDistance(v,cur)){
				distance.put(cur, shortestDistance(v)+getDistance(v,cur));
				vertexLink.put(cur,v);
				unmarks.add(cur);
			}
		}
	}
	
	public static LinkedList<Vertex> getPath(Vertex destination){
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex next = destination;
		if (vertexLink.get(destination)== null){
			return null;
		}
		path.add(next);
		while(vertexLink.get(next)!= null){
			next = vertexLink.get(next);
			path.add(next);
		}
		Collections.reverse(path);
		return path;
		
	}
}
