package com.sapit.springcloud.moudle;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sapit.springcloud.moudle.sys.User;

/**
 * 数据Entity类
 * 
 * @author sapit
 * @version 2014-05-16
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	protected String remarks; // 备注
	protected User createBy; // 创建者
	protected Date createDate; // 创建日期
	protected User updateBy; // 更新者
	protected Date updateDate; // 更新日期
	protected String delFlag; // 删除标记（0：正常；1：删除；2：审核）

	protected User currentLoginUser; // 当前登录用户

	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}

	public DataEntity(String id) {
		super(id);
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert() {
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (this.isNewRecord) {
			setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		if (currentLoginUser != null && StringUtils.isNotBlank(currentLoginUser.getId())) {
			this.updateBy = currentLoginUser;
			this.createBy = currentLoginUser;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate() {
		if (currentLoginUser != null && StringUtils.isNotBlank(currentLoginUser.getId())) {
			this.updateBy = currentLoginUser;
		}
		this.updateDate = new Date();
	}

	@Length(min = 0, max = 255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Length(min = 1, max = 1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public User getCurrentLoginUser() {
		return currentLoginUser;
	}

	public void setCurrentLoginUser(User currentLoginUser) {
		this.currentLoginUser = currentLoginUser;
	}

}
