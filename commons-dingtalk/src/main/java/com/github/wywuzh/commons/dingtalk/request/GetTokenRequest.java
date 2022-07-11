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
package com.github.wywuzh.commons.dingtalk.request;

import com.github.wywuzh.commons.core.web.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类GetTokenRequest的实现描述：获取凭证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 12:41:14
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GetTokenRequest extends BaseRequest {
    private static final long serialVersionUID = 8844322137430327841L;

    /**
     * 应用的唯一标识key
     */
    private String appkey;
    /**
     * 应用的密钥
     */
    private String appsecret;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
