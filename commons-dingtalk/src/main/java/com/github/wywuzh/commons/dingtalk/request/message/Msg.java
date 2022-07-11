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
package com.github.wywuzh.commons.dingtalk.request.message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类Msg的实现描述：消息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class Msg implements Serializable {
    private static final long serialVersionUID = 5196554184817810558L;

    /**
     * 消息类型
     */
    @SerializedName(value = "msgtype")
    private MsgType msgType;

    /**
     * 语音消息
     */
    @SerializedName(value = "voice")
    private Voice voice;
    /**
     * 图片消息
     */
    @SerializedName(value = "image")
    private Image image;
    /**
     * OA消息
     */
    @SerializedName(value = "oa")
    private Oa oa;
    /**
     * 文件消息
     */
    @SerializedName(value = "file")
    private File file;
    /**
     * 卡片消息
     */
    @SerializedName(value = "action_card")
    private ActionCard actionCard;
    /**
     * 链接消息
     */
    @SerializedName(value = "link")
    private Link link;
    /**
     * markdown消息
     */
    @SerializedName(value = "markdown")
    private Markdown markdown;
    /**
     * 文本消息
     */
    @SerializedName(value = "text")
    private Text text;


    /**
     * 类Msg的实现描述：语音消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Voice implements Serializable {
        private static final long serialVersionUID = -7928964438195358015L;

        /**
         * 正整数，小于60，表示音频时长
         */
        @SerializedName(value = "duration")
        private String duration;
        /**
         * 媒体文件id。2MB，播放长度不超过60s，AMR格式
         */
        @SerializedName(value = "media_id")
        private String mediaId;
    }

    /**
     * 类Msg的实现描述：图片消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Image implements Serializable {
        private static final long serialVersionUID = 5338859934323931240L;

        /**
         * 图片消息
         */
        @SerializedName(value = "media_id")
        private String mediaId;
    }

    /**
     * 类Msg的实现描述：消息头部内容
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Head implements Serializable {
        private static final long serialVersionUID = 754913301231045992L;

        /**
         * 消息头部的背景颜色。长度限制为8个英文字符，其中前2为表示透明度，后6位表示颜色值。不要添加0x
         */
        @SerializedName(value = "bgcolor")
        private String bgcolor;
        /**
         * 消息的头部标题 (向普通会话发送时有效，向企业会话发送时会被替换为微应用的名字)，长度限制为最多10个字符
         */
        @SerializedName(value = "text")
        private String text;
    }

    /**
     * 类Msg的实现描述：消息状态栏
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class StatusBar implements Serializable {
        private static final long serialVersionUID = 754913301231045992L;

        /**
         * 状态栏文案。eg："进行中"
         */
        @SerializedName(value = "status_value")
        private String statusValue;
        /**
         * 状态栏背景色，默认为黑色，推荐0xFF加六位颜色值。eg："0xFFF65E5E"
         */
        @SerializedName(value = "status_bg")
        private String statusBg;
    }

    /**
     * 类Msg的实现描述：消息体的表单，最多显示6个，超过会被隐藏
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Form implements Serializable {
        private static final long serialVersionUID = 3005232141246565414L;

        /**
         * 消息体的关键字
         */
        @SerializedName(value = "key")
        private String key;
        /**
         * 消息体的关键字对应的值
         */
        @SerializedName(value = "value")
        private String value;
    }

    /**
     * 类Msg的实现描述：单行富文本信息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Rich implements Serializable {
        private static final long serialVersionUID = 9099804484322830847L;

        /**
         * 单行富文本信息的数目
         */
        @SerializedName(value = "num")
        private String num;
        /**
         * 单行富文本信息的单位
         */
        @SerializedName(value = "unit")
        private String unit;
    }

    /**
     * 类Msg的实现描述：消息体
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Body implements Serializable {
        private static final long serialVersionUID = 754913301231045992L;

        /**
         * 自定义的附件数目。此数字仅供显示，钉钉不作验证
         */
        @SerializedName(value = "file_count")
        private String fileCount;
        /**
         * 消息体中的图片，支持图片资源@mediaId
         */
        @SerializedName(value = "image")
        private String image;
        /**
         * 消息体的表单，最多显示6个，超过会被隐藏
         */
        @SerializedName(value = "form")
        private List<Form> form;
        /**
         * 自定义的作者名字
         */
        @SerializedName(value = "author")
        private String author;
        /**
         * 单行富文本信息
         */
        @SerializedName(value = "rich")
        private Rich rich;
        /**
         * 消息体的标题，建议50个字符以内
         */
        @SerializedName(value = "title")
        private String title;
        /**
         * 消息体的内容，最多显示3行
         */
        @SerializedName(value = "content")
        private String content;
    }

    /**
     * 类Msg的实现描述：OA消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Oa implements Serializable {
        private static final long serialVersionUID = 754913301231045992L;

        /**
         * 消息头部内容
         */
        @SerializedName(value = "head")
        private Head head;
        /**
         * PC端点击消息时跳转到的地址
         */
        @SerializedName(value = "pc_message_url")
        private String pcMessageUrl;
        /**
         * 消息状态栏
         */
        @SerializedName(value = "status_bar")
        private StatusBar statusBar;
        /**
         * 消息体
         */
        @SerializedName(value = "body")
        private Body body;
        /**
         * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接
         */
        @SerializedName(value = "message_url")
        private String messageUrl;
    }

    /**
     * 类Msg的实现描述：文件消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class File implements Serializable {

        /**
         * 媒体文件id。引用的媒体文件最大10MB
         */
        @SerializedName(value = "media_id")
        private String mediaId;
    }

    /**
     * 类Msg的实现描述：使用独立跳转ActionCard样式时的按钮列表；必须与btn_orientation同时设置
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class BtnJsonList implements Serializable {
        private static final long serialVersionUID = -235566030653634629L;

        /**
         * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符
         */
        @SerializedName(value = "action_url")
        private String actionUrl;
        /**
         * 使用独立跳转ActionCard样式时的按钮的标题，最长20个字符
         */
        @SerializedName(value = "title")
        private String title;
    }

    /**
     * 类Msg的实现描述：卡片消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class ActionCard implements Serializable {
        private static final long serialVersionUID = -1796419081903263560L;

        /**
         * 使用独立跳转ActionCard样式时的按钮列表；必须与btn_orientation同时设置
         */
        @SerializedName(value = "btn_json_list")
        private BtnJsonList btnJsonList;
        /**
         * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符
         */
        @SerializedName(value = "single_url")
        private String singleUrl;
        /**
         * 使用独立跳转ActionCard样式时的按钮排列方式，竖直排列(0)，横向排列(1)；必须与btn_json_list同时设置
         */
        @SerializedName(value = "btn_orientation")
        private String btnOrientation;
        /**
         * 使用整体跳转ActionCard样式时的标题，必须与single_url同时设置，最长20个字符
         */
        @SerializedName(value = "single_title")
        private String singleTitle;
        /**
         * 消息内容，支持markdown，语法参考标准markdown语法。建议1000个字符以内
         */
        @SerializedName(value = "markdown")
        private String markdown;
        /**
         * 透出到会话列表和通知的文案，最长64个字符
         */
        @SerializedName(value = "title")
        private String title;
    }

    /**
     * 类Msg的实现描述：链接消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Link implements Serializable {
        private static final long serialVersionUID = 9155626908221959834L;

        /**
         * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接
         */
        @SerializedName(value = "picUrl")
        private String picUrl;
        /**
         * 图片地址
         */
        @SerializedName(value = "messageUrl")
        private String messageUrl;
        /**
         * 消息标题，建议100字符以内
         */
        @SerializedName(value = "title")
        private String title;
        /**
         * 消息描述，建议500字符以内
         */
        @SerializedName(value = "text")
        private String text;
    }

    /**
     * 类Msg的实现描述：markdown消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Markdown implements Serializable {
        private static final long serialVersionUID = 2675680131459142498L;

        /**
         * 首屏会话透出的展示内容
         */
        @SerializedName(value = "title")
        private String title;
        /**
         * markdown格式的消息，建议500字符以内
         */
        @SerializedName(value = "text")
        private String text;
    }

    /**
     * 类Msg的实现描述：文本消息
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 09:48:23
     * @since JDK 1.8
     */
    @Data
    public static class Text implements Serializable {
        private static final long serialVersionUID = -7585326803743674388L;

        /**
         * 文本消息
         */
        @SerializedName(value = "content")
        private String content;
    }

}
