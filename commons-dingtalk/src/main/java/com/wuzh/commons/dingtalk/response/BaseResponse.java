package com.wuzh.commons.dingtalk.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 类BaseResponse的实现描述：响应基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 12:40:20
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public abstract class BaseResponse {

    /**
     * 返回码
     */
    @SerializedName(value = "errcode")
    private Integer errCode;
    /**
     * 返回码描述
     */
    @SerializedName(value = "errmsg")
    private String errMsg;

}