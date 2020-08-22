## 更新日志

### SpringBoot/V2.3.2
1. 添加 Spring Cloud 依赖支持，版本为 `Hoxton.SR6`
2. commons-core模块：
  1. Gson、FastJson、Jackson等json工具类包路径调整&合并
  2. JsonMapper工具类依赖包调整，去掉 jackson 1.9.13 版本依赖。jackson工具依赖版本跟随SpringBoot中管理的版本号


### SpringBoot/V2.3.0
1. 代码合并自`SpringBoot/2.2.7`分支，`2.2.7`之后的分支不再合并到`2.3.x`分支


### SpringBoot/V2.2.0
1. 代码合并自`SpringBoot/2.1.9`分支，`2.1.9`之后的分支不再合并到`2.2.x`分支
2. 各个模块的依赖包都移动到`commons-parent`文件中统一管理
3. 新增commons-static模块：
    1. commons-static-jeesite4子模块：JeeSite4 后台模板
    2. commons-static-okadmin子模块：ok-admin v2.0 后台模板


### SpringBoot/V2.1.1
修改commons-core模块：
 1. 增加`JsonMapper`类：java对象和json相互转化。

新增commons-mybatis-generator模块：
 1. 增加`JavaTypeResolverSupport`类：当我们使用mybatis-generator插件自动生成代码时，`JavaTypeResolverDefaultImpl`类是将数据库表中的`TINYINT`类型默认转换为Java的`Byte`类型。改用`JavaTypeResolverSupport`可支持将数据库表中`TINYINT`类型转换为Java的`Integer`类型
 2. 增加模板文件：`src/main/resources/template`目录增加`mybatis-generator.xml`、`mybatis-generator-comment.ftl`两个模板文件。使用方法参考`src/main/resources/template/READMD.md`文档
 3. 新增SelectByParamsPlugin插件：该插件支持selectTotalByParams、selectListByParams、selectPagerByParams三类查询接口
 4. 新增BatchInsertPlugin插件：该插件为`com.itfsw.mybatis.generator.plugins.BatchInsertPlugin`插件的精简版，去掉了batchInsertSelective接口。


### SpringBoot/V2.0.2
修改commons-core模块：
 1. 增加`web`包目录：建议接口的请求参数类继承`BaseRequest`类、接口的返回参数类继承`BaseResponse`类，需要返回值的接口统一使用`ResponseEntity`类进行封装返回。