package com.sapit.springcloud.common.util.ftp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "ftp.client")
public class FtpClientProperties {

	/**
	 * ftp地址
	 */
	private String host;

	/**
	 * 端口号
	 */
	private Integer port = 21;

	/**
	 * 登录用户
	 */
	private String username;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 被动模式
	 */
	private boolean passiveMode = false;

	/**
	 * 编码
	 */
	private String encoding = "UTF-8";

	/**
	 * 连接超时时间(秒)
	 */
	private Integer connectTimeout;

	/**
	 * 缓冲大小
	 */
	private Integer bufferSize = 1024;

	/**
	 * 设置keepAlive 单位:秒 0禁用
	 */
	private Integer keepAliveTimeout = 0;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPassiveMode() {
		return passiveMode;
	}

	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public Integer getKeepAliveTimeout() {
		return keepAliveTimeout;
	}

	public void setKeepAliveTimeout(Integer keepAliveTimeout) {
		this.keepAliveTimeout = keepAliveTimeout;
	}
}
