package com.sapit.springcloud.web.modules.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.sapit.springcloud.common.redis.CacheUtils;
import com.sapit.springcloud.common.util.CookieUtils;
import com.sapit.springcloud.common.util.PropertiesUtil;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.web.common.servlet.ValidateCodeServlet;
import com.sapit.springcloud.web.common.shiro.FormAuthenticationFilter;
import com.sapit.springcloud.web.common.shiro.IdGen;
import com.sapit.springcloud.web.common.shiro.SystemAuthorizingRealm.Principal;
import com.sapit.springcloud.web.common.shiro.session.SessionDAO;
import com.sapit.springcloud.web.common.utils.UserUtils;
import com.sapit.springcloud.web.common.web.BaseController;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		if (logger.isDebugEnabled()) {
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (PropertiesUtil.notAllowRefreshIndex) {
			CookieUtils.setCookie(response, "LOGINED", "false");
		}

		// 如果已经登录，则跳转到管理首页
		if (principal != null && !principal.isMobileLogin()) {
			return "redirect:/";
		}
		return "login";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "redirect:" + "/";
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

		if (logger.isDebugEnabled()) {
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", sessionDAO.getActiveSessions(false).size(), message, exception);
		}

		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)) {
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}

		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

		// 如果是手机登录，则返回JSON字符串
		if (mobile) {
			return renderString(response, model);
		}

		return "login";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "", "/", "/index" })
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);

		if (logger.isDebugEnabled()) {
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		model.addAttribute("tabmode", CookieUtils.getCookie(request, "tabmode"));
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("menus", UserUtils.getMenuList());

		// 如果已登录，再次访问主页，则退出原账号。
		if (PropertiesUtil.notAllowRefreshIndex) {
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)) {
				CookieUtils.setCookie(response, "LOGINED", "true");
			} else if (StringUtils.equals(logined, "true")) {
				UserUtils.getSubject().logout();
				return "redirect:/login";
			}
		}

		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()) {
			if (request.getParameter("login") != null) {
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null) {
				return "index";
			}
			return "redirect:/login";
		}

		return "index";
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}

	/**
	 * 是否是验证码登录
	 * 
	 * @param useruame
	 *            用户名
	 * @param isFail
	 *            计数加1
	 * @param clean
	 *            计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
