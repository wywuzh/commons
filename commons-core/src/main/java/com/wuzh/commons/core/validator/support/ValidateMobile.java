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
package com.wuzh.commons.core.validator.support;

import org.springframework.util.Assert;

import com.wuzh.commons.core.validator.PatternType;
import com.wuzh.commons.core.validator.Validate;

/**
 * 类ValidateMobile.java的实现描述：手机号验证
 * 
 * <pre>
 *  国家号码段分配如下:
 * 
 *  移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
 *  联通：130、131、132、152、155、156、185、186
 *  电信：133、153、180、189、（1349卫通）
 * </pre>
 * 
 * @author 伍章红 2015-8-2 下午8:02:52
 * @see com.wuzh.frame.core.validator.Validate
 * @see com.wuzh.frame.core.validator.PatternType
 * @since JDK 1.7.0_71
 */
public class ValidateMobile extends Validate {

    public static final String PATTERN_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    @Override
    protected PatternType getPatternType() {
        return PatternType.PATTERN_MOBILE;
    }

    /**
     * 验证手机号所属运营商类型
     * 
     * @author 伍章红 2015-8-2 ( 下午8:07:29 )
     * @param mobile
     * @return 手机号所属运营商类型（-1：手机号输入位数不足11位；0：未知运营商；1：中国移动；2：中国联通；3：中国电信）
     */
    public String validateMobileType(String mobile) {
        Assert.notNull(mobile, "手机号输入不能为空");

        Validate validate = new ValidateMobile();
        // 验证手机号
        if (!validate.matches(mobile)) {
            return "-1";
        }

        String prefix = mobile.trim().substring(0, 3);
        // 中国移动
        if ("134".equals(prefix) || "135".equals(prefix) || "136".equals(prefix) || "137".equals(prefix)
                || "138".equals(prefix) || "139".equals(prefix) || "150".equals(prefix) || "151".equals(prefix)
                || "152".equals(prefix) || "157".equals(prefix) || "158".equals(prefix) || "159".equals(prefix)
                || "187".equals(prefix) || "188".equals(prefix)) {
            return "1";
        }
        // 中国联通
        else if (prefix.equals("130") || prefix.equals("131") || prefix.equals("132") || prefix.equals("156")
                || prefix.equals("185") || prefix.equals("186")) {
            return "2";
        }
        // 中国电信
        else if (prefix.equals("133") || prefix.equals("153") || prefix.equals("180") || prefix.equals("189")) {
            return "3";
        }

        // 未知运营商
        return "0";
    }

    /**
     * 获取手机号的邮箱地址
     * 
     * @author 伍章红 2015-8-2 ( 下午8:25:49 )
     * @param mobile
     * @return
     */
    public String getMobileEmail(String mobile) {
        String mobileType = validateMobileType(mobile);

        if ("1".equals(mobileType)) {
            return mobile + "@139.com";
        } else if ("2".equals(mobileType)) {
            return mobile + "@wo.com.cn";
        } else if ("3".equals(mobileType)) {
            return mobile + "@189.com";
        }
        return null;
    }

    public static void main(String[] args) {
        ValidateMobile validate = new ValidateMobile();

        System.out.println(validate.validateMobileType("18601274623"));
        System.out.println(validate.getMobileEmail("18601274623"));
        System.out.println(validate.matches("18601274623"));
    }
}
