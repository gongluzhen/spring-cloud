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
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.web.common.utils.DictUtils;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/office")
public class OfficeController extends BaseController {
    @Autowired
    private OfficeClient officeClient;

    @ModelAttribute("office")
    public Office get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            Office office =  officeClient.getById(id);
            if(office == null){
                return new Office();
            }
            return office;
        } else {
            return new Office();
        }
    }

    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = {""})
    public String index(Office office, Model model) {
        return "modules/sys/officeIndex";
    }

    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = {"list"})
    public String list(Office office, Model model) {
        model.addAttribute("list", officeClient.findByParentIdsLike(office));
        return "modules/sys/officeList";
    }

    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = "form")
    public String form(Office office, Model model) {
        User user = UserUtils.getUser();
        if (office.getParent() == null || office.getParent().getId() == null) {
            office.setParent(user.getOffice());
        }
        if(StringUtils.isNotBlank(office.getParent().getId())){
        	Office parent = officeClient.getById(office.getParent().getId());
        	if(parent != null){
        		office.setParent(parent);        		
        	}
        }
        
        if (office.getArea() == null) {
            office.setArea(user.getOffice().getArea());
        }
        
        model.addAttribute("office", office);
        return "modules/sys/officeForm";
    }

    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "save")
    public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, office)) {
            return form(office, model);
        }
        office.setCurrentLoginUser(UserUtils.getUser());
		
        office = officeClient.save(office);

        if (office.getChildDeptList() != null) {
            Office childOffice = null;
            for (String id : office.getChildDeptList()) {
                childOffice = new Office();
                childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
                childOffice.setParent(office);
                childOffice.setArea(office.getArea());
                childOffice.setType("2");
                childOffice.setUseable(Constants.YES);
                childOffice.setCurrentLoginUser(UserUtils.getUser());
                
                officeClient.save(childOffice);
            }
        }

        addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
        String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
        return "redirect:/sys/office/list?id=" + id + "&parentIds=" + (StringUtils.isBlank(office.getParentIds()) ? "" : office.getParentIds()) + "&refreshTree=true";
    }

    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "delete")
    public String delete(Office office, RedirectAttributes redirectAttributes) {
        officeClient.delete(office);
        addMessage(redirectAttributes, "删除机构成功");
        return "redirect:/sys/office/list?id=" + office.getParentId() + "&parentIds=" + (StringUtils.isBlank(office.getParentIds()) ? "" : office.getParentIds()) + "&refreshTree=true";
    }

    /**
     * 获取机构JSON数据。
     *
     * @param extId    排除的ID
     * @param type     类型（1：公司；2：部门/小组/其它：3：用户）
     * @param grade    显示级别
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Office> list = officeClient.findList(UserUtils.getUser(), isAll);
        for (int i = 0; i < list.size(); i++) {
            Office e = list.get(i);
            if ((StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1))
                    && (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
                    && (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
                    && Constants.YES.equals(e.getUseable())) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("pIds", e.getParentIds());
                map.put("name", e.getName());
                map.put("type", e.getType());
                if (type != null && "3".equals(type)) {
                    map.put("isParent", true);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }
}
