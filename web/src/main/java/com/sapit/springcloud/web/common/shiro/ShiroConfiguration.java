package com.sapit.springcloud.web.common.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.sapit.springcloud.web.common.shiro.cache.JedisCacheManager;
import com.sapit.springcloud.web.common.shiro.session.JedisSessionDAO;
import com.sapit.springcloud.web.common.shiro.session.SessionManager;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfiguration {

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 默认跳转到登陆页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登陆成功后的页面
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		// 自定义过滤器
		Map<String, Filter> filterMap = new LinkedHashMap<>();
		filterMap.put("authc", new FormAuthenticationFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		// 权限控制map
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/services/**", "anon");
		filterChainDefinitionMap.put("/actuator/**", "anon");
		filterChainDefinitionMap.put("/login", "authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "user");
		filterChainDefinitionMap.put("/act/editor/**", "user");
		filterChainDefinitionMap.put("/ReportServer/**", "user");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 核心的安全事务管理器
	 * 
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(systemAuthorizingRealm());
		securityManager.setSessionManager(sessionManager());
		securityManager.setCacheManager(shiroCacheManager());

		return securityManager;
	}

	/**
	 * 身份认证Realm，此处的注入不可以缺少。否则会在UserRealm中注入对象会报空指针.
	 * 
	 * @return
	 */
	@Bean
	public SystemAuthorizingRealm systemAuthorizingRealm() {
		SystemAuthorizingRealm systemAuthorizingRealm = new SystemAuthorizingRealm();

		return systemAuthorizingRealm;
	}

	@Bean
	public SessionManager sessionManager() {
		SessionManager sessionManager = new SessionManager();
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setGlobalSessionTimeout(1800000L);
		sessionManager.setSessionValidationInterval(120000L);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionIdCookie(new SimpleCookie("springcloud.session.id"));
		sessionManager.setSessionIdCookieEnabled(true);

		return sessionManager;
	}

	@Bean
	public CacheManager shiroCacheManager() {
		JedisCacheManager shiroCacheManager = new JedisCacheManager();
		shiroCacheManager.setCacheKeyPrefix("springcloud_shiro_cache_");

		return shiroCacheManager;
	}

	/**
	 * @return
	 */
	@Bean
	public SessionDAO sessionDAO() {
		JedisSessionDAO sessionDAO = new JedisSessionDAO();
		sessionDAO.setSessionIdGenerator(new IdGen());

		sessionDAO.setSessionKeyPrefix("springcloud_shiro_session_");
		return sessionDAO;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * Shiro生命周期处理器
	 * 
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 自动创建代理
	 * 
	 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
}
