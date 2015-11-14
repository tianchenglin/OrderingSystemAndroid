package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Tags;

/**
 	* A data access object (DAO) providing persistence and search support for Tags entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Tags
  * @author MyEclipse Persistence Tools 
 */
@Component(value = "TagsDAO")
public class TagsDAO    {
	     private static final Logger log = LoggerFactory.getLogger(TagsDAO.class);
		//property constants
	public static final String TAG_NAME = "tagName";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
	public boolean update(Tags persistentInstance) {
		log.debug("deleting Tags instance");
		try {
			hibernateTemplate.update(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			return false;
		}
	}
	
    public void save(Tags transientInstance) {
        log.debug("saving Tags instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Tags persistentInstance) {
        log.debug("deleting Tags instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Tags findById( java.lang.Integer id) {
        log.debug("getting Tags instance with id: " + id);
        try {
            Tags instance = (Tags) hibernateTemplate
                    .get("com.utopia84.model.Tags", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Tags instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Tags as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTagName(Object tagName
	) {
		return findByProperty(TAG_NAME, tagName
		);
	}
	

	public List findAll() {
		log.debug("finding all Tags instances");
		try {
			String queryString = "from Tags";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Tags merge(Tags detachedInstance) {
        log.debug("merging Tags instance");
        try {
            Tags result = (Tags) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Tags instance) {
        log.debug("attaching dirty Tags instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Tags instance) {
        log.debug("attaching clean Tags instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}