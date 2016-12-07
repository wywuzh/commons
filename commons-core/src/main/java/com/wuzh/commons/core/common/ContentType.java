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
package com.wuzh.commons.core.common;

/**
 * 类ContentType.java的实现描述：内容类型
 * 
 * <pre>
 *  Content-Type，内容类型，一般是指网页中存在的Content-Type，用于定义网络文件的类型和网页的编码，决定浏览器将以什么形式、什么编码读取这个文件。
 *  比如用PHP输出图片文件、JSON数据、XML文件等非HTML内容时，就必须用header函数来指定Content-Type，才能达到输出一张图片或是其它指定内容类型的需求。
 *  
 * 参考网址：
 *  1）http://tools.jb51.net/table/http_content_type
 *  2）http://baike.baidu.com/link?url=6tczhCCUnht0mOBULwZC9YoQ3isl2lApQoLgQM7174jVf_RwKVZ_VWnkq3vqTGwPbuJjFtdQ61npCzqHudqQuK
 *  3）http://tool.oschina.net/commons
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:20:18
 * @version v1.0.0
 * @since JDK 1.7
 */
public class ContentType {
    /** --------------------- Application Type ------------------------------ */
    /**
     * 二进制流，不知道下载文件类型
     */
    public static final String APPLICATION_POINT_STREAM = "application/octet-stream";
    /**
     * PDF（Portable Document Format的简称，意为“便携式文件格式”）
     */
    public static final String APPLICATION_POINT_PDF = "application/pdf";
    /**
     * PostScript（PS）是主要用于电子产业和桌面出版领域的一种页面描述语言和编程语言。
     */
    public static final String APPLICATION_POINT_AI = "application/postscript";
    /**
     * Atom feeds
     */
    public static final String APPLICATION_POINT_ATOM_XML = "application/atom+xml";
    /**
     * 相当于application/javascript但是严格的处理规则
     */
    public static final String APPLICATION_POINT_ECMASCRIPT = "application/ecmascript";
    /**
     * ECMAScript/JavaScript（相当于application/ecmascript但是宽松的处理规则）
     */
    public static final String APPLICATION_POINT_javascript = "application/javascript";
    /**
     * EDI ANSI ASC X12数据
     */
    public static final String APPLICATION_POINT_EDIX12 = "application/EDI-X12";
    /**
     * EDI EDIFACT数据
     */
    public static final String APPLICATION_POINT_EDIFACT = "application/EDIFACT";
    /**
     * JSON（JavaScript Object Notation）
     */
    public static final String APPLICATION_POINT_JSON = "application/json";
    /**
     * Ogg, 视频文件格式
     */
    public static final String APPLICATION_POINT_OGG = "application/ogg";
    /**
     * 资源描述框架（Resource Description Framework，缩写
     * RDF），是万维网联盟（W3C）提出的一组标记语言的技术标准，以便更为丰富地描述和表达网络资源的内容与结构。
     */
    public static final String APPLICATION_POINT_RDF_XML = "application/rdf+xml";
    /**
     * RSS（Really Simple
     * Syndication,简易信息聚合）是一种消息来源格式规范，用以聚合经常发布更新数据的网站，例如博客文章、新闻、音频或视频的网摘
     */
    public static final String APPLICATION_POINT_RSS_XML = "application/rss+xml";
    /**
     * 简单对象访问协议（SOAP，全写为Simple Object Access
     * Protocol）是交换数据的一种协议规范，使用在计算机网络Web服务（web service）中，交换带结构信息。
     */
    public static final String APPLICATION_POINT_SOAP_XML = "application/soap+xml";
    /**
     * Web开放字体格式（Web Open Font
     * Format，简称WOFF）是一种网页所采用的字体格式标准。（推荐使用；使用application/x-font-woff直到它变为官方标准）
     */
    public static final String APPLICATION_POINT_WOFF = "application/font-woff";
    /**
     * 可扩展超文本标记语言（eXtensible HyperText Markup
     * Language，XHTML），是一种标记语言，表现方式与超文本标记语言（HTML）类似，不过语法上更加严格。
     */
    public static final String APPLICATION_POINT_XHTML = "application/xhtml+xml";
    /**
     * 可扩展标记语言（英语：eXtensible Markup Language，简称: XML），是一种标记语言。
     */
    public static final String APPLICATION_POINT_XML = "application/xml";
    /**
     * XML文件的文件型别定义（Document Type Definition）
     * 可以看成一个或者多个XML文件的模板，在这里可以定义XML文件中的元素、元素的属性、元素的排列方式、元素包含的内容等等。
     */
    public static final String APPLICATION_POINT_DTD = "application/xml-dtd";
    /**
     * 二进制优化封装协议(Xmlbinary Optimized Packaging)
     */
    public static final String APPLICATION_POINT_XOP_XML = "application/xop+xml";
    /**
     * ZIP压缩文件
     */
    public static final String APPLICATION_POINT_ZIP = "application/zip";
    /**
     * Gzip是若干种文件压缩程序的简称，通常指GNU计划的实现，此处的gzip代表GNU zip。
     */
    public static final String APPLICATION_POINT_GZIP = "application/gzip";
    /**
     * XLS 就是 Microsoft Excel 工作表，是一种非常常用的电子表格格式。
     */
    public static final String APPLICATION_POINT_XLS = "application/x-xls";

