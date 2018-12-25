package com.sapit.springcloud.moudle.generate.code;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.DataEntity;

public class GenScheme extends DataEntity<GenScheme> {

	private static final long serialVersionUID = 1L;
	private String name; // 名称
	private String category; // 分类
	private String basePackageName;
	private String projectPath;
	private String moduleName;
	private String functionName; // 生成功能名
	private String functionNameSimple; // 生成功能名（简写）
	private String functionAuthor; // 生成功能作者
	private GenTable genTable; // 业务表名

	private String flag; // 0：保存方案； 1：保存方案并生成代码

	private boolean replaceFile; // 是否替换现有文件 0：不替换；1：替换文件

	public GenScheme() {
		super();
	}

	public GenScheme(String id) {
		super(id);
	}

	@Length(min = 1, max = 200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionNameSimple() {
		return functionNameSimple;
	}

	public void setFunctionNameSimple(String functionNameSimple) {
		this.functionNameSimple = functionNameSimple;
	}

	public String getFunctionAuthor() {
		return functionAuthor;
	}

	public void setFunctionAuthor(String functionAuthor) {
		this.functionAuthor = functionAuthor;
	}

	public GenTable getGenTable() {
		return genTable;
	}

	public void setGenTable(GenTable genTable) {
		this.genTable = genTable;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean getReplaceFile() {
		return replaceFile;
	}

	public void setReplaceFile(boolean replaceFile) {
		this.replaceFile = replaceFile;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getBasePackageName() {
		return basePackageName;
	}

	public void setBasePackageName(String basePackageName) {
		this.basePackageName = basePackageName;
	}

}
