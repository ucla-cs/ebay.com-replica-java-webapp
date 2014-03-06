package edu.ucla.cs.cs144;


public class Bid {
	
	private String uid;
	
	private long itemId;
	
	
	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}


	private String time;
	
	private Double amount;

	private User u;
	
	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	public String toString(){
		return uid + ","+ itemId +","+ time +","+amount;
	}
}
