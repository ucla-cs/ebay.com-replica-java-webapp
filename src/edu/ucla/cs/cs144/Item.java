package edu.ucla.cs.cs144;

import java.util.LinkedList;

public class Item {
	
	public Item(){
	}
	
	public Item(long itemId, String name, 
			int currently, int firstBid, int numberOfBids, String location,
			String country, String started, String ends, String seller, double buyPrice) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.currently = currently;
		this.firstBid = firstBid;
		this.numberOfBids = numberOfBids;
		this.started = started;
		this.ends = ends;
		this.seller = seller;
		this.description=description;
		this.buyPrice = buyPrice;
	}

	private long itemId =-1;
    
	private String name; 
    
	private LinkedList<String> category = new LinkedList<String>();
    
	private double currently =-1;
    
	private double firstBid= -1;
    
	private int numberOfBids= -1;
    
	private String started;

	private String ends;

	private String seller;

	private String description;
	
	private double buyPrice;

	
	public static void main(String[] args){
		String s = "$124,459.55";
		System.out.println(Double.parseDouble(s.replaceAll("\\$|,", "")));
		
	}
	
	
	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public void setCurrently(double currently) {
		this.currently = currently;
	}

	public void setFirstBid(double firstBid) {
		this.firstBid = firstBid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<String> getCategory() {
		return category;
	}

	public void setCategory(LinkedList<String> category) {
		this.category = category;
	}

	public double getCurrently() {
		return currently;
	}

	public void setCurrently(int currently) {
		this.currently = currently;
	}

	public double getFirstBid() {
		return firstBid;
	}

	public void setFirstBid(int firstBid) {
		this.firstBid = firstBid;
	}

	public int getNumberOfBids() {
		return numberOfBids;
	}

	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}


	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

	public String getEnds() {
		return ends;
	}

	public void setEnds(String ends) {
		this.ends = ends;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public String toString(){
		
		return itemId +","+ name+","+ currently  +","+
				firstBid+","+  numberOfBids+","+ started+","+ ends+","+ seller+","+ description;
	}
	
}
