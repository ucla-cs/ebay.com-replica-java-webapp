package edu.ucla.cs.cs144;


import java.util.LinkedList;

public class CatToItem {

	private String category;
	
	private long itemId;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public String toString(){
		return itemId + ","+category;
	}
	
}
