package com.sapit.springcloud.moudle.sys;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.TreeEntity;
import com.sapit.springcloud.moudle.util.excel.annotation.ExcelField;

public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;
	// private Area parent; // 父级编号
	// private String parentIds; // 所有父级编号
	private String code; // 区域编码
	// private String name; // 区域名称
	// private Integer sort; // 排序
	private String type; // 区域类型（0：国家；1：大区；2：省份、直辖市；3：地市；4：区县）
	private String[] typeArray;
	private User nationalPresident;	//全国总会长sys_user表主键
	private User areaPresident;	//大区区长sys_user表主键

	public String[] getTypeArray() {
		return typeArray;
	}

	public void setTypeArray(String[] typeArray) {
		this.typeArray = typeArray;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Area() {
		super();
		this.sort = 30;
	}

	public Area(String id) {
		super(id);
	}

	// @JsonBackReference
	// @NotNull
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}
	//
	// @Length(min=1, max=2000)
	// public String getParentIds() {
	// return parentIds;
	// }
	//
	// public void setParentIds(String parentIds) {
	// this.parentIds = parentIds;
	// }
	//
	// @Length(min=1, max=100)
	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// public Integer getSort() {
	// return sort;
	// }
	//
	// public void setSort(Integer sort) {
	// this.sort = sort;
	// }

	@Length(min = 1, max = 1)
	@ExcelField(title = "区域类型", align = 2, sort = 4, dictType = "sys_area_type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min = 0, max = 100)
	@ExcelField(title = "区域编码", align = 2, sort = 3)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	//
	// public String getParentId() {
	// return parent != null && parent.getId() != null ? parent.getId() : "0";
	// }

	@Override
	public String toString() {
		return name;
	}

	@ExcelField(title = "上级区域", align = 2, sort = 1)
	public String getParentName() {
		return parent == null ? null : parent.getName();
	}

	@Length(min = 1, max = 100)
	@ExcelField(title = "机构名称", align = 2, sort = 2)
	public String getName() {
		return name;
	}

	@Length(min = 0, max = 255)
	@ExcelField(title = "备注", align = 2, sort = 5)
	public String getRemarks() {
		return remarks;
	}

	public User getNationalPresident() {
		return nationalPresident;
	}

	public void setNationalPresident(User nationalPresident) {
		this.nationalPresident = nationalPresident;
	}

	public User getAreaPresident() {
		return areaPresident;
	}

	public void setAreaPresident(User areaPresident) {
		this.areaPresident = areaPresident;
	}
}