package com.sapit.springcloud.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class PropertiesUtil {
	public static boolean notAllowRefreshIndex;
	public static boolean multiAccountLogin;
	public static String defaultPassword;
	public static String dbName;
	public static String projectPath;
	public static String basePackageName;

	public boolean isNotAllowRefreshIndex() {
		return notAllowRefreshIndex;
	}

	@Value("${notAllowRefreshIndex}")
	public void setNotAllowRefreshIndex(boolean notAllowRefreshIndex) {
		PropertiesUtil.notAllowRefreshIndex = notAllowRefreshIndex;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	@Value("${defaultPassword}")
	public void setDefaultPassword(String defaultPassword) {
		PropertiesUtil.defaultPassword = defaultPassword;
	}

	public boolean isMultiAccountLogin() {
		return multiAccountLogin;
	}

	@Value("${multiAccountLogin}")
	public void setMultiAccountLogin(boolean multiAccountLogin) {
		PropertiesUtil.multiAccountLogin = multiAccountLogin;
	}

	public String getDbName() {
		return dbName;
	}

	@Value("${dbName}")
	public void setDbName(String dbName) {
		PropertiesUtil.dbName = dbName;
	}

	public String getProjectPath() {
		return projectPath;
	}

	@Value("${projectPath}")
	public void setProjectPath(String projectPath) {
		PropertiesUtil.projectPath = projectPath;
	}

	public String getBasePackageName() {
		return basePackageName;
	}

	@Value("${basePackageName}")
	public void setBasePackageName(String basePackageName) {
		PropertiesUtil.basePackageName = basePackageName;
	}

}
