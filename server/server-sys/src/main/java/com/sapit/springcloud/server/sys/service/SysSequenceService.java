package com.sapit.springcloud.server.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysSequence;
import com.sapit.springcloud.server.sys.dao.SysSequenceDao;

@Service
@Transactional(readOnly = true)
public class SysSequenceService extends CrudService<SysSequenceDao, SysSequence> {

	public SysSequence get(String id) {
		return super.get(id);
	}

	public List<SysSequence> findList(SysSequence sysSequence) {
		return super.findList(sysSequence);
	}

	public Page<SysSequence> findPage(SysSequence sysSequence) {
		return super.findPage(sysSequence);
	}

	@Transactional(readOnly = false)
	public SysSequence save(SysSequence sysSequence) {
		return super.save(sysSequence);
	}

	/**
	 * 
	 * @Description (生成序列号)
	 * @param seqName 序列名称 e.g. user_code_seq
	 * @param prefix 前缀 e.g. 201704
	 * @param suffixNum 后缀长度 e.g. 4
	 * @return e.g. 2017040002
	 */
	@Transactional(readOnly = false)
	public String getSequenceNextValue(String seqName, String prefix, int suffixNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seq_name_p", seqName);
		param.put("prefix_p", prefix);
		param.put("suffix_num_p", suffixNum);

		this.dao.getSequenceNextValue(param);

		String result = (String) param.get("result");

		return result;
	}

	@Transactional(readOnly = false)
	public void delete(SysSequence sysSequence) {
		super.delete(sysSequence);
	}

}