    public static final String APPLICATION_POINT_001 = "application/x-001";
    public static final String APPLICATION_POINT_301 = "application/x-301";
    public static final String APPLICATION_POINT_906 = "application/x-906";
    public static final String APPLICATION_POINT_A11 = "application/x-a11";
    public static final String APPLICATION_POINT_ANV = "application/x-anv";
    public static final String APPLICATION_POINT_AWF = "application/vnd.adobe.workflow";
    public static final String APPLICATION_POINT_BMP = "application/x-bmp";
    public static final String APPLICATION_POINT_BOT = "application/x-bot";
    public static final String APPLICATION_POINT_C4T = "application/x-c4t";
    public static final String APPLICATION_POINT_C90 = "application/x-c90";
    public static final String APPLICATION_POINT_CAL = "application/x-cals";
    public static final String APPLICATION_POINT_CAT = "application/s-pki.seccat";
    public static final String APPLICATION_POINT_CDF = "application/x-netcdf";
    public static final String APPLICATION_POINT_CDR = "application/x-cdr";
    public static final String APPLICATION_POINT_CEL = "application/x-cel";
    public static final String APPLICATION_POINT_CER = "application/x-x509-ca-cert";
    public static final String APPLICATION_POINT_CG4 = "application/x-g4";
    public static final String APPLICATION_POINT_CGM = "application/x-cgm";
    public static final String APPLICATION_POINT_CIT = "application/x-cit";
    public static final String APPLICATION_POINT_CMP = "application/x-cmp";
    public static final String APPLICATION_POINT_CMX = "application/x-cmx";
    public static final String APPLICATION_POINT_COT = "application/x-cot";
    public static final String APPLICATION_POINT_CRL = "application/pkix-crl";
    public static final String APPLICATION_POINT_CRT = "application/x-x509-ca-cert";
    public static final String APPLICATION_POINT_CSI = "application/x-csi";
    public static final String APPLICATION_POINT_CUT = "application/x-cut";
    public static final String APPLICATION_POINT_DBF = "application/x-dbf";
    public static final String APPLICATION_POINT_DBM = "application/x-dbm";
    public static final String APPLICATION_POINT_DBX = "application/x-dbx";
    public static final String APPLICATION_POINT_DCX = "application/x-dcx";
    public static final String APPLICATION_POINT_DER = "application/x-x509-ca-cert";
    public static final String APPLICATION_POINT_DGN = "application/x-dgn";
    public static final String APPLICATION_POINT_DIB = "application/x-dib";
    public static final String APPLICATION_POINT_DLL = "application/x-msdownload";
    public static final String APPLICATION_POINT_DOC = "application/msword";
    public static final String APPLICATION_POINT_DOT = "application/msword";
    public static final String APPLICATION_POINT_DRW = "application/x-drw";
    public static final String APPLICATION_POINT_X_DWF = "application/x-dwf";
    public static final String APPLICATION_POINT_DWG = "application/x-dwg";
    public static final String APPLICATION_POINT_DXB = "application/x-dxb";
    public static final String APPLICATION_POINT_DXF = "application/x-dxf";
    public static final String APPLICATION_POINT_EDN = "application/vnd.adobe.edn";
    public static final String APPLICATION_POINT_EMF = "application/x-emf";
    public static final String APPLICATION_POINT_EPI = "application/x-epi";
    public static final String APPLICATION_POINT_X_EPS = "application/x-ps";
    public static final String APPLICATION_POINT_EPS = "application/postscript";
    public static final String APPLICATION_POINT_ETD = "application/x-ebx";
    public static final String APPLICATION_POINT_EXE = "application/x-msdownload";
    public static final String APPLICATION_POINT_FDF = "application/vnd.fdf";
    public static final String APPLICATION_POINT_FIF = "application/fractals";
    public static final String APPLICATION_POINT_FRM = "application/x-frm";
    public static final String APPLICATION_POINT_G4 = "application/x-g4";
    public static final String APPLICATION_POINT_GBR = "application/x-gbr";
    public static final String APPLICATION_POINT_GCD = "application/x-gcd";
    public static final String APPLICATION_POINT_GL2 = "application/x-gl2";
    public static final String APPLICATION_POINT_GP4 = "application/x-gp4";
    public static final String APPLICATION_POINT_HGL = "application/x-hgl";
    public static final String APPLICATION_POINT_HMR = "application/x-hmr";
    public static final String APPLICATION_POINT_HPG = "application/x-hpgl";
    public static final String APPLICATION_POINT_HPL = "application/x-hpl";
    public static final String APPLICATION_POINT_HQX = "application/mac-binhex40";
    public static final String APPLICATION_POINT_HRF = "application/x-hrf";
    public static final String APPLICATION_POINT_HTA = "application/hta";
    public static final String APPLICATION_POINT_APP_ICO = "application/x-ico";
    public static final String APPLICATION_POINT_IFF = "application/x-iff";
    public static final String APPLICATION_POINT_IG4 = "application/x-g4";
    public static final String APPLICATION_POINT_IGS = "application/x-igs";
    public static final String APPLICATION_POINT_III = "application/x-iphone";
    public static final String APPLICATION_POINT_IMG = "application/x-img";
    public static final String APPLICATION_POINT_INS = "application/x-internet-signup";
    public static final String APPLICATION_POINT_ISP = "application/x-internet-signup";
    public static final String APPLICATION_POINT_LBM = "application/x-lbm";
    public static final String APPLICATION_POINT_MAC = "application/x-mac";
    public static final String APPLICATION_POINT_MAN = "application/x-troff-man";
    public static final String APPLICATION_POINT_MDB = "application/msaccess";
    public static final String APPLICATION_POINT_X_MDB = "application/x-mdb";
    public static final String APPLICATION_POINT_MFP = "application/x-shockwave-flash";
    public static final String APPLICATION_POINT_MI = "application/x-mi";
    public static final String APPLICATION_POINT_MIL = "application/x-mil";
    public static final String APPLICATION_POINT_MOCHA = "application/x-javascript";
    public static final String APPLICATION_POINT_MPD = "application/-project";
    public static final String APPLICATION_POINT_LS = "application/x-javascript";
    public static final String APPLICATION_POINT_LTR = "application/x-ltr";
    public static final String APPLICATION_POINT_LAR = "application/x-laplayer-reg";
    public static final String APPLICATION_POINT_LATEX = "application/x-latex";
    public static final String APPLICATION_POINT_X_JPG = "application/x-jpg";
    public static final String APPLICATION_POINT_X_JS = "application/x-javascript";
    public static final String APPLICATION_POINT_X_JPE = "application/x-jpe";
    public static final String APPLICATION_POINT_MPT = "application/-project";
    public static final String APPLICATION_POINT_MPP = "application/-project";
    public static final String APPLICATION_POINT_MPW = "application/s-project";
    public static final String APPLICATION_POINT_MPX = "application/-project";
    public static final String APPLICATION_POINT_MXP = "application/x-mmxp";
    public static final String APPLICATION_POINT_NRF = "application/x-nrf";
    public static final String APPLICATION_POINT_OUT = "application/x-out";
    public static final String APPLICATION_POINT_P10 = "application/pkcs10";
    public static final String APPLICATION_POINT_P12 = "application/x-pkcs12";
    public static final String APPLICATION_POINT_P7B = "application/x-pkcs7-certificates";
    public static final String APPLICATION_POINT_P7C = "application/pkcs7-mime";
    public static final String APPLICATION_POINT_P7M = "application/pkcs7-mime";
    public static final String APPLICATION_POINT_P7R = "application/x-pkcs7-certreqresp";
    public static final String APPLICATION_POINT_P7S = "application/pkcs7-signature";
    public static final String APPLICATION_POINT_PC5 = "application/x-pc5";
    public static final String APPLICATION_POINT_PCI = "application/x-pci";
    public static final String APPLICATION_POINT_PCL = "application/x-pcl";
    public static final String APPLICATION_POINT_PCX = "application/x-pcx";
    public static final String APPLICATION_POINT_PDX = "application/vnd.adobe.pdx";
    public static final String APPLICATION_POINT_PFX = "application/x-pkcs12";
    public static final String APPLICATION_POINT_PGL = "application/x-pgl";
    public static final String APPLICATION_POINT_PIC = "application/x-pic";
    public static final String APPLICATION_POINT_PKO = "application-pki.pko";
    public static final String APPLICATION_POINT_PL = "application/x-perl";
    public static final String APPLICATION_POINT_PLT = "application/x-plt";
    public static final String APPLICATION_POINT_X_PNG = "application/x-png";
    public static final String APPLICATION_POINT_POT = "applications-powerpoint";
    public static final String APPLICATION_POINT_PPA = "application/vs-powerpoint";
    public static final String APPLICATION_POINT_PPM = "application/x-ppm";
    public static final String APPLICATION_POINT_PPS = "application-powerpoint";
    public static final String APPLICATION_POINT_PPT = "applications-powerpoint";
    public static final String APPLICATION_POINT_X_PPT = "application/x-ppt";
    public static final String APPLICATION_POINT_PR = "application/x-pr";
    public static final String APPLICATION_POINT_PRF = "application/pics-rules";
    public static final String APPLICATION_POINT_PRN = "application/x-prn";
    public static final String APPLICATION_POINT_PRT = "application/x-prt";
    public static final String APPLICATION_POINT_X_PS = "application/x-ps";
    public static final String APPLICATION_POINT_PS = "application/postscript";
    public static final String APPLICATION_POINT_PTN = "application/x-ptn";
    public static final String APPLICATION_POINT_PWZ = "application/powerpoint";
    public static final String APPLICATION_POINT_RAS = "application/x-ras";
    public static final String APPLICATION_POINT_RAT = "application/rat-file";
    public static final String APPLICATION_POINT_REC = "application/vnd.rn-recording";
    public static final String APPLICATION_POINT_RED = "application/x-red";
    public static final String APPLICATION_POINT_RGB = "application/x-rgb";
    public static final String APPLICATION_POINT_RJS = "application/vnd.rn-realsystem-rjs";
    public static final String APPLICATION_POINT_RJT = "application/vnd.rn-realsystem-rjt";
    public static final String APPLICATION_POINT_RLC = "application/x-rlc";
    public static final String APPLICATION_POINT_RLE = "application/x-rle";
    public static final String APPLICATION_POINT_RM = "application/vnd.rn-realmedia";
    public static final String APPLICATION_POINT_RMF = "application/vnd.adobe.rmf";
    public static final String APPLICATION_POINT_RMJ = "application/vnd.rn-realsystem-rmj";
    public static final String APPLICATION_POINT_RMP = "application/vnd.rn-rn_music_package";
    public static final String APPLICATION_POINT_RMS = "application/vnd.rn-realmedia-secure";
    public static final String APPLICATION_POINT_RMVB = "application/vnd.rn-realmedia-vbr";
    public static final String APPLICATION_POINT_RMX = "application/vnd.rn-realsystem-rmx";
    public static final String APPLICATION_POINT_RNX = "application/vnd.rn-realplayer";
    public static final String APPLICATION_POINT_RSML = "application/vnd.rn-rsml";
    public static final String APPLICATION_POINT_RTF = "application/msword";
    public static final String APPLICATION_POINT_X_RTF = "application/x-rtf";
    public static final String APPLICATION_POINT_SAM = "application/x-sam";
    public static final String APPLICATION_POINT_SAT = "application/x-sat";
    public static final String APPLICATION_POINT_SDP = "application/sdp";
    public static final String APPLICATION_POINT_SDW = "application/x-sdw";
    public static final String APPLICATION_POINT_SIT = "application/x-stuffit";
    public static final String APPLICATION_POINT_SLB = "application/x-slb";
    public static final String APPLICATION_POINT_SLD = "application/x-sld";
    public static final String APPLICATION_POINT_SMI = "application/smil";
    public static final String APPLICATION_POINT_SMIL = "application/smil";
    public static final String APPLICATION_POINT_SMK = "application/x-smk";
    public static final String APPLICATION_POINT_SPC = "application/x-pkcs7-certificates";
    public static final String APPLICATION_POINT_SPL = "application/futuresplash";
    public static final String APPLICATION_POINT_SSM = "application/streamingmedia";
    public static final String APPLICATION_POINT_SST = "application-pki.certstore";
    public static final String APPLICATION_POINT_STL = "application/-pki.stl";
    public static final String APPLICATION_POINT_STY = "application/x-sty";
    public static final String APPLICATION_POINT_SWF = "application/x-shockwave-flash";
    public static final String APPLICATION_POINT_TDF = "application/x-tdf";
    public static final String APPLICATION_POINT_TG4 = "application/x-tg4";
    public static final String APPLICATION_POINT_TGA = "application/x-tga";
    public static final String APPLICATION_POINT_X_TIF = "application/x-tif";
    public static final String APPLICATION_POINT_TORRENT = "application/x-bittorrent";
    public static final String APPLICATION_POINT_UIN = "application/x-icq";
    public static final String APPLICATION_POINT_VDA = "application/x-vda";
    public static final String APPLICATION_POINT_VDX = "application/vnd.visio";
    public static final String APPLICATION_POINT_VPG = "application/x-vpeg005";
    public static final String APPLICATION_POINT_VSD = "application/vnd.visio";
    public static final String APPLICATION_POINT_X_VSD = "application/x-vsd";
    public static final String APPLICATION_POINT_VSS = "application/vnd.visio";
    public static final String APPLICATION_POINT_VST = "application/vnd.visio";
    public static final String APPLICATION_POINT_X_VST = "application/x-vst";
    public static final String APPLICATION_POINT_VSW = "application/vnd.visio";
    public static final String APPLICATION_POINT_VSX = "application/vnd.visio";
    public static final String APPLICATION_POINT_VTX = "application/vnd.visio";
    public static final String APPLICATION_POINT_WB1 = "application/x-wb1";
    public static final String APPLICATION_POINT_WB2 = "application/x-wb2";
    public static final String APPLICATION_POINT_WB3 = "application/x-wb3";
    public static final String APPLICATION_POINT_WIZ = "application/msword";
    public static final String APPLICATION_POINT_WK3 = "application/x-wk3";
    public static final String APPLICATION_POINT_WK4 = "application/x-wk4";
    public static final String APPLICATION_POINT_WKQ = "application/x-wkq";
    public static final String APPLICATION_POINT_WKS = "application/x-wks";
    public static final String APPLICATION_POINT_WMD = "application/x-ms-wmd";
    public static final String APPLICATION_POINT_WMF = "application/x-wmf";
    public static final String APPLICATION_POINT_WMZ = "application/x-ms-wmz";
    public static final String APPLICATION_POINT_WP6 = "application/x-wp6";
    public static final String APPLICATION_POINT_WPD = "application/x-wpd";
    public static final String APPLICATION_POINT_WPG = "application/x-wpg";
    public static final String APPLICATION_POINT_WPL = "application/-wpl";
    public static final String APPLICATION_POINT_WQ1 = "application/x-wq1";
    public static final String APPLICATION_POINT_WR1 = "application/x-wr1";
    public static final String APPLICATION_POINT_WRI = "application/x-wri";
    public static final String APPLICATION_POINT_WRK = "application/x-wrk";
    public static final String APPLICATION_POINT_WS = "application/x-ws";
    public static final String APPLICATION_POINT_WS2 = "application/x-ws";
    public static final String APPLICATION_POINT_XDP = "application/vnd.adobe.xdp";
    public static final String APPLICATION_POINT_XFD = "application/vnd.adobe.xfd";
    public static final String APPLICATION_POINT_XFDF = "application/vnd.adobe.xfdf";
    public static final String APPLICATION_POINT_EXCEL = "application/-excel";
    public static final String APPLICATION_POINT_XLW = "application/x-xlw";
    public static final String APPLICATION_POINT_XWD = "application/x-xwd";
    public static final String APPLICATION_POINT_X_B = "application/x-x_b";
    public static final String APPLICATION_POINT_X_T = "application/x-x_t";
    public static final String APPLICATION_POINT_ICB = "application/x-icb";

