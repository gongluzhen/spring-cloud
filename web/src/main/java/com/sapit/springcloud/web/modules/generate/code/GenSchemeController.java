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

import com.sapit.springcloud.client.generate.code.GenConfigClient;
import com.sapit.springcloud.client.generate.code.GenSchemeClient;
import com.sapit.springcloud.client.generate.code.GenTableClient;
import com.sapit.springcloud.common.util.PropertiesUtil;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenScheme;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/gen/genScheme")
public class GenSchemeController extends BaseController {
	@Autowired
	private GenSchemeClient genSchemeClient;

	@Autowired
	private GenTableClient genTableClient;

	@Autowired
	private GenConfigClient genConfigClient;

	@ModelAttribute
	public GenScheme get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return genSchemeClient.getById(id);
		} else {
			return new GenScheme();
		}
	}

	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = { "list", "" })
	public String list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			genScheme.setCreateBy(user);
		}
		genScheme.setCurrentLoginUser(user);
		genScheme.setPage(new Page<GenScheme>(request, response));
		
		Page<GenScheme> page = genSchemeClient.find(genScheme);
		model.addAttribute("page", page);

		return "modules/generate/code/genSchemeList";
	}

	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = "form")
	public String form(GenScheme genScheme, Model model) {
		if(StringUtils.isBlank(genScheme.getProjectPath())){
			genScheme.setProjectPath(PropertiesUtil.projectPath);
		}
		if(StringUtils.isBlank(genScheme.getBasePackageName())){
			genScheme.setBasePackageName(PropertiesUtil.basePackageName);
		}
		
		model.addAttribute("genScheme", genScheme);
		model.addAttribute("config", genConfigClient.get());
		model.addAttribute("tableList", genTableClient.findAll());
		return "modules/generate/code/genSchemeForm";
	}

	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "save")
	public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genScheme)) {
			return form(genScheme, model);
		}
		genScheme.setCurrentLoginUser(UserUtils.getUser());
		
		genSchemeClient.save(genScheme);
		addMessage(redirectAttributes, "操作生成方案'" + genScheme.getName() + "'成功");
		return "redirect:/gen/genScheme/?repage";
	}

	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "delete")
	public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes) {
		genSchemeClient.delete(genScheme);
		addMessage(redirectAttributes, "删除生成方案成功");
		return "redirect:/gen/genScheme/?repage";
	}

}
