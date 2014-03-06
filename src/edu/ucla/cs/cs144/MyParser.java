package edu.ucla.cs.cs144;
/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */


import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

import org.xml.sax.*;

class MyParser {
	
	public MyParser(){}
	
	static String workspace = "./";
	
	//THIS IS FOR LOCAL TESTING PURPOSE
	//static String workspace = "/Users/eastman/Desktop/WorkspaceEclipse/Project2/src/edu/ucla/cs/cs144/";
	
	static final String columnSeparator = "|*|";
	static DocumentBuilder builder;

	Item it = new Item();
	
	public User seller = new User();
	
	//public ArrayList<User> users = new ArrayList<User>();

	public ArrayList<CatToItem> catToItems = new ArrayList<CatToItem>();

	public ArrayList<Bid> bids = new ArrayList<Bid>();

	public ItemBuyPrice itemBuyPrices = new ItemBuyPrice();
	
	static final String[] typeName = {
		"none",
		"Element",
		"Attr",
		"Text",
		"CDATA",
		"EntityRef",
		"Entity",
		"ProcInstr",
		"Comment",
		"Document",
		"DocType",
		"DocFragment",
		"Notation",
	};

	static class MyErrorHandler implements ErrorHandler {

		public void warning(SAXParseException exception)
				throws SAXException {
			fatalError(exception);
		}

		public void error(SAXParseException exception)
				throws SAXException {
			fatalError(exception);
		}

		public void fatalError(SAXParseException exception)
				throws SAXException {
			exception.printStackTrace();
			System.out.println("There should be no errors " +
					"in the supplied XML files.");
			System.exit(3);
		}

	}

	/* Non-recursive (NR) version of Node.getElementsByTagName(...)
	 */
	static Element[] getElementsByTagNameNR(Element e, String tagName) {
		Vector< Element > elements = new Vector< Element >();
		Node child = e.getFirstChild();
		while (child != null) {
			if (child instanceof Element && child.getNodeName().equals(tagName))
			{
				elements.add( (Element)child );
			}
			child = child.getNextSibling();
		}
		Element[] result = new Element[elements.size()];
		elements.copyInto(result);
		return result;
	}

	/* Returns the first subelement of e matching the given tagName, or
	 * null if one does not exist. NR means Non-Recursive.
	 */
	static Element getElementByTagNameNR(Element e, String tagName) {
		Node child = e.getFirstChild();
		while (child != null) {
			if (child instanceof Element && child.getNodeName().equals(tagName))
				return (Element) child;
			child = child.getNextSibling();
		}
		return null;
	}

	/* Returns the text associated with the given element (which must have
	 * type #PCDATA) as child, or "" if it contains no text.
	 */
	static String getElementText(Element e) {
		if (e.getChildNodes().getLength() == 1) {
			Text elementText = (Text) e.getFirstChild();
			return elementText.getNodeValue();
		}
		else
			return "";
	}

	/* Returns the text (#PCDATA) associated with the first subelement X
	 * of e with the given tagName. If no such X exists or X contains no
	 * text, "" is returned. NR means Non-Recursive.
	 */
	static String getElementTextByTagNameNR(Element e, String tagName) {
		Element elem = getElementByTagNameNR(e, tagName);
		if (elem != null)
			return getElementText(elem);
		else
			return "";
	}

