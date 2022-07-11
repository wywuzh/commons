# Alibaba dingtalk API
钉钉服务端调用API


## 接口调用
### TokenAPI

获取`access_token`信息，钉钉接口的调用都需要带上token来进行访问。


### ApiConfig
API配置类，项目中请保证其为单例。

该配置类的核心方法`getAccessToken()`提供了获取`access_token`的功能，并对该token进行了缓存，我们使用该方法可以避免频繁的调用`/gettoken`接口来获取token。


## 使用须知
1. `commons-dingtalk`旨在简化钉钉服务端API调用，提供了`ApiConfig`核心配置类简化我们对钉钉token的频繁调用获取

2. 钉钉官方提供了[服务端SDK下载](https://developers.dingtalk.com/document/app/download-the-server-side-sdk)，具体接口调用以官方文档为准

