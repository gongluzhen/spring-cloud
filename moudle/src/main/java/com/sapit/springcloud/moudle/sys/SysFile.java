package com.sapit.springcloud.moudle.sys;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.DataEntity;

/**
 * 系统-附件表Entity
 * 
 * @author gongluzhen
 * @version 2018-12-12
 */
public class SysFile extends DataEntity<SysFile> {

	private static final long serialVersionUID = 1L;
	private String name; // 名称
	private String contentType; // 文件类型
	private Long size; // 大小（byte）
	private String businessTableName; // 业务表名称
	private String businessKey; // 业务表主键

	public SysFile() {
		super();
	}

	public SysFile(String id) {
		super(id);
	}

	@Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Length(min = 0, max = 11, message = "大小（byte）长度必须介于 0 和 11 之间")
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Length(min = 1, max = 200, message = "业务表名称长度必须介于 1 和 200 之间")
	public String getBusinessTableName() {
		return businessTableName;
	}

	public void setBusinessTableName(String businessTableName) {
		this.businessTableName = businessTableName;
	}

	@Length(min = 1, max = 64, message = "业务表主键长度必须介于 1 和 64 之间")
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

}