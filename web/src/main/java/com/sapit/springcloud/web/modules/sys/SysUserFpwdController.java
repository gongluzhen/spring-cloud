package com.sapit.springcloud.web.modules.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sapit.springcloud.client.sys.SysUserFpwdClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserFpwd;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

/**
 * 会员信息忘记密码表Controller
 *
 * @author YJH
 * @version 2017-06-13
 */
@Controller
@RequestMapping(value = "/sys/sysUserFpwd")
public class SysUserFpwdController extends BaseController {
	@Autowired
	private SysUserFpwdClient sysUserFpwdClient;

	@ModelAttribute
	public SysUserFpwd get(@RequestParam(required = false) String id) {
		SysUserFpwd entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = sysUserFpwdClient.getById(id);
		}
		if (entity == null) {
			entity = new SysUserFpwd();
		}
		return entity;
	}

	@RequiresPermissions("sys:sysUserFpwd:view")
	@RequestMapping(value = { "list", "" })
	public String list(SysUserFpwd sysUserFpwd, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysUserFpwd.setCurrentLoginUser(UserUtils.getUser());
		sysUserFpwd.setPage(new Page<SysUserFpwd>(request, response));
		
		Page<SysUserFpwd> page = sysUserFpwdClient.findPage(sysUserFpwd);
		model.addAttribute("page", page);
		return "modules/sys/sysUserFpwdList";
	}

	@RequiresPermissions("sys:sysUserFpwd:view")
	@RequestMapping(value = "form")
	public String form(SysUserFpwd sysUserFpwd, Model model) {
		model.addAttribute("sysUserFpwd", sysUserFpwd);
		return "modules/sys/sysUserFpwdForm";
	}

	@RequiresPermissions("sys:sysUserFpwd:edit")
	@RequestMapping(value = "save")
	public String save(SysUserFpwd sysUserFpwd, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserFpwd)) {
			return form(sysUserFpwd, model);
		}
		sysUserFpwd.setCurrentLoginUser(UserUtils.getUser());
		
		sysUserFpwdClient.save(sysUserFpwd);
		addMessage(redirectAttributes, "保存会员信息忘记密码表成功");
		return "redirect:/sys/sysUserFpwd/?repage";
	}

	@RequiresPermissions("sys:sysUserFpwd:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserFpwd sysUserFpwd, RedirectAttributes redirectAttributes) {
		sysUserFpwdClient.delete(sysUserFpwd);
		addMessage(redirectAttributes, "删除会员信息忘记密码表成功");
		return "redirect:/sys/sysUserFpwd/?repage";
	}

}