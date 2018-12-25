package com.sapit.springcloud.moudle.sys;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sapit.springcloud.moudle.DataEntity;
import com.sapit.springcloud.moudle.util.excel.annotation.ExcelField;

public class Menu extends DataEntity<Menu> {

    private static final long serialVersionUID = 1L;
    private Menu parent;    // 父级菜单
    private String parentIds; // 所有父级编号
    private String name;    // 名称
    private String href;    // 链接
    private String target;    // 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon;    // 图标
    private Integer sort;    // 排序
    private String isShow;    // 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识

    private String userId;

    public Menu() {
        super();
        this.sort = 30;
        this.isShow = "1";
    }

    public Menu(String id) {
        super(id);
    }

    @NotNull
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Length(min = 1, max = 2000)
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100)
    @ExcelField(title = "名称", align = 2, sort = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 2000)
    @ExcelField(title = "链接", align = 2, sort = 3)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min = 0, max = 20)
    @ExcelField(title = "目标", align = 2, sort = 4)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Length(min = 0, max = 100)
    @ExcelField(title = "图标", align = 2, sort = 5)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    @ExcelField(title = "排序", align = 2, sort = 6)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = 1)
    @ExcelField(title = "是否可见", align = 2, sort = 7, dictType = "show_hide")
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Length(min = 0, max = 200)
    @ExcelField(title = "权限", align = 2, sort = 8)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }

    @JsonIgnore
    public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Menu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static String getRootId() {
        return "1";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return name;
    }

    @ExcelField(title = "上级菜单", align = 2, sort = 1)
    public String getParentName() {
        return parent == null ? null : parent.getName();
    }

    @Length(min = 0, max = 255)
    @ExcelField(title = "备注", align = 2, sort = 9)
    public String getRemarks() {
        return remarks;
    }
}