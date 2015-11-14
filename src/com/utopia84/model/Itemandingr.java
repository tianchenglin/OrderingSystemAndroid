package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * AbstractArea entity provides the base persistence definition of the Area
 * entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Itemandingr {

	// Fields
	private int id;
	private int itemid;
	private int ingreid;

	// Constructors

	/** default constructor */
	public Itemandingr() {
	}

	public Itemandingr(int id, int itemid, int ingreid) {
		super();
		this.id = id;
		this.itemid = itemid;
		this.ingreid = ingreid;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public int getIngreid() {
		return ingreid;
	}

	public void setIngreid(int ingreid) {
		this.ingreid = ingreid;
	}

}