    /** --------------------- Text Type ------------------------------ */
    public static final String TEXT_POINT_323 = "text/h323";
    public static final String TEXT_POINT_XML = "text/xml";
    public static final String TEXT_POINT_XQ = "text/xml";
    public static final String TEXT_POINT_XQL = "text/xml";
    public static final String TEXT_POINT_XQUERY = "text/xml";
    public static final String TEXT_POINT_XSD = "text/xml";
    public static final String TEXT_POINT_XSL = "text/xml";
    public static final String TEXT_POINT_XSLT = "text/xml";
    public static final String TEXT_POINT_XHTML = "text/html";
    public static final String TEXT_POINT_XDR = "text/xml";
    public static final String TEXT_POINT_WSC = "text/scriptlet";
    public static final String TEXT_POINT_WSDL = "text/xml";
    public static final String TEXT_POINT_WML = "text/vnd.wap.wml";
    public static final String TEXT_POINT_VXML = "text/xml";
    public static final String TEXT_POINT_TSD = "text/xml";
    public static final String TEXT_POINT_TXT = "text/plain";
    public static final String TEXT_POINT_ULS = "text/iuls";
    public static final String TEXT_POINT_VCF = "text/x-vcard";
    public static final String TEXT_POINT_VML = "text/xml";
    public static final String TEXT_POINT_TLD = "text/xml";
    public static final String TEXT_POINT_STM = "text/html";
    public static final String TEXT_POINT_SVG = "text/xml";
    public static final String TEXT_POINT_SOL = "text/plain";
    public static final String TEXT_POINT_SOR = "text/plain";
    public static final String TEXT_POINT_ASA = "text/asa";
    public static final String TEXT_POINT_ASP = "text/asp";
    public static final String TEXT_POINT_BIZ = "text/xml";
    public static final String TEXT_POINT_RT = "text/vnd.rn-realtext";
    public static final String TEXT_POINT_SPP = "text/xml";
    public static final String TEXT_POINT_HTC = "text/x-component";
    public static final String TEXT_POINT_HTM = "text/html";
    public static final String TEXT_POINT_HTML = "text/html";
    public static final String TEXT_POINT_HTT = "text/webviewhtml";
    public static final String TEXT_POINT_HTX = "text/html";
    public static final String TEXT_POINT_R3T = "text/vnd.rn-realtext3d";
    public static final String TEXT_POINT_RDF = "text/xml";
    public static final String TEXT_POINT_PLG = "text/html";
    public static final String TEXT_POINT_ODC = "text/x-ms-odc";
    public static final String TEXT_POINT_MTX = "text/xml";
    public static final String TEXT_POINT_MML = "text/xml";
    public static final String TEXT_POINT_MATH = "text/xml";
    public static final String TEXT_POINT_JSP = "text/html";
    public static final String TEXT_POINT_FO = "text/xml";
    public static final String TEXT_POINT_CML = "text/xml";
    public static final String TEXT_POINT_CSS = "text/css";
    public static final String TEXT_POINT_DCD = "text/xml";
    public static final String TEXT_POINT_DTD = "text/xml";
    public static final String TEXT_POINT_ENT = "text/xml";

