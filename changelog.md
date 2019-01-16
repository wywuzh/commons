## 更新日志

### SpringBoot/V2.1.1
commons-core模块：
 1. 增加`JsonMapper`类：java对象和json相互转化。
 2. 增加`JavaTypeResolverSupport`类：当我们使用mybatis-generator插件自动生成代码时，`JavaTypeResolverDefaultImpl`类是将数据库表中的`TINYINT`类型默认转换为Java的`Byte`类型。改用`JavaTypeResolverSupport`可支持将数据库表中`TINYINT`类型转换为Java的`Integer`类型


### SpringBoot/V2.0.2
commons-core模块：
 1. 增加`web`包目录：建议接口的请求参数类继承`BaseRequest`类、接口的返回参数类继承`BaseResponse`类，需要返回值的接口统一使用`ResponseEntity`类进行封装返回。