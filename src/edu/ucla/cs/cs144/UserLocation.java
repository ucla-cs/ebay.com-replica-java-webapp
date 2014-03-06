package edu.ucla.cs.cs144;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLocation {

	private String userId;
		
	private String location;
	
	private String country;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static void main(String[] args){

		String timestamp = "Nov-06-1990 06:54:35";
		timestamp = timestamp.trim();

		SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
		Date parsed = null;
		try {
			parsed = format.parse(timestamp);
			//System.out.println(" 2. " + parsed.toString());
		}
		
		catch(ParseException pe) {
			System.out.println("ERROR: Cannot parse \"" + timestamp + "\"");
			System.exit(0);
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(format1.format(parsed));
	}
	
	
	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String toString(){
		return userId+","+location+","+country;
	}
}
