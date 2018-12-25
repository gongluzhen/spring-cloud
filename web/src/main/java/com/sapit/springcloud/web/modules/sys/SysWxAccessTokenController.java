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

import com.sapit.springcloud.client.sys.SysWxAccessTokenClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysWxAccessToken;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/sysWxAccessToken")
public class SysWxAccessTokenController extends BaseController {
	@Autowired
	private SysWxAccessTokenClient sysWxAccessTokenClient;

	@ModelAttribute
	public SysWxAccessToken get(@RequestParam(required = false) String id) {
		SysWxAccessToken entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = sysWxAccessTokenClient.getById(id);
		}
		if (entity == null) {
			entity = new SysWxAccessToken();
		}
		return entity;
	}

	@RequiresPermissions("sys:sysWxAccessToken:view")
	@RequestMapping(value = { "list", "" })
	public String list(SysWxAccessToken sysWxAccessToken, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysWxAccessToken.setCurrentLoginUser(UserUtils.getUser());
		sysWxAccessToken.setPage(new Page<SysWxAccessToken>(request, response));
		
		Page<SysWxAccessToken> page = sysWxAccessTokenClient.findPage(sysWxAccessToken);
		model.addAttribute("page", page);
		return "modules/sys/sysWxAccessTokenList";
	}

	@RequiresPermissions("sys:sysWxAccessToken:view")
	@RequestMapping(value = "form")
	public String form(SysWxAccessToken sysWxAccessToken, Model model) {
		model.addAttribute("sysWxAccessToken", sysWxAccessToken);
		return "modules/sys/sysWxAccessTokenForm";
	}

	@RequiresPermissions("sys:sysWxAccessToken:edit")
	@RequestMapping(value = "save")
	public String save(SysWxAccessToken sysWxAccessToken, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysWxAccessToken)) {
			return form(sysWxAccessToken, model);
		}
		sysWxAccessToken.setCurrentLoginUser(UserUtils.getUser());
		
		sysWxAccessTokenClient.save(sysWxAccessToken);
		addMessage(redirectAttributes, "保存wx成功");
		return "redirect:/sys/sysWxAccessToken/?repage";
	}

	@RequiresPermissions("sys:sysWxAccessToken:edit")
	@RequestMapping(value = "delete")
	public String delete(SysWxAccessToken sysWxAccessToken, RedirectAttributes redirectAttributes) {
		sysWxAccessTokenClient.delete(sysWxAccessToken);
		addMessage(redirectAttributes, "删除wx成功");
		return "redirect:/sys/sysWxAccessToken/?repage";
	}

}