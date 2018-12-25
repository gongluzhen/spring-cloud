package com.sapit.springcloud.moudle.sys;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.DataEntity;

/**
 * userTokenEntity
 * 
 * @author glz
 * @version 2017-04-26
 */
public class SysUserToken extends DataEntity<SysUserToken> {

	private static final long serialVersionUID = 1L;
	private User user; // userID
	private String tokenId; // tokenID
	private Date activeTime; // 活跃时间
	private String mobileLoginTimeout;

	public SysUserToken() {
		super();
	}

	public SysUserToken(String id) {
		super(id);
	}
	
	public SysUserToken(String id, String tokenId) {
		super(id);
		this.tokenId = tokenId;
	}

	@NotNull(message = "userID不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min = 1, max = 64, message = "tokenID长度必须介于 1 和 64 之间")
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public String getMobileLoginTimeout() {
		return mobileLoginTimeout;
	}

	public void setMobileLoginTimeout(String mobileLoginTimeout) {
		this.mobileLoginTimeout = mobileLoginTimeout;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}