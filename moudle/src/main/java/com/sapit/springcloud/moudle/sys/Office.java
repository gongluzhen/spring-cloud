package com.sapit.springcloud.moudle.sys;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.TreeEntity;
import com.sapit.springcloud.moudle.util.excel.annotation.ExcelField;

public class Office extends TreeEntity<Office> {

    private static final long serialVersionUID = 1L;
    //	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
    private Area area;        // 归属区域
    private String code;    // 机构编码
    //	private String name; 	// 机构名称
//	private Integer sort;		// 排序
    private String type;    // 机构类型（1：公司；2：部门；3：小组）
    private String grade;    // 机构等级（1：一级；2：二级；3：三级；4：四级）
    private String address; // 联系地址
    private String zipCode; // 邮政编码
    private String master;    // 负责人
    private String phone;    // 电话
    private String fax;    // 传真
    private String email;    // 邮箱
    private String useable;//是否可用
    private User primaryPerson;//主负责人
    private User deputyPerson;//副负责人
    private List<String> childDeptList;//快速添加子部门
    
    private boolean refreshTree;

    public Office() {
        super();
//		this.sort = 30;
        this.type = "2";
    }

    public Office(String id) {
        super(id);
    }

    public List<String> getChildDeptList() {
        return childDeptList;
    }

    public void setChildDeptList(List<String> childDeptList) {
        this.childDeptList = childDeptList;
    }

    @ExcelField(title = "是否可用", align = 2, sort = 7)
    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public User getPrimaryPerson() {
        return primaryPerson;
    }

    public void setPrimaryPerson(User primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    public User getDeputyPerson() {
        return deputyPerson;
    }

    public void setDeputyPerson(User deputyPerson) {
        this.deputyPerson = deputyPerson;
    }

    //	@JsonBackReference
//	@NotNull
    public Office getParent() {
        return parent;
    }

    public void setParent(Office parent) {
        this.parent = parent;
    }
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}

    @NotNull
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
//
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}

    @Length(min = 1, max = 1)
    @ExcelField(title = "机构类型", align = 2, sort = 5, dictType = "sys_office_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 1, max = 1)
    @ExcelField(title = "机构级别", align = 2, sort = 6, dictType = "sys_office_grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Length(min = 0, max = 255)
    @ExcelField(title = "联系地址", align = 2, sort = 10)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min = 0, max = 100)
    @ExcelField(title = "邮政编码", align = 2, sort = 11)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Length(min = 0, max = 100)
    @ExcelField(title = "负责人", align = 2, sort = 12)
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Length(min = 0, max = 200)
    @ExcelField(title = "电话", align = 2, sort = 13)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 200)
    @ExcelField(title = "传真", align = 2, sort = 14)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Length(min = 0, max = 200)
    @ExcelField(title = "邮箱", align = 2, sort = 15)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 100)
    @ExcelField(title = "机构编码", align = 2, sort = 4)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}

    @Override
    public String toString() {
        return name;
    }

    @ExcelField(title = "上级机构", align = 2, sort = 1)
    public String getParentName() {
        return parent == null ? null : parent.getName();
    }

    @ExcelField(title = "归属区域", align = 2, sort = 2)
    public String getAreaName() {
        return area == null ? null : area.getName();
    }

    @Length(min = 1, max = 100)
    @ExcelField(title = "机构名称", align = 2, sort = 3)
    public String getName() {
        return name;
    }

    @ExcelField(title = "主负责人", align = 2, sort = 8)
    public String getPrimaryPersonName() {
        return primaryPerson == null ? null : primaryPerson.getName();
    }

    @ExcelField(title = "副负责人", align = 2, sort = 9)
    public String getDeputyPersonName() {
        return deputyPerson == null ? null : deputyPerson.getName();
    }

    @Length(min = 0, max = 255)
    @ExcelField(title = "备注", align = 2, sort = 16)
    public String getRemarks() {
        return remarks;
	}

	public boolean isRefreshTree() {
		return refreshTree;
	}

	public void setRefreshTree(boolean refreshTree) {
		this.refreshTree = refreshTree;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}