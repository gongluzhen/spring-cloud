package com.sapit.springcloud.web.modules.generate.code;

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

import com.sapit.springcloud.client.generate.code.GenTemplateClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTemplate;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/gen/genTemplate")
public class GenTemplateController extends BaseController {

	@Autowired
	private GenTemplateClient genTemplateClient;

	@ModelAttribute
	public GenTemplate get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return genTemplateClient.getById(id);
		} else {
			return new GenTemplate();
		}
	}

	@RequiresPermissions("gen:genTemplate:view")
	@RequestMapping(value = { "list", "" })
	public String list(GenTemplate genTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			genTemplate.setCreateBy(user);
		}
		genTemplate.setCurrentLoginUser(user);
		genTemplate.setPage(new Page<GenTemplate>(request, response));
		
		Page<GenTemplate> page = genTemplateClient.find(genTemplate);
		model.addAttribute("page", page);
		return "modules/gen/genTemplateList";
	}

	@RequiresPermissions("gen:genTemplate:view")
	@RequestMapping(value = "form")
	public String form(GenTemplate genTemplate, Model model) {
		model.addAttribute("genTemplate", genTemplate);
		return "modules/gen/genTemplateForm";
	}

	@RequiresPermissions("gen:genTemplate:edit")
	@RequestMapping(value = "save")
	public String save(GenTemplate genTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTemplate)) {
			return form(genTemplate, model);
		}
		genTemplate.setCurrentLoginUser(UserUtils.getUser());
		
		genTemplateClient.save(genTemplate);
		addMessage(redirectAttributes, "保存代码模板'" + genTemplate.getName() + "'成功");
		return "redirect:/gen/genTemplate/?repage";
	}

	@RequiresPermissions("gen:genTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(GenTemplate genTemplate, RedirectAttributes redirectAttributes) {
		genTemplateClient.delete(genTemplate);
		addMessage(redirectAttributes, "删除代码模板成功");
		return "redirect:/gen/genTemplate/?repage";
	}

}
