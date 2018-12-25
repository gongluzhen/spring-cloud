package com.sapit.springcloud.server.generate.code.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.BaseService;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenConfig;
import com.sapit.springcloud.moudle.generate.code.GenScheme;
import com.sapit.springcloud.moudle.generate.code.GenTable;
import com.sapit.springcloud.moudle.generate.code.GenTableColumn;
import com.sapit.springcloud.moudle.generate.code.GenTemplate;
import com.sapit.springcloud.server.generate.code.dao.GenSchemeDao;
import com.sapit.springcloud.server.generate.code.dao.GenTableColumnDao;
import com.sapit.springcloud.server.generate.code.dao.GenTableDao;
import com.sapit.springcloud.server.generate.code.util.GenUtils;

@Service
@Transactional(readOnly = true)
public class GenSchemeService extends BaseService {

	@Autowired
	private GenSchemeDao genSchemeDao;
	@Autowired
	private GenTableDao genTableDao;
	@Autowired
	private GenTableColumnDao genTableColumnDao;

	public GenScheme get(String id) {
		return genSchemeDao.get(id);
	}

	public Page<GenScheme> find(GenScheme genScheme) {
		genScheme.getPage().setList(genSchemeDao.findList(genScheme));
		return genScheme.getPage();
	}

	@Transactional(readOnly = false)
	public GenScheme save(GenScheme genScheme) {
		if (StringUtils.isBlank(genScheme.getId())) {
			genScheme.preInsert();
			genSchemeDao.insert(genScheme);
		} else {
			genScheme.preUpdate();
			genSchemeDao.update(genScheme);
		}
		// 生成代码
		if ("1".equals(genScheme.getFlag())) {
			generateCode(genScheme);
		}
		return genScheme;
	}

	@Transactional(readOnly = false)
	public void delete(GenScheme genScheme) {
		genSchemeDao.delete(genScheme);
	}

	private String generateCode(GenScheme genScheme) {
		StringBuilder result = new StringBuilder();

		// 查询主表及字段列
		GenTable genTable = genTableDao.get(genScheme.getGenTable().getId());
		genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));

		// 获取所有代码模板
		GenConfig config = GenUtils.getConfig();

		// 获取模板列表
		List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
		List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);

		// 如果有子表模板，则需要获取子表列表
		if (childTableTemplateList.size() > 0) {
			GenTable parentTable = new GenTable();
			parentTable.setParentTable(genTable.getName());
			genTable.setChildList(genTableDao.findList(parentTable));
		}

		// 生成子表模板代码
		for (GenTable childTable : genTable.getChildList()) {
			childTable.setParent(genTable);
			childTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
			genScheme.setGenTable(childTable);
			Map<String, Object> childTableModel = GenUtils.getDataModel(genScheme);
			for (GenTemplate tpl : childTableTemplateList) {
				result.append(GenUtils.generateToFile(tpl, childTableModel, genScheme.getReplaceFile(), genScheme.getProjectPath()));
			}
		}

		// 生成主表模板代码
		genScheme.setGenTable(genTable);
		Map<String, Object> model = GenUtils.getDataModel(genScheme);
		for (GenTemplate tpl : templateList) {
			result.append(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile(), genScheme.getProjectPath()));
		}
		return result.toString();
	}
}
