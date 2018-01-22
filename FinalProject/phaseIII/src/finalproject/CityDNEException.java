package finalproject;

public class CityDNEException extends Exception{
	public CityDNEException(){
		super("City do not exist!!!");
	}
	
	public CityDNEException(String message){
		super(message);
	}
}
