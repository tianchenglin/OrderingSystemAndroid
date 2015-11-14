package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.utopia84.model.Ingredients;

/**
 	* A data access object (DAO) providing persistence and search support for Ingredients entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Ingredients
  * @author MyEclipse Persistence Tools 
 */

public class IngredientsDAO{
	     private static final Logger log = LoggerFactory.getLogger(IngredientsDAO.class);
		//property constants
	public static final String INGRE_NAME = "ingreName";
	public static final String INGRE_COUNT = "ingreCount";
	public static final String ITEM_ID = "itemId";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Ingredients transientInstance) {
        log.debug("saving Ingredients instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Ingredients persistentInstance) {
        log.debug("deleting Ingredients instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Ingredients findById( java.lang.Integer id) {
        log.debug("getting Ingredients instance with id: " + id);
        try {
            Ingredients instance = (Ingredients) hibernateTemplate
                    .get("com.utopia84.model.Ingredients", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Ingredients instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Ingredients as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByIngreName(Object ingreName
	) {
		return findByProperty(INGRE_NAME, ingreName
		);
	}
	
	public List findByIngreCount(Object ingreCount
	) {
		return findByProperty(INGRE_COUNT, ingreCount
		);
	}
	
	public List findByItemId(Object itemId
	) {
		return findByProperty(ITEM_ID, itemId
		);
	}
	

	public List findAll() {
		log.debug("finding all Ingredients instances");
		try {
			String queryString = "from Ingredients";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Ingredients merge(Ingredients detachedInstance) {
        log.debug("merging Ingredients instance");
        try {
            Ingredients result = (Ingredients) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Ingredients instance) {
        log.debug("attaching dirty Ingredients instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Ingredients instance) {
        log.debug("attaching clean Ingredients instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}