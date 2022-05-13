/*
 * Copyright 2015-2017 the original author or authors.
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
package com.wuzh.commons.core.exception;

/**
 * 类AmountException.java的实现描述：自定义金额异常处理类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:37:50
 * @version v1.0.0
 * @since JDK 1.7
 */
public class AmountException extends FrameException {
    private static final long serialVersionUID = 7889065518700211013L;

    public AmountException() {
        super();
    }

    public AmountException(String message) {
        super(message);
    }

    public AmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmountException(Throwable cause) {
        super(cause);
    }
}
