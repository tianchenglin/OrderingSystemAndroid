package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Menutype;

/**
 	* A data access object (DAO) providing persistence and search support for Menutype entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Menutype
  * @author MyEclipse Persistence Tools 
 */
@Component(value = "MenutypeDAO")
public class MenutypeDAO   {
	     private static final Logger log = LoggerFactory.getLogger(MenutypeDAO.class);
		//property constants
	public static final String TYPE_NAME = "typeName";
	public static final String TYPE_PARENT_ID = "typeParentId";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Menutype transientInstance) {
        log.debug("saving Menutype instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Menutype persistentInstance) {
        log.debug("deleting Menutype instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Menutype findById( String id) {
        log.debug("getting Menutype instance with id: " + id);
        try {
            Menutype instance = (Menutype) hibernateTemplate
                    .get("com.utopia84.model.Menutype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Menutype instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Menutype as model where model." 
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
	
	public List findByTypeParentId(Object typeParentId
	) {
		return findByProperty(TYPE_PARENT_ID, typeParentId
		);
	}
	
	public boolean update(Menutype persistentInstance) {
		try {
			hibernateTemplate.update(persistentInstance);
			return true;
		} catch (RuntimeException re) {
			return false;
		}
	}
	
	public List findAll() {
		log.debug("finding all Menutype instances");
		try {
			String queryString = "from Menutype";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Menutype merge(Menutype detachedInstance) {
        log.debug("merging Menutype instance");
        try {
            Menutype result = (Menutype) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Menutype instance) {
        log.debug("attaching dirty Menutype instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Menutype instance) {
        log.debug("attaching clean Menutype instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}