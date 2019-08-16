## mybatis-generator代码自动生成插件使用
### commons-mybatis-generator 插件使用说明
第一步：pom.xml文件引入依赖包：
```xml
<dependency>
    <groupId>com.wuzh.commons.springboot</groupId>
    <artifactId>commons-mybatis-generator</artifactId>
    <version>2.1.1</version>
</dependency>
```

第二步：将template目录下`mybatis-generator.xml`、`mybatis-generator-comment.ftl`两个文件copy到自己项目的`src/main/resources`目录下

第三步：pom.xml文件下添加plugin：
```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.7</version>
    <configuration>
        <!-- 配置文件 -->
        <configurationFile>src/main/resources/mybatis-generator.xml</configurationFile>
        <!-- 允许移动和修改 -->
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
    <dependencies>
        <!-- jdbc 依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- 导入自定义插件类
        1. https://blog.csdn.net/u_ascend/article/details/80742919
        2. https://blog.csdn.net/twj13162380953/article/details/81286714
        3. https://segmentfault.com/a/1190000013037968
        -->
        <dependency>
            <groupId>com.wuzh.commons.springboot</groupId>
            <artifactId>commons-mybatis-generator</artifactId>
            <version>2.1.1</version>
        </dependency>
    </dependencies>
</plugin>
```


### commons-mybatis-generator 插件功能清单
#### BatchInsertPlugin 批量新增插件
插件全路径：com.wuzh.commons.mybatis.generator.plugins.BatchInsertPlugin。

该插件是itfsw批量新增SQL插件精简版，去掉batchInsertSelective接口。去掉ModelColumnPlugin插件依赖。

使用：
```xml
    <!-- 批量插入插件 -->
    <plugin type="com.wuzh.commons.mybatis.generator.plugins.BatchInsertPlugin">
        <!--
        开启后可以实现官方插件根据属性是否为空决定是否插入该字段功能！
        需开启allowMultiQueries=true多条sql提交操作，所以不建议使用！插件默认不开启
        -->
        <property name="allowMultiQueries" value="false"/>
    </plugin>
```


### SelectByParamsPlugin 自定义select查询插件
插件全路径：com.wuzh.commons.mybatis.generator.plugins.SelectByParamsPlugin。

插件使用：
```xml
    <!-- 批量插入插件 -->
    <plugin type="com.wuzh.commons.mybatis.generator.plugins.SelectByParamsPlugin"></plugin>
```

该插件会在Mapper接口中自动生成selectTotalByParams、selectListByParams、selectPagerByParams三个查询方法，Mapper映射器中会生成对应的查询SQL语句：
1. `<sql id="appendConditions">`：取出表中的所有字段，封装成一个公共的Where条件，这样减少了我们对每个表的SQL查询封装
2. selectPagerByParams：根据配置的驱动`driverClass`生成相应的分页SQL。目前支持MySQL、Oracle
3. selectListByParams、selectPagerByParams：生成通用的排序SQL。`map.sorts`

该插件同时也对`<table>`的功能进行了增强
1. conditionsLikeColumns：Like模糊查询，有多个字段时可用逗号分隔，支持全角和半角。`<property name="conditionsLikeColumns" value=""/>`
2. conditionsForeachInColumns：Foreach in查询，有多个字段时可用逗号分隔，支持全角和半角。`<property name="conditionsForeachInColumns" value=""/>`