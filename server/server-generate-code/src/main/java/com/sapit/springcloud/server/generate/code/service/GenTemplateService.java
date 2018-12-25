package com.sapit.springcloud.server.generate.code.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.BaseService;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTemplate;
import com.sapit.springcloud.server.generate.code.dao.GenTemplateDao;

@Service
@Transactional(readOnly = true)
public class GenTemplateService extends BaseService {

	@Autowired
	private GenTemplateDao genTemplateDao;
	
	public GenTemplate get(String id) {
		return genTemplateDao.get(id);
	}
	
	public Page<GenTemplate> find(GenTemplate genTemplate) {
		genTemplate.getPage().setList(genTemplateDao.findList(genTemplate));
		return genTemplate.getPage();
	}
	
	@Transactional(readOnly = false)
	public GenTemplate save(GenTemplate genTemplate) {
		if (genTemplate.getContent()!=null){
			genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
		}
		if (StringUtils.isBlank(genTemplate.getId())){
			genTemplate.preInsert();
			genTemplateDao.insert(genTemplate);
		}else{
			genTemplate.preUpdate();
			genTemplateDao.update(genTemplate);
		}
		return genTemplate;
	}
	
	@Transactional(readOnly = false)
	public void delete(GenTemplate genTemplate) {
		genTemplateDao.delete(genTemplate);
	}
	
}