    /** --------------------- Video Type ------------------------------ */
    public static final String VIDEO_POINT_ASF = "video/x-ms-asf";
    public static final String VIDEO_POINT_ASX = "video/x-ms-asf";
    public static final String VIDEO_POINT_AVI = "video/avi";
    public static final String VIDEO_POINT_RV = "video/vnd.rn-realvideo";
    public static final String VIDEO_POINT_MOVIE = "video/x-sgi-movie";
    public static final String VIDEO_POINT_MP2V = "video/mpeg";
    /**
     * MP4，全称MPEG-4 Part 14，是一种使用MPEG-4的多媒体计算机文件格式，扩展名为.mp4，以存储数字音频及数字视频为主。
     */
    public static final String VIDEO_POINT_MP4 = "video/mp4";
    public static final String VIDEO_POINT_MPA = "video/x-mpg";
    public static final String VIDEO_POINT_MPE = "video/x-mpeg";
    public static final String VIDEO_POINT_MPEG = "video/mpg";
    public static final String VIDEO_POINT_MPG = "video/mpg";
    public static final String VIDEO_POINT_MPS = "video/x-mpeg";
    public static final String VIDEO_POINT_MPV = "video/mpg";
    public static final String VIDEO_POINT_MPV2 = "video/mpeg";
    public static final String VIDEO_POINT_IVF = "video/x-ivf";
    public static final String VIDEO_POINT_M1V = "video/x-mpeg";
    public static final String VIDEO_POINT_M2V = "video/x-mpeg";
    public static final String VIDEO_POINT_M4E = "video/mpeg4";
    public static final String VIDEO_POINT_WMV = "video/x-ms-wmv";
    public static final String VIDEO_POINT_WMX = "video/x-ms-wmx";
    public static final String VIDEO_POINT_WVX = "video/x-ms-wvx";
    public static final String VIDEO_POINT_WM = "video/x-ms-wm";

