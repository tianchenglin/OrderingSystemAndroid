package com.utopia84.dao;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Itemandmenutype;

/**
 * A data access object (DAO) providing persistence and search support for
 * Itemandtax entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.utopia84.model.Itemandtax
 * @author MyEclipse Persistence Tools
 */
@Component(value = "ItemandmenutypeDAO")
public class ItemandmenutypeDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ItemandmenutypeDAO.class);

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Itemandmenutype transientInstance) {
		log.debug("saving Itemandmenutype instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Itemandmenutype persistentInstance) {
		log.debug("deleting Itemandmenutype instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	

    public Itemandmenutype findById( java.lang.Integer id) {
        log.debug("getting Itemandmenutype instance with id: " + id);
        try {
        	Itemandmenutype instance = (Itemandmenutype) hibernateTemplate
                    .get("com.utopia84.model.Itemandmenutype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

	public void delete(int id) {
		hibernateTemplate.bulkUpdate("delete from Itemandmenutype where menutypeId="+id);
	}
}