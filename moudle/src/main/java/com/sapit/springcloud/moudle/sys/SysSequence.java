package com.sapit.springcloud.moudle.sys;

import org.hibernate.validator.constraints.Length;

import com.sapit.springcloud.moudle.DataEntity;

public class SysSequence extends DataEntity<SysSequence> {

    private static final long serialVersionUID = 1L;
    private String seqName;        // 序列名称
    private String prefix;        // prefix
    private String currentVal;        // 当前值

    public SysSequence() {
        super();
    }

    public SysSequence(String id) {
        super(id);
    }

    @Length(min = 1, max = 100, message = "序列名称长度必须介于 1 和 100 之间")
    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    @Length(min = 1, max = 100, message = "prefix长度必须介于 1 和 100 之间")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Length(min = 1, max = 100, message = "当前值长度必须介于 1 和 100 之间")
    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal;
    }

}