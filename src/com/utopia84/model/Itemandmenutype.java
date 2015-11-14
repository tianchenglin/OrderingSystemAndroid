package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * AbstractItemandmodi entity provides the base persistence definition of the
 * Itemandmodi entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Itemandmenutype {

	// Fields

	private Integer id;
	private Integer itemId;
	private Integer menutypeId;

	// Constructors

	/** default constructor */
	public Itemandmenutype() {
	}

	/** full constructor */
	public Itemandmenutype(Integer itemId, Integer menutypeId) {
		this.itemId = itemId;
		this.menutypeId = menutypeId;
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

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getMenutypeId() {
		return menutypeId;
	}

	public void setMenutypeId(Integer menutypeId) {
		this.menutypeId = menutypeId;
	}

}