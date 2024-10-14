/*
 * Copyright 2015-2024 the original author or authors.
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
package io.github.wywuzh.commons.dingtalk.exception;

/**
 * 类DingtalkException的实现描述：钉钉API处理异常
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-27 22:34:59
 * @version v2.3.8
 * @since JDK 1.8
 */
public class DingtalkException extends RuntimeException {

    public DingtalkException() {
        super();
    }

    public DingtalkException(String message) {
        super(message);
    }

    public DingtalkException(String message, Throwable cause) {
        super(message, cause);
    }

    public DingtalkException(Throwable cause) {
        super(cause);
    }

}
