package com.wuzh.commons.components.easyui.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 类TreeGrid.java的实现描述：jQuery-easyui组件treegrid对应实体类
 *
 * @author 伍章红 2015年11月10日 下午3:58:10
 * @version v1.0.0
 * @since JDK 1.7
 */
public class TreeGrid<T extends Serializable> extends Tree<T> {
    private static final long serialVersionUID = 1L;

    public TreeGrid() {
        super();
    }

    public TreeGrid(String id, String name, String text, String pid,
            String iconCls) {
        super(id, name, text, pid, iconCls);
    }

    public TreeGrid(String id, String name, String text, String pid) {
        super(id, name, text, pid);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
