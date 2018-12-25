package com.sapit.springcloud.web.modules.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sapit.springcloud.client.sys.LogClient;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Log;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
@RequestMapping(value = "/sys/log")
public class LogController extends BaseController {
	@Autowired
	private LogClient logClient;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		log.setCurrentLoginUser(UserUtils.getUser());
		log.setPage(new Page<Log>(request, response));
		
        Page<Log> page = logClient.findPage(log); 
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}

}
