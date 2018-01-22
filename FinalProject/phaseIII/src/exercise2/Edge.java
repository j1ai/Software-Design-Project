package exercise2;

import java.util.ArrayList;
/**
 * This class is used for adding an edge between two vertices
 * @author zhongtian
 *
 */
public class Edge {
		protected Vertex vertex1;
		protected Vertex vertex2;
		protected int distance;
		/**
		 * 
		 * @param start -> the starting vertex
		 * @param destination -> the destination vertex
		 * @param distance -> the distance between them
		 */
		public Edge(Vertex start, Vertex destination, int distance){
			this.vertex1 = start;
			this.vertex2 = destination;
			this.distance = distance;
		}
		
		/**
		 * Checking if the given edge has the same vertices and equal distances
		 * @param e -> the edge
		 * @return true if they equal and false otherwise
		 */
		public boolean equals(Edge e){
			if (e.vertex1.equals(vertex1) && e.vertex2.equals(vertex2))
				return true;
			else
				return false;
		}
}
