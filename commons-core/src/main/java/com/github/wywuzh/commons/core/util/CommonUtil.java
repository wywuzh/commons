/*
 * Copyright 2015-2022 the original author or authors.
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

import com.github.wywuzh.commons.core.math.CalculationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类CommonsUtil的实现描述：通用工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2018/7/15 20:26
 * @version v2.0.2
 * @since JDK 1.8
 */
public class CommonUtil {

    // 验证的正则表达式
    private static final String REG_ALPHA = "^[a-zA-Z]+$";

    private static final String REG_ALPHANUM = "^[a-zA-Z0-9]+$";

    private static final String REG_NUMBER = "^\\d+$";

    private static final String REG_INTEGER = "^[-+]?[1-9]\\d*$|^0$/";

    private static final String REG_FLOAT = "[-\\+]?\\d+(\\.\\d+)?$";

    private static final String REG_PHONE = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$";

    private static final String REG_MOBILE = "^((\\+86)|(86))?(1)\\d{10}$";

    private static final String REG_QQ = "^[1-9]\\d{4,10}$";

    private static final String REG_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private static final String REG_ZIP = "^[1-9]\\d{5}$";

    private static final String REG_IP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final String REG_URL = "^(http|https|ftp):\\/\\/(([A-Z0-9][A-Z0-9_-]*)(\\.[A-Z0-9][A-Z0-9_-]*)+)(:(\\d+))?\\/?/i";

    private static final String REG_CHINESE = "^[\\u0391-\\uFFE5]+$";

    private static final String REG_MONEY = "[\\-\\+]?\\d+(\\.\\d+)?$";

