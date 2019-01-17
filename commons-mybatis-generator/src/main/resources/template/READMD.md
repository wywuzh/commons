## mybatis-generator代码自动生成插件使用
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