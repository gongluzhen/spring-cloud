package com.sapit.springcloud.server.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.DateUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Log;
import com.sapit.springcloud.server.sys.dao.LogDao;

@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {

	public Page<Log> findPage(Log log) {
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}

		return super.findPage(log);

	}

	@Transactional(readOnly = false)
	public Log insert(Log entity) {
		entity.preInsert();
		super.dao.insert(entity);
		return entity;
	}

}
