package com.zeus.integration.spring.web.vo;

/**
 * 字典
 * Created by hexiaofeng on 15-11-4.
 */
public class Dictionary {
    // 名称
    private String label;
    // 值
    private Object value;

    public Dictionary() {
    }

    public Dictionary(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
