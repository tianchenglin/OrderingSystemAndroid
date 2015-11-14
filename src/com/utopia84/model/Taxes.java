package com.utopia84.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * AbstractTaxes entity provides the base persistence definition of the Taxes
 * entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Taxes {

	// Fields

	private Integer id;
	private String taxeName;
	private Float rate;
	private Short includeTaxesInPrice;
	private Short assignTaxToAllItems;
	
	private int include;

	// Constructors

	/** default constructor */
	public Taxes() {
	}
	public Taxes( String taxeName, Float rate) { 
		this.taxeName = taxeName;
		this.rate = rate;
	}
	public Taxes(int id , String taxeName, Float rate) {
		this.id = id ; 
		this.taxeName = taxeName;
		this.rate = rate;
	}

	/** full constructor */
	public Taxes(String taxeName, Float rate, Short includeTaxesInPrice,
			Short assignTaxToAllItems) {
		this.taxeName = taxeName;
		this.rate = rate;
		this.includeTaxesInPrice = includeTaxesInPrice;
		this.assignTaxToAllItems = assignTaxToAllItems;
	}

	@Column(name="include",columnDefinition="INT(11) default 0")
	public int getInclude() {
		return include;
	}
	
	public void setInclude(int include) {
		this.include = include;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaxeName() {
		return this.taxeName;
	}

	public void setTaxeName(String taxeName) {
		this.taxeName = taxeName;
	}

	public Float getRate() {
		return this.rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Short getIncludeTaxesInPrice() {
		return this.includeTaxesInPrice;
	}

	public void setIncludeTaxesInPrice(Short includeTaxesInPrice) {
		this.includeTaxesInPrice = includeTaxesInPrice;
	}

	public Short getAssignTaxToAllItems() {
		return this.assignTaxToAllItems;
	}

	public void setAssignTaxToAllItems(Short assignTaxToAllItems) {
		this.assignTaxToAllItems = assignTaxToAllItems;
	}
}