    /**
     * 可以用于判断Object,String,Map,Collection,String,Array是否为空
     */
    public static boolean isNull(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            if (((String) value).trim().replaceAll("\\s", "").equals("")) {
                return true;
            }
        } else if (value instanceof Collection) {
            if (((Collection) value).isEmpty()) {
                return true;
            }
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) == 0) {
                return true;
            }
        } else if (value instanceof Map) {
            if (((Map) value).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }
        return false;

    }

    public static boolean isNull(Object value, Object... items) {
        if (isNull(value) || isNull(items)) {
            return true;
        }
        for (Object item : items) {
            if (isNull(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }

    public static boolean isNotNull(Object value, Object... items) {
        return !isNull(value, items);
    }

    public static boolean isAlpha(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_ALPHA, value);
    }

    public static boolean isAlphanum(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_ALPHANUM, value);
    }

    public static boolean isInteger(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_INTEGER, value);
    }

    public static boolean isFloat(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_FLOAT, value);
    }

    public static boolean isMoney(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_MONEY, value);
    }

    public static boolean isPhone(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_PHONE, value);
    }

    public static boolean isMobile(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_MOBILE, value);
    }

    public static boolean isEmail(String value) {
        if (isNull(value)) {
            return false;
        }
        return Pattern.matches(REG_EMAIL, value);
    }

    public static boolean isQQ(String value) {
        return Pattern.matches(REG_QQ, value);
    }

    public static boolean isZip(String value) {
        return Pattern.matches(REG_ZIP, value);
    }

    public static boolean isIP(String value) {
        return Pattern.matches(REG_IP, value);
    }

    public static boolean isURL(String value) {
        return Pattern.matches(REG_URL, value);
    }

    public static boolean isChinese(String value) {
        return Pattern.matches(REG_CHINESE, value);
    }

    /**
     * 验证是否为合法身份证
     */
    public static boolean isIdCard(String value) {
        value = value.toUpperCase();
        if (!(Pattern.matches("^\\d{17}(\\d|X)$", value) || Pattern.matches("\\d{15}$", value))) {
            return false;
        }
        int provinceCode = Integer.parseInt(value.substring(0, 2));
        if (provinceCode < 11 || provinceCode > 91) {
            return false;
        }
        return true;
    }

    public static boolean isDate(String value) {
        if (value == null || value.isEmpty())
            return false;
        try {
            new SimpleDateFormat().parse(value);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将多个Set<String>合并成一个
     *
     * @param collSet
     * @return
     */
    public static String[] joins(Set<String>... collSet) {
        return joins(Arrays.asList(collSet), "_");
    }

    /**
     * 将多个Set<String>合并成一个
     *
     * @param collSet
     * @param separator 分隔符
     * @return
     */
    public static String[] joins(Set<String>[] collSet, String separator) {
        return joins(Arrays.asList(collSet), separator);
    }

    /**
     * 将多个Set<String>合并成一个
     *
     * @param collSet
     * @param separator 分隔符
     * @return
     */
    public static String[] joins(List<Set<String>> collSet, String separator) {
        String shareKey = "";
        if (collSet == null || collSet.size() == 0) {
            return shareKey.split(",");
        }

        for (int i = 0; i < collSet.size(); i++) {
            Set<String> beforeSet = null;
            if (i == 0) {
                beforeSet = new HashSet<>();
            } else {
                String[] split = shareKey.split(",");
                beforeSet = new HashSet<>(Arrays.asList(split));
            }
            Set<String> set = collSet.get(i);
            shareKey = join(beforeSet, set, separator);
        }
        return shareKey.split(",");
    }

    /**
     * 将两个Set合并成一个
     *
     * @param firstSet
     * @param secondSet
     * @param separator 分隔符
     * @return
     */
    public static String join(Set<String> firstSet, Set<String> secondSet, String separator) {
        String result = "";
        if (firstSet != null && firstSet.size() > 0) {
            int index = 0;
            for (String firstStr : firstSet) {
                if (index > 0) {
                    result = StringUtils.join(result, ",");
                }
                result = StringUtils.join(result, firstStr);
                index++;
            }
        }
        if (secondSet != null && secondSet.size() > 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (String secondStr : secondSet) {
                if (i > 0) {
                    sb.append(",");
                }
                if (StringUtils.isNotEmpty(result)) {
                    String[] firstStrArr = result.split(",");
                    int j = 0;
                    for (String str : firstStrArr) {
                        if (j > 0) {
                            sb.append(",");
                        }
                        sb.append(str).append(separator).append(secondStr);
                        j++;
                    }
                } else {
                    sb.append(secondStr);
                }
                i++;
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 分割中英文，一个英文占一行，相邻的中文占一行
     *
     * @param content
     * @return
     */
    public static List<String> splitEnglishCharacter(String content) {
        char[] chars = content.toCharArray();
        // 拼装规则：一个英文占一行，相邻的中文占一行
        List<String> expressions = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (isContainChinese(String.valueOf(c))) {
                sb.append(c);
            } else {
                // 检查sb中是否存在字符
                if (sb.length() > 0) {
                    expressions.add(sb.toString());
                }
                expressions.add(String.valueOf(c));
                sb = new StringBuilder();
            }
        }
        // 检查最后一部分没有加入的
        if (sb.length() > 0) {
            expressions.add(sb.toString());
        }
        return expressions;
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符  false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("context is empty!");
        }
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 分割字符串，content中的内容只要出现在了separator分割字符串中，就会被分割出来
     *
     * @param content   内容
     * @param separator 分割字符串
     * @return
     */
    public static List<String> splitContent(String content, String separator) {
        // 拼装规则：一个英文占一行，相邻的中文占一行
        List<String> expressions = new LinkedList<>();
        if (StringUtils.isBlank(content) || StringUtils.isBlank(separator)) {
            return expressions;
        }

        char[] chars = content.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (!StringUtils.contains(separator, String.valueOf(c))) {
                sb.append(c);
            } else {
                // 检查sb中是否存在字符
                if (sb.length() > 0) {
                    expressions.add(sb.toString());
                }
                expressions.add(String.valueOf(c));
                sb = new StringBuffer();
            }
        }
        // 检查最后一部分没有加入的
        if (sb.length() > 0) {
            expressions.add(sb.toString());
        }

        // String spiltRules = "\\+|-|\\*|/|=|\\(|\\)";
        // return Arrays.asList(content.split(spiltRules));
        return expressions;
    }

    /**
     * 拆分集合，按照每个list 1000行记录进行拆分
     *
     * @param list 集合信息
     * @return
     * @since v2.5.2
     */
    public static List<List<String>> splitList(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<List<String>> unions = new LinkedList<>();
        int total = list.size();
        final int pageSize = 1000;
        long pageTotal = (total - 1) / pageSize + 1;
        for (int index = 0; index < pageTotal; index++) {
            int toIndex = (index + 1) * pageSize < total ? (index + 1) * pageSize : total;
            List<String> subList = list.subList(index * pageSize, toIndex);
            unions.add(subList);
        }
        return unions;
    }

    /**
     * 匹配目标集合中存在的数据
     *
     * @param sourceList 目标集合
     * @param matchList  匹配数据集合，可选，如果传入为空则直接返回目标集合
     * @return
     * @since v2.5.2
     */
    public static List<String> matchSourceList(List<String> sourceList, List<String> matchList) {
        if (CollectionUtils.isEmpty(sourceList) || CollectionUtils.isEmpty(matchList)) {
            return sourceList;
        }
        List<String> resultList = new ArrayList<>();
        for (String item : sourceList) {
            if (!matchList.contains(item)) {
                continue;
            }
            resultList.add(item);
        }
        return resultList;
    }

    /**
     * 解析文件大小
     *
     * @param sourceFileSize 源文件大小
     * @return
     * @since v2.5.2
     */
    public static String resolveFileSize(Long sourceFileSize) {
        if (sourceFileSize == null) {
            return null;
        }
        // KB
        BigDecimal fileSize = CalculationUtils.div(new BigDecimal(sourceFileSize), CalculationUtils.DEFAULT_SIZE, 2);
        if (fileSize.compareTo(CalculationUtils.DEFAULT_SIZE) <= 0) {
            return fileSize.toString() + " KB";
        }
        // MB
        fileSize = CalculationUtils.div(fileSize, CalculationUtils.DEFAULT_SIZE, 2);
        if (fileSize.compareTo(CalculationUtils.DEFAULT_SIZE) <= 0) {
            return fileSize.toString() + " MB";
        }
        // GB
        fileSize = CalculationUtils.div(fileSize, CalculationUtils.DEFAULT_SIZE, 2);
        return fileSize.toString() + " GB";
    }

    public static void main(String[] args) {
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
        String join = join(firstSet, secondSet, "_");
        System.out.println(join);
        String[] joins = joins(firstSet, secondSet, threeSet);
        System.out.println(Arrays.toString(joins));

        // 计算公式
        String content = "word分词不满足需求，咋办？";
        System.out.println(isContainChinese(content));
        // 分割计算公式：将中英文分割开
        List<String> characters = splitEnglishCharacter(content);
        for (String e : characters) {
            System.out.println(e + "\t==>" + isContainChinese(e));
        }
        String result = StringUtils.join(characters, "");
        System.out.println(result);
    }
}
