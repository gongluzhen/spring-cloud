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
import com.sapit.springcloud.client.sys.OfficeClient;
import com.sapit.springcloud.client.sys.RoleClient;
import com.sapit.springcloud.client.sys.UserClient;
import com.sapit.springcloud.common.util.Collections3;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {
	@Autowired
	private RoleClient roleClient;
	@Autowired
	private OfficeClient officeClient;
	@Autowired
	private UserClient userClient;

	@ModelAttribute("role")
	public Role get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return roleClient.getById(id);
		} else {
			return new Role();
		}
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = { "list", "" })
	public String list(Role role, Model model) {
		List<Role> list = UserUtils.getRoleList();
		model.addAttribute("list", list);
		return "modules/sys/roleList";
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		if (role.getOffice() == null) {
			role.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("role", role);
		model.addAttribute("menuList", UserUtils.getMenuList());
		model.addAttribute("officeList", officeClient.findAll(UserUtils.getUser()));
		return "modules/sys/roleForm";
	}

	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "save")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, role)) {
			return form(role, model);
		}
		if (!"true".equals(checkName(role.getOldName(), role.getName()))) {
			addMessage(model, "保存角色'" + role.getName() + "'失败, 角色名已存在");
			return form(role, model);
		}
		if (!"true".equals(checkEnname(role.getOldEnname(), role.getEnname()))) {
			addMessage(model, "保存角色'" + role.getName() + "'失败, 英文名已存在");
			return form(role, model);
		}
		role.setCurrentLoginUser(UserUtils.getUser());
		
		roleClient.save(role);
		addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		return "redirect:/sys/role/?repage";
	}

	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "delete")
	public String delete(Role role, RedirectAttributes redirectAttributes) {
		roleClient.delete(role);
		addMessage(redirectAttributes, "删除角色成功");
		return "redirect:/sys/role/?repage";
	}

	/**
	 * 角色分配页面
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assign")
	public String assign(Role role, Model model) {
		List<User> userList = UserUtils.findUser(new User(new Role(role.getId())));
		model.addAttribute("userList", userList);
		return "modules/sys/roleAssign";
	}

	/**
	 * 角色分配 -- 打开角色分配对话框
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(Role role, Model model) {
		List<User> userList = UserUtils.findUser(new User(new Role(role.getId())));
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "name", ","));
		model.addAttribute("selectUserIds", Collections3.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeClient.findAll(UserUtils.getUser()));
		return "modules/sys/selectUserToRole";
	}

	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * 
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(officeId));
		Page<User> page = UserUtils.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 角色分配 -- 从角色中移除用户
	 * 
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "outrole")
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		Role role = roleClient.getById(roleId);
		User user = userClient.getById(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
		} else {
			if (user.getRoleList().size() <= 1) {
				addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
			} else {
				role.setCurrentLoginUser(UserUtils.getUser());
				Boolean flag = roleClient.outUserInRole(role, userId);
				if (!flag) {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
				} else {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
				}
			}
		}
		return "redirect:/sys/role/assign?id=" + role.getId();
	}

	/**
	 * 角色分配
	 * 
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		role.setCurrentLoginUser(UserUtils.getUser());
		
		StringBuilder msg = new StringBuilder();
		// TODO 在插入角色之前把以前的删除
		userClient.deleteAllUserRoleByRoleId(role);
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			User user = roleClient.assignUserToRole(role, idsArr[i]);
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		addMessage(redirectAttributes, "已成功分配 " + newNum + " 个用户" + msg);
		return "redirect:/sys/role/assign?id=" + role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name != null && name.equals(oldName)) {
			return "true";
		} else if (name != null && roleClient.getByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * 
	 * @param oldEnname
	 * @param enname
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname != null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname != null && roleClient.getByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}

}
