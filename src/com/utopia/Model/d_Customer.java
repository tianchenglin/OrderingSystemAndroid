package com.utopia.Model;

public class d_Customer {
	private int id;
	private int customNo; 
	private int ItemNo ;   
	
	
	
	public d_Customer() {
		super();
	}
 

	public d_Customer(int id, int customNo, int itemNo ) {
		super();
		this.id = id;
		this.customNo = customNo; 
	}


 
	public int getId() {
		return id;
	}

	public int getItemNo() {
		return ItemNo;
	}

	public void setItemNo(int itemNo) {
		ItemNo = itemNo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomNo() {
		return customNo;
	}

	public void setCustomNo(int customNo) {
		this.customNo = customNo;
	}

 
}