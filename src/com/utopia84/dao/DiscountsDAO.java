package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Discounts;

@Component(value = "DiscountsDAO")
public class DiscountsDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DiscountsDAO.class);
	private HibernateTemplate hibernateTemplate;
	// property constants
	public static final String DISCOUNT_NAME = "discountName";
	public static final String AMOUNT = "amount";
	public static final String TYPE = "type";

	public void save(Discounts transientInstance) {
		log.debug("saving Discounts instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Discounts persistentInstance) {
		log.debug("deleting Discounts instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public boolean update(Discounts persistentInstance) {
		log.debug("deleting Discounts instance");
		try {
			hibernateTemplate.update(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			return false;
		}
	}

	public Discounts findById(java.lang.Integer id) {
		log.debug("getting Discounts instance with id: " + id);
		try {
			Discounts instance = (Discounts) hibernateTemplate.get(
					"com.utopia84.model.Discounts", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Discounts instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Discounts as model where model."
					+ propertyName + "= ?";
			return hibernateTemplate.find(queryString,value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDiscountName(Object discountName) {
		return findByProperty(DISCOUNT_NAME, discountName);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List<Discounts> findAll() {
		log.debug("finding all Discounts instances");
		System.out.println("finding all Discounts instances");
		try {
			String queryString = "from Discounts";
			return (List<Discounts>)hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Discounts merge(Discounts detachedInstance) {
		log.debug("merging Discounts instance");
		try {
			Discounts result = (Discounts) hibernateTemplate
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Discounts instance) {
		log.debug("attaching dirty Discounts instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Discounts instance) {
		log.debug("attaching clean Discounts instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}