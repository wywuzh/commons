/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.core.web.request;

import java.io.Serializable;

/**
 * 类BaseRequest的实现描述：请求参数基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/13 20:36
 * @version v2.0.2
 * @since JDK 1.8
 */
public abstract class BaseRequest implements Serializable {

    public abstract String toString();
}
