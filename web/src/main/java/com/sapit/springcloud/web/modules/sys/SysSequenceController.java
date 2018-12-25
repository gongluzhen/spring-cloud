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

import com.sapit.springcloud.client.sys.SysSequenceClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysSequence;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/sysSequence")
public class SysSequenceController extends BaseController {
	@Autowired
	private SysSequenceClient sysSequenceClient;

	@ModelAttribute
	public SysSequence get(@RequestParam(required = false) String id) {
		SysSequence entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = sysSequenceClient.getById(id);
		}
		if (entity == null) {
			entity = new SysSequence();
		}
		return entity;
	}

	@RequiresPermissions("sys:sysSequence:view")
	@RequestMapping(value = { "list", "" })
	public String list(SysSequence sysSequence, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysSequence.setCurrentLoginUser(UserUtils.getUser());
		sysSequence.setPage(new Page<SysSequence>(request, response));
		
		Page<SysSequence> page = sysSequenceClient.findPage(sysSequence);
		model.addAttribute("page", page);
		return "modules/sys/sysSequenceList";
	}

	@RequiresPermissions("sys:sysSequence:view")
	@RequestMapping(value = "form")
	public String form(SysSequence sysSequence, Model model) {
		model.addAttribute("sysSequence", sysSequence);
		return "modules/sys/sysSequenceForm";
	}

	@RequiresPermissions("sys:sysSequence:edit")
	@RequestMapping(value = "save")
	public String save(SysSequence sysSequence, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysSequence)) {
			return form(sysSequence, model);
		}
		sysSequence.setCurrentLoginUser(UserUtils.getUser());
		
		sysSequenceClient.save(sysSequence);
		addMessage(redirectAttributes, "保存序列成功");
		return "redirect:/sys/sysSequence/?repage";
	}

	@RequiresPermissions("sys:sysSequence:edit")
	@RequestMapping(value = "delete")
	public String delete(SysSequence sysSequence, RedirectAttributes redirectAttributes) {
		sysSequenceClient.delete(sysSequence);
		addMessage(redirectAttributes, "删除序列成功");
		return "redirect:/sys/sysSequence/?repage";
	}

}