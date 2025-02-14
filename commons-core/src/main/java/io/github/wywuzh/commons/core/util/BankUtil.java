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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 银行数据处理工具类
 *
 * @author wuzh, 12/30/2013
 * @since JDK 1.6.0_20
 */
public class BankUtil {
    private static final Log logger = LogFactory.getLog(BankUtil.class);

    /**
     * 将传入的银行卡号转换成每四位一格空格分隔的形式输出
     *
     * <pre>
     * 如传入的银行卡号为6222020200102027979，输出时则为6222 0202 0010 2027 979
     * </pre>
     *
     * @param cardId 银行卡号
     * @return
     */
    public static String convert4BankCardId(String cardId) {
        StringBuffer card = new StringBuffer();
        for (int i = 0; i < cardId.length(); i++) {
            // 每四位加一个空格
            if (i != 0 && i % 4 == 0) {
                card.append(" ");
            }
            card.append(cardId.substring(i, i + 1));
        }
        return card.toString();
    }

}
