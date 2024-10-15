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
package io.github.wywuzh.commons.core.util;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import io.github.wywuzh.commons.core.sequence.SnowflakeIdWorker;

/**
 * 类IdWorkerTest的实现描述：雪花id
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-07-29 14:11:22
 * @version v2.7.0
 * @since JDK 1.8
 */
@Slf4j
public class SnowflakeIdWorkerTest {

    public static void main(String[] args) {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
        long nextId = snowflakeIdWorker.nextId();
        log.info("idWorker.nextId={}, 长度={}", nextId, String.valueOf(nextId).length());
        String nextIdHex = String.format("%x", nextId);
        log.info("idWorker.nextIdHex={}, 长度={}", nextIdHex, nextIdHex.length());
        nextIdHex = SnowflakeIdWorker.nextIdHex();
        log.info("idWorker.nextIdHex={}, 长度={}", nextIdHex, nextIdHex.length());

        long nextLongId = 1002579559079514112L;
        log.info("nextLongId={}, 长度={}", nextLongId, String.valueOf(nextId).length());
        String nextLongIdHex = String.format("%x", nextLongId);
        log.info("nextLongIdHex={}, 长度={}", nextLongIdHex, nextLongIdHex.length());

        final long twepoch = 1288834974657L;
        log.info("开始时间:{}", DateUtils.format(new Date(twepoch), DateUtils.PATTERN_DATE_TIME));

        log.info("开始时间戳:{}", DateUtils.parse("2015-01-01", DateUtils.PATTERN_DATE).getTime());
    }

}
