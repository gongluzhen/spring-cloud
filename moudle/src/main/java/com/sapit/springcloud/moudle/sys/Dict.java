/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/sapit/businessIntegration">businessIntegration</a> All rights reserved.
 */
package com.sapit.springcloud.moudle.sys;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.DataEntity;
import com.sapit.springcloud.moudle.util.excel.annotation.ExcelField;

public class Dict extends DataEntity<Dict> {

    private static final long serialVersionUID = 1L;
    private String value;    // 数据值
    private String label;    // 标签名
    private String type;    // 类型
    private String description;// 描述
    private Integer sort;    // 排序
    private String parentId;//父Id

    public Dict() {
        super();
    }

    public Dict(String id) {
        super(id);
    }

    public Dict(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @XmlAttribute
    @Length(min = 1, max = 100)
    @ExcelField(title = "键值", align = 2, sort = 1)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    @Length(min = 1, max = 100)
    @ExcelField(title = "标签", align = 2, sort = 2)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Length(min = 1, max = 100)
    @ExcelField(title = "类型", align = 2, sort = 3)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    @Length(min = 0, max = 100)
    @ExcelField(title = "描述", align = 2, sort = 4)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @ExcelField(title = "排序", align = 2, sort = 5)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = 100)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return label;
    }

    @Length(min = 0, max = 255)
    @ExcelField(title = "备注", align = 2, sort = 6)
    public String getRemarks() {
        return remarks;
    }
}