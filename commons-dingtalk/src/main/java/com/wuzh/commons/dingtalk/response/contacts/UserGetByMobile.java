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
package com.wuzh.commons.dingtalk.response.contacts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 类UserGetByMobile的实现描述：根据手机号获取userid
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:24:39
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserGetByMobile implements Serializable {

    /**
     * 员工的userid
     */
    @SerializedName("userid")
    private String userId;

}