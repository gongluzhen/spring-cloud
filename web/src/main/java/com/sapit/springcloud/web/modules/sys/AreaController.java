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
import com.sapit.springcloud.client.sys.AreaClient;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Area;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/area")
public class AreaController extends BaseController {

	@Autowired
	private AreaClient areaClient;

	@ModelAttribute("area")
	public Area get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return areaClient.getById(id);
		} else {
			return new Area();
		}
	}

	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = { "list", "" })
	public String list(Area area, Model model) {
		if (area == null || StringUtils.isBlank(area.getType())) {
			area.setTypeArray(new String[] { "0", "1" });
		}
		model.addAttribute("list", areaClient.findAllList(area));
		return "modules/sys/areaList";
	}

	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "form")
	public String form(Area area, Model model) {
		if (area.getParent() == null || area.getParent().getId() == null) {
			area.setParent(UserUtils.getUser().getOffice().getArea());
		}
		area.setParent(areaClient.getById(area.getParent().getId()));
		model.addAttribute("area", area);
		return "modules/sys/areaForm";
	}

	@RequiresPermissions("sys:area:edit")
	@RequestMapping(value = "save")
	public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, area)) {
			return form(area, model);
		}
		area.setCurrentLoginUser(UserUtils.getUser());
		
		areaClient.save(area);
		addMessage(redirectAttributes, "保存区域'" + area.getName() + "'成功");
		return "redirect:/sys/area/";
	}

	@RequiresPermissions("sys:area:edit")
	@RequestMapping(value = "delete")
	public String delete(Area area, RedirectAttributes redirectAttributes) {
		areaClient.delete(area);
		addMessage(redirectAttributes, "删除区域成功");
		return "redirect:/sys/area/";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaClient.findAll();
		for (int i = 0; i < list.size(); i++) {
			Area e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	@ResponseBody
	@RequestMapping(value = "allList")
	public List<Area> findAllList(Area area, Model model) {
		List<Area> list = areaClient.findAllList(area);
		return list;
	}
}