    /** --------------------- Audio Type ------------------------------ */
    public static final String AUDIO_POINT_XPL = "audio/scpls";
    public static final String AUDIO_POINT_SND = "audio/basic";
    public static final String AUDIO_POINT_AU = "audio/basic";
    public static final String AUDIO_POINT_WMA = "audio/x-ms-wma";
    public static final String AUDIO_POINT_WAV = "audio/wav";
    public static final String AUDIO_POINT_WAX = "audio/x-ms-wax";
    public static final String AUDIO_POINT_ACP = "audio/x-mei-aac";
    public static final String AUDIO_POINT_AIF = "audio/aiff";
    public static final String AUDIO_POINT_AIFC = "audio/aiff";
    public static final String AUDIO_POINT_AIFF = "audio/aiff";
    public static final String AUDIO_POINT_RMI = "audio/mid";
    public static final String AUDIO_POINT_RPM = "audio/x-pn-realaudio-plugin";
    public static final String AUDIO_POINT_PLS = "audio/scpls";
    public static final String AUDIO_POINT_RA = "audio/vnd.rn-realaudio";
    public static final String AUDIO_POINT_RAM = "audio/x-pn-realaudio";
    public static final String AUDIO_POINT_RMM = "audio/x-pn-realaudio";
    public static final String AUDIO_POINT_LA1 = "audio/x-liquid-file";
    public static final String AUDIO_POINT_LAVS = "audio/x-liquid-secure";
    public static final String AUDIO_POINT_LMSFF = "audio/x-la-lms";
    public static final String AUDIO_POINT_M3U = "audio/mpegurl";
    public static final String AUDIO_POINT_MPGA = "audio/rn-mpeg";
    public static final String AUDIO_POINT_MND = "audio/x-musicnet-download";
    public static final String AUDIO_POINT_MNS = "audio/x-musicnet-stream";
    public static final String AUDIO_POINT_MP1 = "audio/mp1";
    public static final String AUDIO_POINT_MP2 = "audio/mp2";
    public static final String AUDIO_POINT_MP3 = "audio/mp3";
    public static final String AUDIO_POINT_MID = "audio/mid";
    public static final String AUDIO_POINT_MIDI = "audio/mid";

