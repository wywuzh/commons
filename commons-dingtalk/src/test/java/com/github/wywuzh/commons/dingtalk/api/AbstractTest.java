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
package com.github.wywuzh.commons.dingtalk.api;

import com.github.wywuzh.commons.dingtalk.config.ApiConfig;
import org.junit.Before;

/**
 * 类AbstractTest的实现描述：测试基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:28:06
 * @version v2.3.8
 * @since JDK 1.8
 */
public abstract class AbstractTest {

    protected ApiConfig apiConfig;

    protected final String AGENT_ID = "1087695954";
    /**
     * 应用的唯一标识key
     */
    protected final String APP_KEY = "dingziruwslmoflj5cyc";
    /**
     * 应用的密钥
     */
    protected final String APP_SECRET = "UOrFnv0IqZJf2XJobif7_jblEgF0Kn2S4-013sTfil9o7yhg59g_DynyUtdFlGCD";

    @Before
    public void init() {
        apiConfig = new ApiConfig(AGENT_ID, APP_KEY, APP_SECRET);
    }

}
