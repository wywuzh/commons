## 更新日志
### SpringBoot/v2.5.2
1. commons-static模块：
  1. commons-static-easyui：v1.4.5以上版本combobox控件的setValues方法无法在onSelect事件中重新设置下拉值，会导致自定义的`initComboboxForCheck(selector, data, valueField, textField, onHidePanel, onLoadSuccess)`函数的下拉“所有”多选功能失效。因此将easyui的版本固定在v1.4.5，不再向上升级。


### SpringBoot/v2.4.5
1. commons-mybatis-generator模块：
  1. 新增BatchUpdatePlugin插件：支持MySQL、Oracle数据库表的批量更新sql生成
  2. SelectByParamsPlugin插件调整：添加 not in 查询支持


### SpringBoot/v2.3.8
1. 新增commons-dingtalk模块：提供钉钉API接口调用
2. 考虑jeesite4代码新版本版权原因，前端界面不再使用jeesite4的模板，删除commons-static模块下的commons-static-jeesite4子模块
3. commons-static模块：
  1. 新增`commons-static-vue`子模块


### SpringBoot/V2.3.6
1. commons-mybatis-generator模块：
  1. 添加DeleteByPKPlugin插件：根据主键删除数据插件，支持自定义方法名，支持物理删除和逻辑删除两种方式的sql
2. 新增commons-dbutils模块：基于Apache commons-dbutils进行二次封装处理的JDBC工具类库


### SpringBoot/V2.3.5
1. 添加 RabbitMQ、kafka 等分布式通信依赖支持
2. 添加Dubbo、Shiro依赖
3. 调整groupId，Maven打包发版接入sonatype


### SpringBoot/V2.3.2
1. 添加 Spring Cloud 依赖支持，版本为 `Hoxton.SR6`
2. commons-core模块：
  1. Gson、FastJson、Jackson等json工具类包路径调整&合并
  2. JsonMapper工具类依赖包调整，去掉 jackson 1.9.13 版本依赖。jackson工具依赖版本跟随SpringBoot中管理的版本号
3. commons-static模块：
  1. 新增`commons-static-easyui`子模块，jQuery EasyUI 后台模板
  2. `commons-static-assets`子模块：添加 easyui 自定义样式


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
