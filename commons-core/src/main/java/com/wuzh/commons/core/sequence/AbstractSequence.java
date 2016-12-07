/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.sequence;

import java.util.Date;

import com.wuzh.commons.core.util.DateUtil;
import com.wuzh.commons.core.util.StringHelper;

/**
 * 类AbstractSequence.java的实现描述：序列基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月8日 上午12:04:28
 * @version v1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractSequence {

    /**
     * 当前数字
     */
    private long currentNumber;
    /**
     * 最后一次时间
     */
    private String lastTime;

    public synchronized String getCode() {
        String currentTime = getCurrentTime();
        if (currentTime.equals(lastTime)) {
            currentNumber++;
        } else {
            // 时间改变，回到起点
            currentNumber = 1;
            lastTime = currentTime;
        }
        return new StringBuffer().append(getFirstPart()).append(getSecondPart()).append(getThreePart()).toString();
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    private String getCurrentTime() {
        return DateUtil.format(new Date(), datePattern());
    }

    /**
     * 第一部分
     * 
     * @return
     */
    protected abstract Object getFirstPart();

    /**
     * 第二部分
     * 
     * @return
     */
    private String getSecondPart() {
        return lastTime;
    }

    /**
     * 第三部分
     * 
     * @return
     */
    private String getThreePart() {
        return StringHelper.formatToString(getThreePartLength(), currentNumber);
    }

    /**
     * 时间格式
     * 
     * @param pattern
     * @return
     */
    protected abstract String datePattern();

    /**
     * 指定第三部分值长度
     * 
     * @param length
     * @return
     */
    protected abstract int getThreePartLength();

}
