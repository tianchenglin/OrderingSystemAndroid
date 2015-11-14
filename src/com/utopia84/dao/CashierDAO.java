package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.utopia84.model.Cashier;

/**
 * A data access object (DAO) providing persistence and search support for
 * Cashier entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.utopia84.model.Cashier
 * @author MyEclipse Persistence Tools
 */

public class CashierDAO {
	private static final Logger log = LoggerFactory.getLogger(CashierDAO.class);

	// property constants
	public static final String CURRENT_MONEY = "currentMoney";
	public static final String INIT_MONEY = "initMoney";
	public static final String CHANGE_MONEY = "changeMoney";
	public static final String USER_CODE = "userCode";
	public static final String CASHIER_ID = "cashierId";
	public static final String STATUS = "status";

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Cashier transientInstance) {
		log.debug("saving Cashier instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Cashier persistentInstance) {
		log.debug("deleting Cashier instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Cashier findById(java.lang.Integer id) {
		log.debug("getting Cashier instance with id: " + id);
		try {
			Cashier instance = (Cashier) hibernateTemplate.get(
					"com.utopia84.model.Cashier", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Cashier instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Cashier as model where model."
					+ propertyName + "= ?";
			return hibernateTemplate.find(queryString,value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCurrentMoney(Object currentMoney) {
		return findByProperty(CURRENT_MONEY, currentMoney);
	}

	public List findByInitMoney(Object initMoney) {
		return findByProperty(INIT_MONEY, initMoney);
	}

	public List findByChangeMoney(Object changeMoney) {
		return findByProperty(CHANGE_MONEY, changeMoney);
	}

	public List findByUserCode(Object userCode) {
		return findByProperty(USER_CODE, userCode);
	}

	public List findByCashierId(Object cashierId) {
		return findByProperty(CASHIER_ID, cashierId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Cashier instances");
		try {
			String queryString = "from Cashier";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Cashier merge(Cashier detachedInstance) {
		log.debug("merging Cashier instance");
		try {
			Cashier result = (Cashier) hibernateTemplate
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Cashier instance) {
		log.debug("attaching dirty Cashier instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Cashier instance) {
		log.debug("attaching clean Cashier instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}