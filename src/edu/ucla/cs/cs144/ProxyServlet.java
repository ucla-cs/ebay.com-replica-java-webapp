package edu.ucla.cs.cs144;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String query = request.getParameter("q");
    	
    	query = query.replaceAll("%", " ");
    	
    	String[] results = GoogleConnector.getGoogleSuggestions(request.getParameter("q"));
    	
    	StringBuilder sb = new StringBuilder();
    	
    	if(results==null||results.length==0){
    		sb.append("[]");
    	}
    	else{
    		sb.append("[");
    		for(int i=0;i<results.length;i++){
    			sb.append("\"");
    			sb.append(results[i]);
    			sb.append("\"");
    			if(i!=results.length-1)sb.append(",");
    		}
    		sb.append("]");
    	}
    	PrintWriter out = response.getWriter();
        out.println(sb);
    	
    }
}
