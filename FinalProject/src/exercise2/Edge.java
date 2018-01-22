package exercise2;

import java.util.ArrayList;

public class Edge {
		protected Vertex start;
		protected Vertex destionation;
		protected int distance;
		
		public Edge(Vertex start, Vertex destination, int distance){
			this.start = start;
			this.destionation = destination;
			this.distance = distance;
		}
}
