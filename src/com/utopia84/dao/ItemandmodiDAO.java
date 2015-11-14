package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Itemandmodi;

/**
 	* A data access object (DAO) providing persistence and search support for Itemandmodi entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Itemandmodi
  * @author MyEclipse Persistence Tools 
 */
@Component(value = "ItemandmodiDAO")
public class ItemandmodiDAO{
	     private static final Logger log = LoggerFactory.getLogger(ItemandmodiDAO.class);
		//property constants
	public static final String ITEM_ID = "itemId";
	public static final String MODI_ID = "modiId";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Itemandmodi transientInstance) {
        log.debug("saving Itemandmodi instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(int id) {
        log.debug("deleting Itemandmodi instance");
        try {
        	hibernateTemplate.bulkUpdate("delete from Itemandmodi where modiId="+id);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Itemandmodi findById( java.lang.Integer id) {
        log.debug("getting Itemandmodi instance with id: " + id);
        try {
            Itemandmodi instance = (Itemandmodi) hibernateTemplate
                    .get("com.utopia84.model.Itemandmodi", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
 
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Itemandmodi instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Itemandmodi as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByItemId(Object itemId
	) {
		return findByProperty(ITEM_ID, itemId
		);
	}
	
	public List findByModiId(Object modiId
	) {
		return findByProperty(MODI_ID, modiId
		);
	}
	

	public List findAll() {
		log.debug("finding all Itemandmodi instances");
		try {
			String queryString = "from Itemandmodi";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Itemandmodi merge(Itemandmodi detachedInstance) {
        log.debug("merging Itemandmodi instance");
        try {
            Itemandmodi result = (Itemandmodi) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Itemandmodi instance) {
        log.debug("attaching dirty Itemandmodi instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Itemandmodi instance) {
        log.debug("attaching clean Itemandmodi instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}