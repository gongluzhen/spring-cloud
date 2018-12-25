package com.sapit.springcloud.server.generate.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.service.BaseService;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTable;
import com.sapit.springcloud.moudle.generate.code.GenTableColumn;
import com.sapit.springcloud.server.generate.code.dao.GenDataBaseDictDao;
import com.sapit.springcloud.server.generate.code.dao.GenTableColumnDao;
import com.sapit.springcloud.server.generate.code.dao.GenTableDao;
import com.sapit.springcloud.server.generate.code.util.GenUtils;

@Service
@Transactional(readOnly = true)
public class GenTableService extends BaseService {

	@Autowired
	private GenTableDao genTableDao;
	@Autowired
	private GenTableColumnDao genTableColumnDao;
	@Autowired
	private GenDataBaseDictDao genDataBaseDictDao;

	public GenTable get(String id) {
		GenTable genTable = genTableDao.get(id);
		GenTableColumn genTableColumn = new GenTableColumn();
		genTableColumn.setGenTable(new GenTable(genTable.getId()));
		genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
		return genTable;
	}

	public Page<GenTable> find(GenTable genTable) {
		genTable.getPage().setList(genTableDao.findList(genTable));
		return genTable.getPage();
	}

	public List<GenTable> findAll() {
		return genTableDao.findAllList(new GenTable());
	}

	/**
	 * 获取物理数据表列表
	 * 
	 * @param genTable
	 * @return
	 */
	public List<GenTable> findTableListFormDb(GenTable genTable) {
		return genDataBaseDictDao.findTableList(genTable);
	}

	/**
	 * 验证表名是否可用，如果已存在，则返回false
	 * 
	 * @param genTable
	 * @return
	 */
	public boolean checkTableName(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			return true;
		}
		GenTable genTable = new GenTable();
		genTable.setName(tableName);
		List<GenTable> list = genTableDao.findList(genTable);
		return list.size() == 0;
	}

	/**
	 * 获取物理数据表列表
	 * 
	 * @param genTable
	 * @return
	 */
	public GenTable getTableFormDb(GenTable genTable) {
		// 如果有表名，则获取物理表
		if (StringUtils.isNotBlank(genTable.getName())) {

			List<GenTable> list = genDataBaseDictDao.findTableList(genTable);
			if (list.size() > 0) {

				// 如果是新增，初始化表属性
				if (StringUtils.isBlank(genTable.getId())) {
					genTable = list.get(0);
					// 设置字段说明
					if (StringUtils.isBlank(genTable.getComments())) {
						genTable.setComments(genTable.getName());
					}
					genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
				}

				// 添加新列
				List<GenTableColumn> columnList = genDataBaseDictDao.findTableColumnList(genTable);
				for (GenTableColumn column : columnList) {
					boolean b = false;
					for (GenTableColumn e : genTable.getColumnList()) {
						if (e.getName().equals(column.getName())) {
							b = true;
						}
					}
					if (!b) {
						genTable.getColumnList().add(column);
					}
				}

				// 删除已删除的列
				for (GenTableColumn e : genTable.getColumnList()) {
					boolean b = false;
					for (GenTableColumn column : columnList) {
						if (column.getName().equals(e.getName())) {
							b = true;
						}
					}
					if (!b) {
						e.setDelFlag(GenTableColumn.DEL_FLAG_DELETE);
					}
				}

				// 获取主键
				genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));

				// 初始化列属性字段
				GenUtils.initColumnField(genTable);

			}
		}
		return genTable;
	}

	@Transactional(readOnly = false)
	public GenTable save(GenTable genTable) {
		if (StringUtils.isBlank(genTable.getId())) {
			genTable.preInsert();
			genTableDao.insert(genTable);
		} else {
			genTable.preUpdate();
			genTableDao.update(genTable);
		}
		// 保存列
		for (GenTableColumn column : genTable.getColumnList()) {
			column.setGenTable(genTable);
			if (StringUtils.isBlank(column.getId())) {
				column.preInsert();
				genTableColumnDao.insert(column);
			} else {
				column.preUpdate();
				genTableColumnDao.update(column);
			}
		}
		return genTable;
	}

	@Transactional(readOnly = false)
	public void delete(GenTable genTable) {
		genTableDao.delete(genTable);
		genTableColumnDao.deleteByGenTableId(genTable.getId());
	}

}