    /** --------------------- Image Type ------------------------------ */
    /**
     * 标签图像文件格式（Tagged Image File Format，简写为TIFF）
     * 是一种主要用来存储包括照片和艺术图在内的图像的文件格式。它最初由Aldus公司与微软公司一起为PostScript打印开发。
     */
    public static final String IMAGE_POINT_TIF = "image/tiff";
    public static final String IMAGE_POINT_TIFF = "image/tiff";
    public static final String IMAGE_POINT_FAX = "image/fax";
    /**
     * 图像互换格式（GIF，Graphics Interchange Format）
     * 是一种位图图形文件格式，以8位色（即256种颜色）重现真彩色的图像。
     */
    public static final String IMAGE_POINT_GIF = "image/gif";
    public static final String IMAGE_POINT_ICO = "image/x-icon";
    public static final String IMAGE_POINT_JFIF = "image/jpeg";
    public static final String IMAGE_POINT_JPE = "image/jpeg";
    /**
     * JPEG是一种针对相片图像而广泛使用的一种有损压缩标准方法。
     */
    public static final String IMAGE_POINT_JPEG = "image/jpeg";
    public static final String IMAGE_POINT_JPG = "image/jpeg";
    public static final String IMAGE_POINT_NET = "image/pnetvue";
    /**
     * 便携式网络图形（Portable Network Graphics，PNG）
     * 是一种无损压缩的位图图形格式，支持索引、灰度、RGB三种颜色方案以及Alpha通道等特性。
     */
    public static final String IMAGE_POINT_PNG = "image/png";
    public static final String IMAGE_POINT_RP = "image/vnd.rn-realpix";
    public static final String IMAGE_POINT_WBMP = "image/vnd.wap.wbmp";

