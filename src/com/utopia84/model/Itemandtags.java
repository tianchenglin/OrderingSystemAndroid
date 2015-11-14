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
public class Itemandtags {

	// Fields

	private Integer id;
	private Integer itemId;
	private Integer tagId;

	// Constructors

	/** default constructor */
	public Itemandtags() {
	}

	/** full constructor */
	public Itemandtags(Integer itemId, Integer taxId) {
		this.itemId = itemId;
		this.tagId = taxId;
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

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

}