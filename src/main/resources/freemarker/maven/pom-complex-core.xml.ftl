<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${basePackage}</groupId>
        <artifactId>${projectName}</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <artifactId>${moudle_core}</artifactId>

    <profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <conf.dir>${r"${project.basedir}"}/src/main/config/dev</conf.dir>
            </properties>
        </profile>
        <profile>
            <id>pro</id>
            <properties>
                <conf.dir>${r"${project.basedir}"}/src/main/config/pro</conf.dir>
            </properties>
        </profile>
    </profiles>

    <build>
        <plugins>
            <!--打包插件-Springboot-->
            <!--<plugin>-->
            <!--<groupId>org.springframework.boot</groupId>-->
            <!--<artifactId>spring-boot-maven-plugin</artifactId>-->
            <!--</plugin>-->
            <!--打包插件-JAR-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.1.0</version>
            </plugin>
            <!--打包插件-assembly-->
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>2.6</version>
                <configuration>
                    <finalName>${r"${project.artifactId}"}</finalName>
                    <descriptor>src/main/assembly/assembly.xml</descriptor>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <!--绑定到package生命周期阶段上-->
                        <phase>package</phase>
                        <goals>
                            <!--只运行一次-->
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <!--项目自身依赖-->
        <dependency>
            <groupId>${basePackage}</groupId>
            <artifactId>${moudle_api}</artifactId>
            <version>${r"${project.version}"}</version>
        </dependency>
        <!--cache 缓存管理-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <!--redis KV存储-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!--分布式配置 Apollo-->
        <dependency>
            <groupId>com.ctrip.framework.apollo</groupId>
            <artifactId>apollo-client</artifactId>
            <version>1.1.2</version>
        </dependency>
        <!--分布式日志 日志格式化-->
        <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>4.9</version>
        </dependency>
        <!--分布式日志 日志发送器-->
        <dependency>
            <groupId>com.github.danielwegener</groupId>
            <artifactId>logback-kafka-appender</artifactId>
            <version>0.1.0</version>
        </dependency>
        <!--分布式通讯 Kafka-->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
        <!--spring-cloud-starter-netflix-eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!--spring-cloud-starter-openfeign-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
    </dependencies>

</project>