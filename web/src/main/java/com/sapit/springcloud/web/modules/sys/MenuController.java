package com.sapit.springcloud.web.modules.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sapit.springcloud.client.sys.MenuClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Menu;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController {
	@Autowired
	private MenuClient menuClient;

	@ModelAttribute("menu")
	public Menu get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return menuClient.getById(id);
		} else {
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = UserUtils.getMenuList();
		Menu.sortList(list, sourcelist, Menu.getRootId(), true);
		model.addAttribute("list", list);
		return "modules/sys/menuList";
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "form")
	public String form(Menu menu, Model model) {
		if (menu.getParent() == null || menu.getParent().getId() == null) {
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(menuClient.getById(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())) {
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = UserUtils.getMenuList();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0) {
				menu.setSort(list.get(list.size() - 1).getSort() + 30);
			}
		}
		model.addAttribute("menu", menu);
		return "modules/sys/menuForm";
	}

	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "save")
	public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
		if (!UserUtils.getUser().isAdmin()) {
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能添加或修改数据！");
			return "redirect:/sys/role/?repage";
		}
		if (!beanValidator(model, menu)) {
			return form(menu, model);
		}
		menu.setCurrentLoginUser(UserUtils.getUser());
		
		menuClient.save(menu);
		addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
		return "redirect:/sys/menu/";
	}

	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "delete")
	public String delete(Menu menu, RedirectAttributes redirectAttributes) {
		menuClient.delete(menu);
		addMessage(redirectAttributes, "删除菜单成功");
		return "redirect:/sys/menu/";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "tree")
	public String tree(String parentId, Model model) {
		model.addAttribute("parentId", parentId);
		model.addAttribute("menus", UserUtils.getMenuList());
		return "modules/sys/menuTree";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(String parentId, Model model) {
		model.addAttribute("parentId", parentId);
		return "modules/sys/menuTreeselect";
	}

	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		for (int i = 0; i < ids.length; i++) {
			Menu menu = new Menu(ids[i]);
			menu.setSort(sorts[i]);
			menuClient.updateMenuSort(menu);
		}
		addMessage(redirectAttributes, "保存菜单排序成功!");
		return "redirect:/sys/menu/";
	}

	/**
	 * isShowHide是否显示隐藏菜单
	 * 
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = UserUtils.getMenuList();
		for (int i = 0; i < list.size(); i++) {
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
				if (isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")) {
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
