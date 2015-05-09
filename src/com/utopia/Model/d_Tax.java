package com.utopia.Model;

public class d_Tax {
	private int tax_id ; 
	private String tax_name; 
	private double tax_value ;
	
	public d_Tax() {
		super();
	}
	
	public d_Tax(int tax_id , String tax_name , double tax_value) {
		super();
		this.tax_id = tax_id;
		this.tax_name = tax_name;
		this.tax_value = tax_value;
	}
	public int getTax_id() {
		return tax_id;
	}
	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}
	public String getTax_name() {
		return tax_name;
	}
	public void setTax_name(String tax_name) {
		this.tax_name = tax_name;
	}
	public double getTax_value() {
		return tax_value;
	}
	public void setTax_value(double tax_value) {
		this.tax_value = tax_value;
	} 
	
}
