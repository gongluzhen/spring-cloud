package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysFile;
import com.sapit.springcloud.server.sys.dao.SysFileDao;

/**
 * 系统-附件表Service
 * 
 * @author gongluzhen
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class SysFileService extends CrudService<SysFileDao, SysFile> {
	public SysFile get(String id) {
		return super.get(id);
	}

	public SysFile get(SysFile sysFile) {
		return super.get(sysFile);
	}

	public List<SysFile> findList(SysFile sysFile) {
		return super.findList(sysFile);
	}

	public Page<SysFile> findPage(SysFile sysFile) {
		return super.findPage(sysFile);
	}

	@Transactional(readOnly = false)
	public SysFile save(SysFile sysFile) {
		return super.save(sysFile);
	}

	@Transactional(readOnly = false)
	public void updateBusinessKeyByIds(String businessKey, String fileIds) {
		if(StringUtils.isBlank(businessKey)){
			return;
		}
		
		super.dao.deleteByBusinessKey(businessKey);
		
		if(StringUtils.isBlank(fileIds) || fileIds.split(",").length == 0){
			return;
		}
		super.dao.updateBusinessKeyByIds(businessKey, fileIds.split(","));
	}

	@Transactional(readOnly = false)
	public void delete(SysFile sysFile) {
		super.delete(sysFile);
	}

}