    /** --------------------- Message Type ------------------------------ */
    public static final String MESSAGE_POINT_EML = "message/rfc822";
    public static final String MESSAGE_POINT_MHT = "message/rfc822";
    public static final String MESSAGE_POINT_MHTML = "message/rfc822";
    public static final String MESSAGE_POINT_NWS = "message/rfc822";

    /** --------------------- Drawing Type ------------------------------ */
    public static final String DRAWING_POINT_907 = "drawing/907";
    public static final String DRAWING_POINT_SLK = "drawing/x-slk";
    public static final String DRAWING_POINT_TOP = "drawing/x-top";

    /** --------------------- Java Type ------------------------------ */
    public static final String JAVA_POINT_JAVA = "java/*";
    public static final String JAVA_POINT_CLASS = "java/*";

    /** --------------------- Other Type ------------------------------ */
    public static final String OTHOR_POINT_DWF = "Model/vnd.dwf";

    /** --------------------- Office2007 ------------------------------ */
    public static final String APPLICATION_POINT_OFFICE2007_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
    public static final String APPLICATION_POINT_OFFICE2007_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String APPLICATION_POINT_OFFICE2007_PPSX = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
    public static final String APPLICATION_POINT_OFFICE2007_POTX = "application/vnd.openxmlformats-officedocument.presentationml.template";
    public static final String APPLICATION_POINT_OFFICE2007_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String APPLICATION_POINT_OFFICE2007_XLTX = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";

    /** --------------------- Office2003 ------------------------------ */
    public static final String APPLICATION_POINT_OFFICE2003_XLS = "application/vnd.ms-excel";
}
