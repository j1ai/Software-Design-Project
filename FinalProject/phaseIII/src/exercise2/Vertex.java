package exercise2;

import java.util.ArrayList;
/**
 * The vertex class
 * @author zhongtian
 *
 */
public class Vertex {
	public String city;

	/**
	 * The constructor setting up the vertex with the given city
	 * @param city
	 */
	public Vertex(String city){
		this.city=city;
	}
	
	/**
	 * If the vertex equals another
	 * @param v -> the other city
	 * @return true is equals false otherwise
	 */
	public boolean equals(Vertex v){
		if (v.city.equals(this.city))
			return true;
		else
			return false;
	}
	
	/**
	 * Tostring method printing the city
	 */
	public String toString(){
		return city;
	}
}
