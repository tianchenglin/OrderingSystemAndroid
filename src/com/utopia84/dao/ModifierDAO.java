package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Modifier;

/**
 * A data access object (DAO) providing persistence and search support for
 * Modifier entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.utopia84.model.Modifier
 * @author MyEclipse Persistence Tools
 */
@Component(value = "ModifierDAO")
public class ModifierDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ModifierDAO.class);
	// property constants
	public static final String MODI_NAME = "modiName";
	public static final String CHOICE_TYPE = "choiceType";

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Modifier transientInstance) {
		log.debug("saving Modifier instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void update(Modifier transientInstance) {
		log.debug("saving Modifier instance");
		try {
			hibernateTemplate.update(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void delete(Modifier persistentInstance) {
		log.debug("deleting Modifier instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Modifier findById(java.lang.Integer id) {
		log.debug("getting Modifier instance with id: " + id);
		try {
			Modifier instance = (Modifier) hibernateTemplate.get(
					"com.utopia84.model.Modifier", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Modifier instances");
		try {
			String queryString = "from Modifier";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Modifier merge(Modifier detachedInstance) {
		log.debug("merging Modifier instance");
		try {
			Modifier result = (Modifier) hibernateTemplate
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Modifier instance) {
		log.debug("attaching dirty Modifier instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Modifier instance) {
		log.debug("attaching clean Modifier instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}