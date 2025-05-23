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
package io.github.wywuzh.commons.core.exception;

/**
 * 类SystemFlagException.java的实现描述：系统内置数据标识异常
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:38:33
 * @version v1.0.0
 * @since JDK 1.7
 */
public class SystemFlagException extends FrameException {
    private static final long serialVersionUID = 1L;

    public SystemFlagException() {
        super();
    }

    public SystemFlagException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemFlagException(String message) {
        super(message);
    }

    public SystemFlagException(Throwable cause) {
        super(cause);
    }

}
