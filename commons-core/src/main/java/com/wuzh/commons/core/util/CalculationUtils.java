/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.core.util;

import java.math.BigDecimal;

/**
 * 类CalculationUtils的实现描述：数值计算工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-04-23 19:23:47
 * @version v2.2.6
 * @since JDK 1.8
 */
public class CalculationUtils {

    /**
     * 默认值：0
     */
    public static final BigDecimal DEFAULT_ZERO = new BigDecimal("0");
    /**
     * 默认值：1
     */
    public static final BigDecimal DEFAULT_ONE = new BigDecimal("1");
    /**
     * 默认值：10
     */
    public static final BigDecimal DEFAULT_TEN = new BigDecimal("10");
    /**
     * 默认值：100
     */
    public static final BigDecimal DEFAULT_ONE_HUNDRED = new BigDecimal("100");

    public static BigDecimal add(BigDecimal v1, BigDecimal... array) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (array == null || array.length == 0) {
            return v1;
        }
        synchronized (v1) {
            BigDecimal result = new BigDecimal(v1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
            for (BigDecimal num : array) {
                if (num == null) {
                    continue;
                }
                result = add(result, num);
            }
            return result.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * v1+v2 保留两位
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return add(v1, v2, 2);
    }

    /**
     * v1+v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale 保留小数位数
     * @return
     */
    public static synchronized BigDecimal add(BigDecimal v1, BigDecimal v2, int scale) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        return v1.add(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * v1+v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式，eg：BigDecimal.ROUND_HALF_UP
     * @return
     */
    public static synchronized BigDecimal add(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        return v1.add(v2).setScale(scale, roundingMode);
    }

    public static BigDecimal sub(BigDecimal v1, BigDecimal... array) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (array == null || array.length == 0) {
            return v1;
        }
        synchronized (v1) {
            BigDecimal result = new BigDecimal(v1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
            for (BigDecimal num : array) {
                if (num == null) {
                    continue;
                }
                result = sub(result, num);
            }
            return result.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * v1-v2 保留两位
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return sub(v1, v2, 2);
    }

    /**
     * v1-v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale 保留小数位数
     * @return
     */
    public static synchronized BigDecimal sub(BigDecimal v1, BigDecimal v2, int scale) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        return v1.subtract(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * v1-v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式，eg：BigDecimal.ROUND_HALF_UP
     * @return
     */
    public static synchronized BigDecimal sub(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        return v1.subtract(v2).setScale(scale, roundingMode);
    }

    public static BigDecimal mul(BigDecimal v1, BigDecimal... array) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (array == null || array.length == 0) {
            return v1;
        }
        synchronized (v1) {
            BigDecimal result = new BigDecimal(v1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    continue;
                }
                result = mul(result, array[i]);
            }
            return result.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * v1*v2 默认保留两位小数
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        return mul(v1, v2, 2);
    }

    /**
     * v1*v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale 保留小数位数
     * @return
     */
    public static synchronized BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        return v1.multiply(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * v1*v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式，eg：BigDecimal.ROUND_HALF_UP
     * @return
     */
    public static synchronized BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        return v1.multiply(v2).setScale(scale, roundingMode);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal... array) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (array == null || array.length == 0) {
            return v1;
        }
        synchronized (v1) {
            BigDecimal result = new BigDecimal(v1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    continue;
                }
                result = div(result, array[i]);
            }
            return result.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * v1/v2 默认保留两位小数
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        // 四舍五入,保留2位小数
        return div(v1, v2, 2);
    }

    /**
     * v1/v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale 保留小数位数
     * @return
     */
    public static synchronized BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2.compareTo(CalculationUtils.DEFAULT_ZERO) != 0) {
            return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP); // 四舍五入
        }
        return CalculationUtils.DEFAULT_ZERO;
    }

    /**
     * v1/v2 保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式，eg：BigDecimal.ROUND_HALF_UP
     * @return
     */
    public static synchronized BigDecimal div(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
        if (v1 == null) {
            v1 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2 == null) {
            v2 = CalculationUtils.DEFAULT_ZERO;
        }
        if (v2.compareTo(CalculationUtils.DEFAULT_ZERO) != 0) {
            return v1.divide(v2, scale, roundingMode); // 四舍五入
        }
        return CalculationUtils.DEFAULT_ZERO;
    }

    public static void main(String[] args) {
        /*for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BigDecimal MALL_CTT_BASIC_DEDUCTION = new BigDecimal(14899.18);
                    BigDecimal MALL_CTT_PROMOTION_DEDUCTION = new BigDecimal(-544.19);
                    BigDecimal MALL_PROMOTION_DEDUCTION = new BigDecimal(-4663.81);
                    BigDecimal LEASE_FEE = new BigDecimal(1006.22);
                    BigDecimal result = add(MALL_CTT_BASIC_DEDUCTION, MALL_CTT_PROMOTION_DEDUCTION, MALL_PROMOTION_DEDUCTION, LEASE_FEE);
                    System.out.println(*//*Thread.currentThread().getName() + ":" +*//* result);
                }
            }).start();
        }*/

        BigDecimal mul = CalculationUtils.mul(
                new BigDecimal("1018227.70"),
                new BigDecimal("1.13")
        );
        System.out.println(mul);

        BigDecimal result = CalculationUtils.div(
                mul,
                new BigDecimal("1.16"),
                2
        );
        System.out.println(result);
    }

}