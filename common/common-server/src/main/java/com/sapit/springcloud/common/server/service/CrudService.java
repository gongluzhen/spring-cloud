package com.sapit.springcloud.common.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.util.BeanUtils;
import com.sapit.springcloud.moudle.DataEntity;
import com.sapit.springcloud.moudle.Page;

/**
 * Service基类
 * @author sapit
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询分页数据
     * @param page 分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(T entity) {
    	entity.getPage().setList(dao.findList(entity));
        return entity.getPage();
    }

    /**
     * 保存数据（插入或更新）
     * @param entity
     */
    @Transactional(readOnly = false)
    public T save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();

            T tOld = dao.get(entity);
            BeanUtils.copyPropertiesIgnoreNull(entity, tOld);

            dao.update(tOld);
        }
        return entity;
    }

    /**
     * 删除数据
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

}
