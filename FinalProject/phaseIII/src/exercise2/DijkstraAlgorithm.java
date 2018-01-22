package exercise2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 * Dijkstra's Algorithm in finding the shortest route
 * @author zhongtian
 *
 */
public class DijkstraAlgorithm {

	private final List<Edge> edges;
	private Set<Vertex> seen;
	private Set<Vertex> unseen;
	private Map<Vertex, Vertex> links;
	private Map<Vertex, Integer> distance;
	
	/**
	 * The constructor for setting up the graph
	 * @param graph
	 */
	public DijkstraAlgorithm(Graph graph) {
		this.edges = new ArrayList<Edge>(Graph.edges);
	}

	/**
	 * The main method in executing to find the shortest route
	 * @param source
	 */
	public void execute(Vertex source) {
		seen = new HashSet<Vertex>();
		unseen = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		links = new HashMap<Vertex, Vertex>();
		distance.put(source, 0);
		unseen.add(source);
		while (unseen.size() > 0) {
			Vertex node = getMinimum(unseen);
			seen.add(node);
			unseen.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for (Vertex target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + Graph.getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + Graph.getDistance(node, target));
				links.put(target, node);
				unseen.add(target);
			}
		}
	}

	private List<Vertex> getNeighbors(Vertex v) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.vertex1.equals(v) && !isSettled(edge.vertex2)) {
				neighbors.add(edge.vertex2);
			}
		}
		return neighbors;
	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Vertex vertex) {
		return seen.contains(vertex);
	}

	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if (d != null) 
			return d;
		else 
			return Integer.MAX_VALUE;
	}

	/**
	 * Getting a vertex of the map
	 * @param v -> the vertex
	 * @return the vertex
	 */
	public Vertex get(Vertex v) {
		for (Entry<Vertex, Vertex> s : links.entrySet())
			if (s.getKey().equals(v))
				return s.getValue();
		return null;
	}

	/**
	 * Getting the path to the target
	 * @param target -> the target vertex
	 * @return the path
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex next = target;
		if (get(next) == null) {
			return null;
		}
		path.add(next);
		while (get(next) != null) {
			next = get(next);
			path.add(next);
		}
		Collections.reverse(path);
		return path;
	}

}
