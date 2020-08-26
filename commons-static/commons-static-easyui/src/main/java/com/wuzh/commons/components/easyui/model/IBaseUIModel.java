package com.wuzh.commons.components.easyui.model;

import java.io.Serializable;

/**
 * 类IBaseUIModel.java的实现描述：UI组件基类
 *
 * @author 伍章红 2015年11月10日 下午4:29:05
 * @version v1.0.0
 * @since JDK 1.7
 */
public interface IBaseUIModel {

    /**
     * 关联目标对象，作为扩展属性使用，提供前台组件引用
     *
     * @author 伍章红 2015年11月10日 下午5:18:52
     * @return
     */
    public Serializable getTarget();

    /**
     * 关联目标对象，作为扩展属性使用，提供前台组件引用
     *
     * @author 伍章红 2015年11月10日 下午5:18:54
     * @param target
     */
    public void setTarget(Serializable target);
}
