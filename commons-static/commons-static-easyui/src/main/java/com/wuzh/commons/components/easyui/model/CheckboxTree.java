package com.wuzh.commons.components.easyui.model;

import java.io.Serializable;

/**
 * 类CheckboxTree.java的实现描述：复选框tree
 *
 * @author 伍章红 2015年11月10日 下午4:36:23
 * @version v1.0.0
 * @since JDK 1.7
 */
public class CheckboxTree<T extends Serializable> extends Tree<T> implements
        Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点是否被选中
     */
    private boolean checked;

    public CheckboxTree() {
        super();
    }

    public CheckboxTree(String id, String name, String text, String pid) {
        super(id, name, text, pid);
    }

    public CheckboxTree(String id, String name, String text, String pid,
            String iconCls) {
        super(id, name, text, pid, iconCls);
    }

    public CheckboxTree(String id, String name, String text, String pid,
            boolean checked) {
        super(id, name, text, pid);
        this.checked = checked;
    }

    public CheckboxTree(String id, String name, String text, String pid,
            String iconCls, boolean checked) {
        super(id, name, text, pid, iconCls);
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
