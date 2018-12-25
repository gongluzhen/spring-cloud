package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.redis.CacheUtils;
import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Menu;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.dao.MenuDao;

@Service
@Transactional(readOnly = true)
public class MenuService extends CrudService<MenuDao, Menu> {

	public Menu getMenu(String id) {
		return super.dao.get(id);
	}

	public List<Menu> findAll(User currentLoginUser) {
		List<Menu> menuList = null;
		if (currentLoginUser.isAdmin()) {
			menuList = super.dao.findAllList(new Menu());
		} else {
			Menu m = new Menu();
			m.setUserId(currentLoginUser.getId());
			menuList = super.dao.findByUserId(m);
		}
		return menuList;
	}

	@Transactional(readOnly = false)
	public Menu save(Menu menu) {
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();

		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())) {
			menu.preInsert();
			super.dao.insert(menu);
		} else {
			menu.preUpdate();
			super.dao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%," + menu.getId() + ",%");
		List<Menu> list = super.dao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			super.dao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		CacheUtils.remove(Constants.CACHE_MENU_LIST);
		CacheUtils.remove(Constants.CACHE_MENU_NAME_PATH_MAP);
		
		return menu;
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		super.dao.updateSort(menu);
		// 清除用户菜单缓存
		CacheUtils.remove(Constants.CACHE_MENU_LIST);
		CacheUtils.remove(Constants.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		super.dao.delete(menu);
		// 清除用户菜单缓存
		CacheUtils.remove(Constants.CACHE_MENU_LIST);
		CacheUtils.remove(Constants.CACHE_MENU_NAME_PATH_MAP);
	}
	
	public List<Menu> findAllList(Menu menu) {
		return dao.findAllList(menu);
	}
	
	public List<Menu> findByUserId(Menu menu){
		return dao.findByUserId(menu);
	}
}