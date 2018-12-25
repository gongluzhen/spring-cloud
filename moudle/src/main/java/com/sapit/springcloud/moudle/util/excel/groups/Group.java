package com.sapit.springcloud.moudle.util.excel.groups;

public enum Group {
	SYSUSER("系统用户", 1);
	private String name;
	private int group;

	private Group(String name, int group) {
		this.name = name;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return this.group + "";
	}
}
