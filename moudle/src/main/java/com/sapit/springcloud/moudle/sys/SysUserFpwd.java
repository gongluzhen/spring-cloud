package com.sapit.springcloud.moudle.sys;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sapit.springcloud.moudle.DataEntity;
import com.sapit.springcloud.moudle.util.excel.annotation.ExcelField;

public class SysUserFpwd extends DataEntity<SysUserFpwd> {

	private static final long serialVersionUID = 1L;
	private User user; // userID userID
	private String tokenId; // tokenID tokenID
	private Date activeTime; // 活跃时间 活跃时间
	private String emailFlag; // 字典表：1-是；0-否。

	public SysUserFpwd() {
		super();
	}

	public SysUserFpwd(String id) {
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min = 0, max = 64, message = "tokenID tokenID长度必须介于 0 和 64 之间")
	@ExcelField(title = "tokenId", align = 2, sort = 2)
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "活跃时间", align = 2, sort = 3)
	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	@Length(min = 0, max = 1, message = "字典表：1-是；0-否。长度必须介于 0 和 1 之间")
	@ExcelField(title = "邮件是否发送成功", align = 2, sort = 4, dictType = "yes_no")
	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	@Override
	public String toString() {
		return "会员（" + this.user.getName() + "）";
	}
}