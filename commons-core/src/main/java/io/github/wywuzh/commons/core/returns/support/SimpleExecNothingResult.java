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
package io.github.wywuzh.commons.core.returns.support;

import io.github.wywuzh.commons.core.returns.ReturnBase;
import io.github.wywuzh.commons.core.returns.ReturnCode;

/**
 * 类SimpleExecHavingResult.java的实现描述：执行处理，处理结果数据不返回
 *
 * @author 伍章红 2015-9-12 下午12:46:10
 * @version v1.0.0
 * @since JDK 1.7.0_71
 */
public class SimpleExecNothingResult extends ReturnBase {
    private static final long serialVersionUID = 1L;

    public SimpleExecNothingResult() {
        super();
    }

    public SimpleExecNothingResult(ReturnCode returnCode, String message) {
        super(returnCode, message);
    }

    public SimpleExecNothingResult(ReturnCode returnCode) {
        super(returnCode);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("SimpleExecResult [");
        result.append("returnCode=").append(getReturnCode()).append(",");
        result.append("message=").append(getMessage());
        result.append("]");
        return result.toString();
    }

}
