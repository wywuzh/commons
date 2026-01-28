/*
 * Copyright 2015-2026 the original author or authors.
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
package io.github.wywuzh.commons.core.executor.exception;

/**
 * 事务执行异常
 *
 * @author 伍章红
 * @since JDK 1.8
 */
public class TransactionExecutionException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public TransactionExecutionException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     * @param cause   原因异常
     */
    public TransactionExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
