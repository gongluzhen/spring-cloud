/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/sapit/businessIntegration">businessIntegration</a> All rights reserved.
 */
package com.sapit.springcloud.moudle.sys;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.DataEntity;

public class SysWxAccessToken extends DataEntity<SysWxAccessToken> {
	
	private static final long serialVersionUID = 1L;
	private String accessToken;		// access_token凭证
	private String jsapiTicket;		// jsapi_ticket凭证
	private Long expiresIn;		// 凭证有效时间，单位：秒
	private Long createTime;		// 创建时间毫秒数
	private String appId;		// userid
	
	public SysWxAccessToken() {
		super();
	}

	public SysWxAccessToken(String id){
		super(id);
	}

	@Length(min=1, max=600, message="access_token凭证长度必须介于 1 和 600 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=1, max=600, message="jsapi_ticket凭证长度必须介于 1 和 600 之间")
	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	
	@NotNull(message="凭证有效时间，单位：秒不能为空")
	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	@NotNull(message="创建时间毫秒数不能为空")
	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=1, max=128, message="userid长度必须介于 1 和 128 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}