package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.utopia84.model.Stafftype;

/**
 	* A data access object (DAO) providing persistence and search support for Stafftype entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Stafftype
  * @author MyEclipse Persistence Tools 
 */

public class StafftypeDAO    {
	     private static final Logger log = LoggerFactory.getLogger(StafftypeDAO.class);
		//property constants
	public static final String TYPE_NAME = "typeName";
	public static final String PRIORITY = "priority";
	public static final String DELMARK = "delmark";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Stafftype transientInstance) {
        log.debug("saving Stafftype instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Stafftype persistentInstance) {
        log.debug("deleting Stafftype instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Stafftype findById( java.lang.Integer id) {
        log.debug("getting Stafftype instance with id: " + id);
        try {
            Stafftype instance = (Stafftype) hibernateTemplate
                    .get("com.utopia84.model.Stafftype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Stafftype instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Stafftype as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTypeName(Object typeName
	) {
		return findByProperty(TYPE_NAME, typeName
		);
	}
	
	public List findByPriority(Object priority
	) {
		return findByProperty(PRIORITY, priority
		);
	}
	
	public List findByDelmark(Object delmark
	) {
		return findByProperty(DELMARK, delmark
		);
	}
	

	public List findAll() {
		log.debug("finding all Stafftype instances");
		try {
			String queryString = "from Stafftype";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Stafftype merge(Stafftype detachedInstance) {
        log.debug("merging Stafftype instance");
        try {
            Stafftype result = (Stafftype) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Stafftype instance) {
        log.debug("attaching dirty Stafftype instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Stafftype instance) {
        log.debug("attaching clean Stafftype instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}