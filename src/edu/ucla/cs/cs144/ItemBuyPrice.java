package edu.ucla.cs.cs144;


public class ItemBuyPrice {
	
	private long itemId;
	
	private double buyPrice;


	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public String toString(){
		return itemId + "," + buyPrice;
	}
	
}
