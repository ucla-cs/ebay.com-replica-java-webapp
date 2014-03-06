package edu.ucla.cs.cs144;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {


		String id = request.getParameter("id");
		request.setAttribute("id", id);


		String sr = AuctionSearchClient.getXMLDataForItemId(id);
		
		if(sr==null ||sr.equals("")){
			response.getOutputStream().println("ITEM NOT FOUND!");
			response.getOutputStream().close();
			return;
		}
		
		MyParser p = new MyParser();
		
		p.processXML(sr);
		
		request.setAttribute("item",p.it);
		
		request.setAttribute("seller",p.seller);
		
		request.setAttribute("catToItem",p.catToItems);
		
		request.setAttribute("bid",p.bids);
		
		request.setAttribute("itemBuyPrice",p.itemBuyPrices);
		
		request.getRequestDispatcher("/ItemResult.jsp").forward(request, response);
		
    }
}