	/* Returns the amount (in XXXXX.xx format) denoted by a money-string
	 * like $3,453.23. Returns the input if the input is an empty string.
	 */
	static String strip(String money) {
		if (money.equals(""))
			return money;
		else {
			double am = 0.0;
			NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
			try { am = nf.parse(money).doubleValue(); }
			catch (ParseException e) {
				System.out.println("This method should work for all " +
						"money values you find in our data.");
				System.exit(20);
			}
			nf.setGroupingUsed(false);
			return nf.format(am).substring(1);
		}
	}

	
	
	
	/* Process one items-???.xml file.
	 */
	public void processXML(String xmlString) {

		Document doc = null;
		try {
			
	        InputSource is = new InputSource(new StringReader(xmlString));
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        
			doc = builder.parse(is);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(3);
		}
		catch (SAXException e) {
			System.out.println("Parsing error on String parsing ");
			System.out.println("  (not supposed to happen with supplied XML files)");
			e.printStackTrace();
			System.exit(3);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		/* At this point 'doc' contains a DOM representation of an 'Items' XML
		 * file. Use doc.getDocumentElement() to get the root Element. */
		System.out.println("Successfully parsed -");

		/* Fill in code here (you will probably need to write auxiliary
            methods). */

		/**************************************************************/
		//RECURSIVELY PARSE THE DATA
		parseItem(doc);

		System.out.println(doc);
		
		/**************************************************************/

	}

	public static String changeDateTime(String timestamp){
		
		SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
		
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
		
		return format1.format(parsed);
	}


	

	
	
	


	public void parseItem(Node n){
		// dump out node name, type, and value


		org.w3c.dom.NodeList nlist = n.getChildNodes();

		System.out.println(nlist.getLength());
		
		if(nlist.getLength()!=1){
			return;
		}
		
		n = n.getFirstChild();
		
		
		
		nlist = n.getChildNodes();

		System.out.println(nlist.getLength());
		
		LinkedList<String> category = new LinkedList<String>();

		org.w3c.dom.NamedNodeMap nattrib = n.getAttributes();
		if(nattrib != null && nattrib.getLength() > 0)
			for(int i=0; i<nattrib.getLength(); i++){
				Node c = nattrib.item(i);
				//int cTypeNumber = n.getNodeType();
				String ctype = typeName[c.getNodeType()];
				String cname = c.getNodeName();
				String cvalue = c.getNodeValue();
				if(ctype.equals("Attr") && cname.equals("ItemID")){
					it.setItemId(Long.parseLong(cvalue));
				}
			}


		String location="ERROR";
		
		String country="ERROR";
		
		for(int i=0; i<nlist.getLength(); i++){
			Node c =nlist.item(i);

			//int cTypeNumber = n.getNodeType();
			String ctype = typeName[c.getNodeType()];
			String cname = c.getNodeName();
			String cvalue = c.getNodeValue();
			
			if(ctype.equals("Element") && cname.equals("Name")){
				
				it.setName(c.getChildNodes().item(0).getNodeValue());
			}
			else if(ctype.equals("Element" )&& cname.equals("Category")){

				CatToItem ci = new CatToItem();

				ci.setCategory(c.getChildNodes().item(0).getNodeValue());

				ci.setItemId(it.getItemId());

				catToItems.add(ci);

				category.add(c.getChildNodes().item(0).getNodeValue());
			}

			else if(ctype.equals("Element") && cname.equals("Currently")){
				
				
				it.setCurrently(Double.parseDouble(c.getChildNodes().item(0).getNodeValue().replaceAll("\\$|,", "")));
			}
			else if(ctype.equals("Element") && cname.equals("Buy_Price")){
				Double price = Double.parseDouble(c.getChildNodes().item(0).getNodeValue().replaceAll("\\$|,", ""));
				it.setBuyPrice(price);
				ItemBuyPrice ibp = new ItemBuyPrice();
				ibp.setBuyPrice(price);
				ibp.setItemId(it.getItemId());
				itemBuyPrices = ibp;
			}
			
			else if(ctype.equals("Element") && cname.equals("First_Bid")){
				it.setFirstBid(Double.parseDouble(c.getChildNodes().item(0).getNodeValue().replaceAll("\\$|,", "")));
			}
			else if(ctype.equals("Element") && cname.equals("Number_of_Bids")){
				it.setNumberOfBids(Integer.parseInt(c.getChildNodes().item(0).getNodeValue().replaceAll("\\D", "")));
			}
			else if(ctype.equals("Element") && cname.equals("Bids")){
				parseBids(c,it.getItemId());
			}
			else if(ctype.equals("Element") && cname.equals("Location")){
				location=  c.getChildNodes().item(0).getNodeValue();
			}
			else if(ctype.equals("Element") && cname.equals("Country")){
				country = c.getChildNodes().item(0).getNodeValue();
			}
			else if(ctype.equals("Element") && cname.equals("Started")){
				it.setStarted(changeDateTime(c.getChildNodes().item(0).getNodeValue()));
			}
			else if(ctype.equals("Element") && cname.equals("Ends")){
				it.setEnds(changeDateTime(c.getChildNodes().item(0).getNodeValue()));
			}
			else if(ctype.equals("Element") && cname.equals("Seller")){
				it.setSeller(parseSeller(c,location,country));
			}
			else if(ctype.equals("Element") && cname.equals("Description")){
				if(c.getChildNodes().item(0)!=null){
					it.setDescription(c.getChildNodes().item(0).getNodeValue());
				}
			}

		}

		//System.out.println(it);
	}

	private void parseBids(Node n, long l) {
		// dump out node name, type, and value
		int nTypeNumber = n.getNodeType();
		String ntype = typeName[n.getNodeType()];
		String nname = n.getNodeName();
		String nvalue = n.getNodeValue();

		org.w3c.dom.NodeList nlist = n.getChildNodes();

		for(int i=0; i<nlist.getLength(); i++){
			Node c =nlist.item(i);

			String ctype = typeName[c.getNodeType()];
			String cname = c.getNodeName();
			String cvalue = c.getNodeValue();


			if(ctype.equals("Element") && cname.equals("Bid")){


				org.w3c.dom.NodeList clist = c.getChildNodes();
				Bid b = new Bid();
				b.setItemId(l);

				for(int j=0; j<clist.getLength(); j++){
					Node d =clist.item(j);

					String dtype = typeName[d.getNodeType()];
					String dname = d.getNodeName();
					String dvalue = d.getNodeValue();


					if(dtype.equals("Element") && dname.equals("Bidder")){
						//HERE IS WHERE WE PARSE THE BIDDER
						b.setUid(parseBidder(d,b));
					}
					else if(dtype.equals("Element") && dname.equals("Time")){
						b.setTime(changeDateTime(d.getChildNodes().item(0).getNodeValue()));
					}
					else if(dtype.equals("Element") && dname.equals("Amount")){
						b.setAmount(Double.parseDouble(d.getChildNodes().item(0).getNodeValue().replaceAll("\\$|,", "")));
					}
				}
				bids.add(b);

			}
		}
	}


	private String parseBidder(Node n, Bid b) {
		// dump out node name, type, and value
		int nTypeNumber = n.getNodeType();
		String ntype = typeName[n.getNodeType()];
		String nname = n.getNodeName();
		String nvalue = n.getNodeValue();

		org.w3c.dom.NodeList nlist = n.getChildNodes();


		org.w3c.dom.NamedNodeMap nattrib = n.getAttributes();

		User u = new User();
		String name = null;
		UserLocation ul = new UserLocation();
		
		if(nattrib != null && nattrib.getLength() > 0)
			for(int i=0; i<nattrib.getLength(); i++){
				Node c = nattrib.item(i);
				//int cTypeNumber = n.getNodeType();
				String ctype = typeName[c.getNodeType()];
				String cname = c.getNodeName();
				String cvalue = c.getNodeValue();
				if(ctype.equals("Attr") && cname.equals("UserID")){
					u.setUserId(c.getChildNodes().item(0).getNodeValue());
					ul.setUserId(c.getChildNodes().item(0).getNodeValue());

					name=c.getChildNodes().item(0).getNodeValue();
				}
				if(ctype.equals("Attr") && cname.equals("Rating")){
					u.setRating(c.getChildNodes().item(0).getNodeValue());

				}
			}


		for(int i=0; i<nlist.getLength(); i++){
			Node c =nlist.item(i);

			//int cTypeNumber = n.getNodeType();
			String ctype = typeName[c.getNodeType()];
			String cname = c.getNodeName();
			String cvalue = c.getNodeValue();


			if(ctype.equals("Element") && cname.equals("Location")){
				u.setLocation(c.getChildNodes().item(0).getNodeValue());
				ul.setLocation(c.getChildNodes().item(0).getNodeValue());

			}
			else if(ctype.equals("Element" )&& cname.equals("Country")){
				u.setCountry(c.getChildNodes().item(0).getNodeValue());
				ul.setCountry(c.getChildNodes().item(0).getNodeValue());

			}
		}

		b.setU(u);
		
		
		
		if(ul.getCountry()!=null && ul.getLocation()!=null){
			u.setCountry(ul.getCountry());
			u.setLocation(ul.getLocation());
		}
		
		return name;
	}

	private  String parseSeller(Node n, String location, String country) {
		// dump out node name, type, and value
		int nTypeNumber = n.getNodeType();
		String ntype = typeName[n.getNodeType()];
		String nname = n.getNodeName();
		String nvalue = n.getNodeValue();

		org.w3c.dom.NodeList nlist = n.getChildNodes();


		org.w3c.dom.NamedNodeMap nattrib = n.getAttributes();

		User u = new User();
		UserLocation ul = new UserLocation();
		ul.setLocation(location);
		ul.setCountry(country);
		
		String name = null;

		if(nattrib != null && nattrib.getLength() > 0)
			for(int i=0; i<nattrib.getLength(); i++){
				Node c = nattrib.item(i);
				//int cTypeNumber = n.getNodeType();
				String ctype = typeName[c.getNodeType()];
				String cname = c.getNodeName();
				String cvalue = c.getNodeValue();
				if(ctype.equals("Attr") && cname.equals("UserID")){
					u.setUserId(c.getChildNodes().item(0).getNodeValue());
					ul.setUserId(c.getChildNodes().item(0).getNodeValue());
					name = c.getChildNodes().item(0).getNodeValue();
				}
				if(ctype.equals("Attr") && cname.equals("Rating")){
					u.setRating(c.getChildNodes().item(0).getNodeValue());
				}
			}

		seller = u;
		u.setLocation(location);
		u.setCountry(country);
		
		return name;
	}

	public static void main (String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java MyParser [file] [file] ...");
			System.exit(1);
		}

		/* Initialize parser. */
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringElementContentWhitespace(true);      
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new MyErrorHandler());
		}
		catch (FactoryConfigurationError e) {
			System.out.println("unable to get a document builder factory");
			System.exit(2);
		} 
		catch (ParserConfigurationException e) {
			System.out.println("parser was unable to be configured");
			System.exit(2);
		}
		/* Process all files listed on command line. */
		for (int i = 0; i < args.length; i++) {
			File currentFile = new File(args[i]);
			
			
			
			//processFile(currentFile);
		}
	}
}
