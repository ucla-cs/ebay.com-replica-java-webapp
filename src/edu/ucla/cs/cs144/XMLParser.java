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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class XMLParser {
   
   static final String columnSeparator = "|*|";
   static DocumentBuilder builder;
   
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
   static String[] processString(String xmlString) {

		Document doc = null;
		try {
			System.out.println(xmlString);
	        InputSource is = new InputSource(new StringReader(xmlString));
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
            
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
       
       /* Fill in code here (you will probably need to write auxiliary
           methods). */

       String[] results =nodes(doc);
       

       return results;
       
       //rrecursiveDescent(doc, 0);
   }
   
   public static String[] nodes(Document doc){
//	 XMLParser.getElementByTagNameNR(, "toplevel");
       //NodeList l =doc.getElementsByTagName("CompleteSuggestion");
       System.out.println(doc);
       System.out.println(doc.getDocumentElement());
       //Element r = getElementByTagNameNR(doc., "toplevel");
       
       Node top = doc.getFirstChild();

       org.w3c.dom.NodeList nlist = top.getChildNodes();
       
       
       
	   
	   String[] results = new String[nlist.getLength()];
	   
	   for(int i=0;i<nlist.getLength();i++){
		   Node cur = nlist.item(i);
		   Node suggestion = cur.getFirstChild();
		   String value = suggestion.getAttributes().item(0).getNodeValue();
		   results[i]=value;
	   }
	   
       return results;
   }
   
}
