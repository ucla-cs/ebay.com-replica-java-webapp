package edu.ucla.cs.cs144;

import java.net.HttpURLConnection;

import java.net.*;

import java.io.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import org.w3c.dom.NodeList;

public class GoogleConnector {
	
	public static String[] getGoogleSuggestions(String query){
		String[] results = null;
		//HttpURLConnection huc = new HttpURLConnection();
		try{
			URL google = new URL("http://google.com/complete/search?output=toolbar&q="+query);
	        URLConnection yc = google.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        StringBuffer input=new StringBuffer();
	        String inputLine;
	
	        while ((inputLine = in.readLine()) != null) 
	            input.append(inputLine);
	        
	        in.close();
	        
	        results = XMLParser.processString(input.toString());
	       
	        System.out.println(input);
	        
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(3);
		}
		return results;
	}
	
	public static void main(String[] args){
		new GoogleConnector().getGoogleSuggestions("bob");
		
	}
	
}
