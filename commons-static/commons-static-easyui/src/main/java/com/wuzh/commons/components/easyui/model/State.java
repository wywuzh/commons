/*
 * Copyright 2015-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
