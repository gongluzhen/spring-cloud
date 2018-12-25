package com.sapit.springcloud.web.modules.generate.code;

import java.util.List;

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
import com.sapit.springcloud.client.generate.code.GenTableClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTable;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/gen/genTable")
public class GenTableController extends BaseController {

	@Autowired
	private GenTableClient genTableClient;

	@Autowired
	private GenConfigClient genConfigClient;

	@ModelAttribute
	public GenTable get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return genTableClient.getById(id);
		} else {
			return new GenTable();
		}
	}

	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = { "list", "" })
	public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			genTable.setCreateBy(user);
		}
		genTable.setCurrentLoginUser(user);
		genTable.setPage(new Page<GenTable>(request, response));
		
		Page<GenTable> page = genTableClient.find(genTable);
		model.addAttribute("page", page);
		return "modules/generate/code/genTableList";
	}

	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = "form")
	public String form(GenTable genTable, Model model) {
		// 获取物理表列表
		List<GenTable> tableList = genTableClient.findTableListFormDb(new GenTable());
		model.addAttribute("tableList", tableList);
		// 验证表是否存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableClient.checkTableName(genTable.getName())) {
			addMessage(model, "下一步失败！" + genTable.getName() + " 表已经添加！");
			genTable.setName("");
		}
		// 获取物理表字段
		else {
			genTable = genTableClient.getTableFormDb(genTable);
		}
		model.addAttribute("genTable", genTable);
		model.addAttribute("config", genConfigClient.get());
		return "modules/generate/code/genTableForm";
	}

	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "save")
	public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTable)) {
			return form(genTable, model);
		}
		// 验证表是否已经存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableClient.checkTableName(genTable.getName())) {
			addMessage(model, "保存失败！" + genTable.getName() + " 表已经存在！");
			genTable.setName("");
			return form(genTable, model);
		}
		
		genTable.setCurrentLoginUser(UserUtils.getUser());
		
		genTableClient.save(genTable);
		addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
		return "redirect:/gen/genTable/?repage";
	}

	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "delete")
	public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
		genTableClient.delete(genTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:/gen/genTable/?repage";
	}

}
