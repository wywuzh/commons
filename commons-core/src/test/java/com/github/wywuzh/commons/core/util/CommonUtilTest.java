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
package com.github.wywuzh.commons.core.util;

import com.github.wywuzh.commons.core.json.jackson.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * 类CommonUtilTest.java的实现描述：通用工具类测试
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021年10月9日 下午2:57:09
 * @version v2.4.8
 * @since JDK 1.8
 */
@Slf4j
public class CommonUtilTest {

    @Test
    public void splitContent() {
        String content = "销售额＝含税收入(1+增值税征收率)";
        String separator = "+-*/=()";
        List<String> result = CommonUtil.splitContent(content, separator);

        log.info("处理结果：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(result));
    }

}
