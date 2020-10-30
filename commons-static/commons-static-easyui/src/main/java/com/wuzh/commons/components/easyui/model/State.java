package com.wuzh.commons.components.easyui.model;

/**
 * 类State.java的实现描述：节点状态
 *
 * @author 伍章红 2015年11月10日 下午3:38:15
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum State {

    /**
     * 展开节点
     */
    OPEN("OPEN"),
    /**
     * 关闭节点
     */
    CLOSED("CLOSED");

    private String value;

    private State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value.toLowerCase();
    }

}
