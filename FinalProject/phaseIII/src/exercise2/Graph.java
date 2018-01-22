package exercise2;

import java.util.ArrayList;
/**
 * Graph class used in assisting to find the best route between the cities
 * @author zhongtian
 *
 */
public class Graph {
	public static ArrayList<Vertex> vertices;
	public static ArrayList<Edge> edges;
	public static ArrayList<String> vertices_name;

	public Graph() {
		vertices_name = new ArrayList<String>();
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	private static boolean contains(Edge e){
		for (Edge e1:Graph.edges){
			if (e1.equals(e))
				return true;
		}
		return false;
			
	}
	/**
	 * Adds an edge between two vertices with given distance
	 * @param v1 -> vertex 1
	 * @param v2 -> vertex 2
	 * @param distance -> distance between the distance
	 * @throws EdgeExistsException 
	 */
	public static void addEdge(Vertex v1, Vertex v2, int distance) throws EdgeExistsException {
		Edge e1 = new Edge(v1, v2, distance);
		Edge e2 = new Edge(v2, v1, distance);
		if (!contains(e1) & !contains(e2)) {
			edges.add(e2);
			edges.add(e1);
		} else
			throw new EdgeExistsException();
	}

	/**
	 * Add vertex to the graph
	 * @param vertex -> the vertex
	 * @return the vertex
	 */
	public static Vertex addVertex(String vertex) {
		Vertex v = new Vertex(vertex);
		if (!vertices.contains(v)) {
			vertices_name.add(vertex);
			vertices.add(v);
			return v;
		}
		return null;
	}

	/**
	 * Find the vertex in the given city
	 * @param city -> the city
	 * @return the vertex if found else null
	 */
	public static Vertex findvertex(String city) {
		for (Vertex v : vertices) {
			if (v.city.equals(city)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Gets the distance between two vertices
	 * @param v1 -> vertex 1
	 * @param v2 -> vertex 2
	 * @return the edge distance if found else -1
	 */
	public static int getDistance(Vertex v1, Vertex v2) {
		for (Edge edge : edges) {
			if (edge.vertex1.equals(v1) && edge.vertex2.equals(v2))
		        return edge.distance;
		}
		return -1;
	}
}