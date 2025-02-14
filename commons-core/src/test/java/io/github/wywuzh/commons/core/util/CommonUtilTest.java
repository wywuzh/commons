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
package io.github.wywuzh.commons.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import io.github.wywuzh.commons.core.json.jackson.JsonMapper;

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
    public void join() {
        Set<String> firstSet = new HashSet<>();
        firstSet.add("Java");
        firstSet.add("DataSource");
        firstSet.add("Web");
        Set<String> secondSet = new HashSet<>();
        secondSet.add("Spring");
        secondSet.add("MyBatis");
        secondSet.add("Spring JDBC");
        Set<String> threeSet = new HashSet<>();
        threeSet.add("JavaScript");
        threeSet.add("CSS");
        threeSet.add("HTML");
        String join = CommonUtil.join(firstSet, secondSet, "_");
        log.info("CommonUtil.join：{}", join);

        String[] joins = CommonUtil.joins(firstSet, secondSet, threeSet);
        log.info("CommonUtil.joins：{}", Arrays.toString(joins));
    }

    @Test
    public void isContainChinese() {
        String content = "word分词不满足需求，咋办？";
        System.out.println(CommonUtil.isContainChinese(content));
    }

    @Test
    public void splitContent() {
        String content = "销售额＝含税收入(1+增值税征收率)";
        String separator = "+-*/=()";
        List<String> result = CommonUtil.splitContent(content, separator);

        log.info("处理结果：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(result));
    }

}
