package com.sapit.springcloud.web.modules.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sapit.springcloud.client.sys.RoleClient;
import com.sapit.springcloud.client.sys.SysUserFpwdClient;
import com.sapit.springcloud.client.sys.UserClient;
import com.sapit.springcloud.common.util.CheckMobile;
import com.sapit.springcloud.common.util.DateUtils;
import com.sapit.springcloud.common.util.PropertiesUtil;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.SysUserFpwd;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.moudle.util.excel.ExportExcel;
import com.sapit.springcloud.moudle.util.excel.ImportExcel;
import com.sapit.springcloud.web.common.beanvalidator.BeanValidators;
import com.sapit.springcloud.web.common.beanvalidator.SysUser;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

/**
 * 用户Controller
 *
 * @author panasonic
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {
	@Autowired
	private UserClient userClient;
	@Autowired
	private RoleClient roleClient;
	@Autowired
	private SysUserFpwdClient sysUserFpwdClient;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return userClient.getById(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "index" })
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequestMapping(value = { "list", "" })
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = UserUtils.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@ResponseBody
	@RequestMapping(value = { "listData" })
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = UserUtils.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}

		model.addAttribute("user", user);

		Role roleParam = new Role();
		model.addAttribute("allRoles", roleClient.findAllList(roleParam));
		return "modules/sys/userForm";
	}

	@RequiresPermissions("ei:member:informationView")
	@RequestMapping(value = "informationView")
	public String informationView(User user, Model model) {
		model.addAttribute("user", user);

		return "modules/ei/memberInformationView";
	}

	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(userClient.entryptPassword(user.getNewPassword()));
			user.setUpdatePasswdDate(new Date()); // 存储密码修改日期
		}
		if (!beanValidator(model, user, SysUser.class)) {
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
			user.setOldLoginName("");
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		user.setCurrentLoginUser(UserUtils.getUser());

		userClient.save(user);
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:/sys/user/list?repage";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if (UserUtils.getUser().getId().equals(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		} else if (User.isAdmin(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			userClient.delete(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<User> page = UserUtils.findUser(new Page<User>(request, response, -1), user);
			if(page != null && CollectionUtils.isNotEmpty(page.getList())){
				for(User u : page.getList()){
					u.setRoleList(roleClient.findList(new Role(u)));
				}
			}
			new ExportExcel("用户数据", User.class, 1).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:/sys/user/list?repage";
	}

	/**
	 * 导入用户数据
	 *
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list) {
				try {
					if ("true".equals(checkLoginName("", user.getLoginName()))) {
						user.setPassword(userClient.entryptPassword(PropertiesUtil.defaultPassword));
						user.setCurrentLoginUser(UserUtils.getUser());
						
						BeanValidators.validateWithException(validator, user);
						
						userClient.save(user);
						
						successNum++;
					} else {
						failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, "");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:/sys/user/list?repage";
	}

	/**
	 * 下载导入用户数据模板
	 *
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(new User());
			new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:/sys/user/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 *
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null && userClient.getByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 *
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			if (currentUser.equals(user)) {
				model.addAttribute("message", "您并没有做更改， 无需保存~");
			} else {
				currentUser.setEmail(user.getEmail());
				currentUser.setPhone(user.getPhone());
				currentUser.setMobile(user.getMobile());
				currentUser.setRemarks(user.getRemarks());
				currentUser.setPhoto(user.getPhoto());
				currentUser.setFileIds(user.getFileIds());
				currentUser.setCurrentLoginUser(UserUtils.getUser());
				
				userClient.updateUserInfo(currentUser);
				model.addAttribute("message", "保存用户信息成功");
			}
		}
		model.addAttribute("user", currentUser);

		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 *
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}

	/**
	 * 修改个人用户密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			if (userClient.validatePassword(oldPassword, user.getPassword())) {
				userClient.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = userClient.findByOfficeId(officeId);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	@ResponseBody
	@RequestMapping(value = "checkPasswdModDate")
	public Map<String, Object> checkPasswdModDate(@RequestParam String username) {
		Map<String, Object> result = UserUtils.getCheckPasswdModDate(username);
		return result;
	}

	/**
	 * 跳转到用户详情页面
	 *
	 * @return userDetails.jsp
	 * @author YJH
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "userDetails")
	public String userDetails(User user, Model model) {
		model.addAttribute("user", user);
		return "modules/sys/userDetails";
	}

	/**
	 * 跳转到找回密码页面
	 *
	 * @param user
	 *            User
	 * @param model
	 *            Model
	 * @return retrievalPasswordForm.jsp
	 */
	@RequestMapping(value = "memberRegister/retrievalPassword")
	public String retrievalPassword(User user, Model model) {
		return "modules/sys/retrievalPasswordForm";
	}

	/**
	 * 重置密码保存
	 *
	 * @param user
	 *            User
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @return 登录页面
	 * @author YJH
	 */
	@RequestMapping(value = "memberRegister/resetPasswordSave")
	public String resetPasswordSave(User user, RedirectAttributes redirectAttributes) {
		userClient.updatePasswordById(user.getId(), user.getLoginName(), user.getPassword());
		sysUserFpwdClient.deleteByUserId(user); // 当用户重置完密码之后，要把此条记录从忘记密码表中逻辑删除，防止用户在次点开链接，继续修改密码
		addMessage(redirectAttributes, "修改密码成功！请重新登录！");
		return "redirect:/sys/user/memberRegister/retrievalPassword?repage";
	}

	/**
	 * 邮件跳转找回密码
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "fpwd")
	public String forgotPassword(String token, HttpServletRequest request, Model model) {
		// 检测
		SysUserFpwd sysUserFpwd = sysUserFpwdClient.checkToken(token);
		if (sysUserFpwd == null) {
			return "";
		}
		boolean isFromMobile = false;
		try {
			// 获取ua，用来判断是否为移动端访问
			String userAgent = request.getHeader("USER-AGENT").toLowerCase();
			if (null == userAgent) {
				userAgent = "";
			}
			isFromMobile = CheckMobile.check(userAgent);
			// 判断是否为移动端访问
			if (isFromMobile) {
				System.out.println("移动");
				return "redirect:/static/wechat/changePwd.html?id=" + sysUserFpwd.getUser().getId();
			} else {
				System.out.println("PC");
				User user = userClient.getById(sysUserFpwd.getUser().getId());
				model.addAttribute("user", user);
				return "modules/sys/resetPasswordForm";
			}
		} catch (Exception e) {

		}
		return "";
	}

}
