package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.utopia84.model.Desk;

/**
 * A data access object (DAO) providing persistence and search support for Desk
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.utopia84.model.Desk
 * @author MyEclipse Persistence Tools
 */

public class DeskDAO {
	private static final Logger log = LoggerFactory.getLogger(DeskDAO.class);
	// property constants
	public static final String TYPE_ID = "typeId";
	public static final String STATE = "state";
	public static final String _SACCOUNT = "SAccount";
	public static final String DESK_NAME = "deskName";
	public static final String STATETIME = "statetime";
	public static final String PEOPLE_NUM = "peopleNum";
	public static final String ROW = "row";
	public static final String COL = "col";
	public static final String DELMARK = "delmark";
	public static final String MESSAGE = "message";

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Desk transientInstance) {
		log.debug("saving Desk instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Desk persistentInstance) {
		log.debug("deleting Desk instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Desk findById(java.lang.Integer id) {
		log.debug("getting Desk instance with id: " + id);
		try {
			Desk instance = (Desk) hibernateTemplate.get(
					"com.utopia84.model.Desk", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Desk instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Desk as model where model."
					+ propertyName + "= ?";
			return hibernateTemplate.find(queryString,value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTypeId(Object typeId) {
		return findByProperty(TYPE_ID, typeId);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findBySAccount(Object SAccount) {
		return findByProperty(_SACCOUNT, SAccount);
	}

	public List findByDeskName(Object deskName) {
		return findByProperty(DESK_NAME, deskName);
	}

	public List findByStatetime(Object statetime) {
		return findByProperty(STATETIME, statetime);
	}

	public List findByPeopleNum(Object peopleNum) {
		return findByProperty(PEOPLE_NUM, peopleNum);
	}

	public List findByRow(Object row) {
		return findByProperty(ROW, row);
	}

	public List findByCol(Object col) {
		return findByProperty(COL, col);
	}

	public List findByDelmark(Object delmark) {
		return findByProperty(DELMARK, delmark);
	}

	public List findByMessage(Object message) {
		return findByProperty(MESSAGE, message);
	}

	public List findAll() {
		log.debug("finding all Desk instances");
		try {
			String queryString = "from Desk";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Desk merge(Desk detachedInstance) {
		log.debug("merging Desk instance");
		try {
			Desk result = (Desk) hibernateTemplate.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Desk instance) {
		log.debug("attaching dirty Desk instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Desk instance) {
		log.debug("attaching clean Desk instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}