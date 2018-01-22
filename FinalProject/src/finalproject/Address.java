package finalproject;

/**
 * @author max
 *
 * This class stores shopper address information when it becomes a customer. 
 * The Address class stores the street name, city, name of customer, and customer ID.
 */
public class Address {
	String street, city, name;
	int ID;
	static int custID = 1;
	
	public Address(String street, String city, String name) {
		this.street = street;
		this.city = city;
		this.name = name;
		this.ID = custID;
		custID++;
	}
	
	public Address(String street, String city, String name, int ID) {
		this.street = street;
		this.city = city;
		this.name = name;
		this.ID = ID;
		custID++;
	}
	
	public String toString() {
/*		String rString = "";
		rString += "Street: " + this.street;
		rString += "\nCity: " + this.city;
		rString += "\nProvince: " + this.province;
		rString += "\nPostal Code: " + this.postalCode;
		rString += "\nCountry: " + this.country;
		return rString;*/
		return (street + "," + city + "," + name + "," + ID);
	}
}
