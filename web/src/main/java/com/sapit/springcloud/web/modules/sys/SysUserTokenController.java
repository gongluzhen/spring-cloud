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

import com.sapit.springcloud.client.sys.SysUserTokenClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserToken;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/sysUserToken")
public class SysUserTokenController extends BaseController {
	@Autowired
	private SysUserTokenClient sysUserTokenClient;

	@ModelAttribute
	public SysUserToken get(@RequestParam(required = false) String id) {
		SysUserToken entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = sysUserTokenClient.getById(id);
		}
		if (entity == null) {
			entity = new SysUserToken();
		}
		return entity;
	}

	@RequiresPermissions("sys:sysUserToken:view")
	@RequestMapping(value = { "list", "" })
	public String list(SysUserToken sysUserToken, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysUserToken.setCurrentLoginUser(UserUtils.getUser());
		sysUserToken.setPage(new Page<SysUserToken>(request, response));
		
		Page<SysUserToken> page = sysUserTokenClient.findPage(sysUserToken);
		model.addAttribute("page", page);
		return "modules/sys/sysUserTokenList";
	}

	@RequiresPermissions("sys:sysUserToken:view")
	@RequestMapping(value = "form")
	public String form(SysUserToken sysUserToken, Model model) {
		model.addAttribute("sysUserToken", sysUserToken);
		return "modules/sys/sysUserTokenForm";
	}

	@RequiresPermissions("sys:sysUserToken:edit")
	@RequestMapping(value = "save")
	public String save(SysUserToken sysUserToken, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserToken)) {
			return form(sysUserToken, model);
		}
		sysUserToken.setCurrentLoginUser(UserUtils.getUser());
		
		sysUserTokenClient.save(sysUserToken);
		addMessage(redirectAttributes, "保存userToken成功");
		return "redirect:/sys/sysUserToken/?repage";
	}

	@RequiresPermissions("sys:sysUserToken:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserToken sysUserToken, RedirectAttributes redirectAttributes) {
		sysUserTokenClient.delete(sysUserToken);
		addMessage(redirectAttributes, "删除userToken成功");
		return "redirect:/sys/sysUserToken/?repage";
	}

}