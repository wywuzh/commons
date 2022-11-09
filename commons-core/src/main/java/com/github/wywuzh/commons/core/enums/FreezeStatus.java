/*
 * Copyright 2015-2022 the original author or authors.
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
package com.github.wywuzh.commons.core.enums;

/**
 * 类FreezeStatus.java的实现描述：冻结状态
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:27:50
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum FreezeStatus {
    /**
     * 解冻（未冻结）
     */
    UNFREEZE("0"),
    /**
     * 冻结
     */
    FREEZE("1");

    private String status;

    private FreezeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static FreezeStatus findByStatus(String status) {
        if ("0".equals(status)) {
            return UNFREEZE;
        } else if ("1".equals(status)) {
            return FREEZE;
        }
        return null;
    }
}
