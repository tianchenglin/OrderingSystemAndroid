package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.TagsDAO;
import com.utopia84.model.Tags;

@Component(value = "tagsService")
public class TagsService {
	@Resource(name="TagsDAO")
	private TagsDAO tagsDao;

	public void tagsAdd(int id ,Tags tags) {
		//如果id为0，则代表数据库中没有此记录
		if(id==0){//
			tagsDao.save(tags);
		}else{
			tags.setId(id);
			tagsDao.update(tags);
		}
		
	}
	public List<Tags> getAllTags() {
		return tagsDao.findAll();
	}
	
	public Tags tagsDelete(int id) {
		Tags tags = tagsDao.findById(id);
		tagsDao.delete(tags);
		return tags;
	}

	public boolean tagsUpdate(Tags tags) {
		return tagsDao.update(tags);
	}
	
	
	public TagsDAO getTagsDao() {
		return tagsDao;
	}
	public void setTagsDao(TagsDAO tagsDao) {
		this.tagsDao = tagsDao;
	}
	public void update(int tagsid, int length) {
		Tags tags = tagsDao.findById(tagsid);
		tags.setInclude(length);
		tagsDao.update(tags);
	}
}
