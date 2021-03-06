<configuration scan="true" scanPeriod="10 seconds" debug="true">

    <!--控制台日志-->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--支持的色值blue cyan faint green magenta red yellow-->
            <pattern>
                %green(%date{HH:mm:ss})[%highlight(%-5level)]%boldYellow(%-1.4thread) %boldGreen(%-30.30logger{20}) %msg%n
                <!--<pattern>%green(%date{HH:mm:ss})[%highlight(%-5level)]%boldYellow(%-1.4thread) %boldGreen(%logger) %msg%n-->
            </pattern>
        </encoder>
    </appender>

    <!-- 按照每天生成日志文件 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>logs/%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
            <!--日志文件最大的大小-->
            <maxFileSize>200MB</maxFileSize>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--在线分布式日志-->
    <appender name="kafkaAppender" class="com.github.danielwegener.logback.kafka.KafkaAppender">
        <encoder class="com.github.danielwegener.logback.kafka.encoding.LayoutKafkaMessageEncoder">
            <layout class="net.logstash.logback.layout.LogstashLayout"/>
        </encoder>
        <topic>vvm-log</topic>
        <keyingStrategy class="com.github.danielwegener.logback.kafka.keying.ThreadNameKeyingStrategy"/>
        <deliveryStrategy class="com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy"/>
        <!-- 此处可以配置多个kafka地址，使用逗号隔开。目前的环境只有一台kafka-->
        <producerConfig>bootstrap.servers=172.17.103.78:9092,172.17.103.79:9092,172.17.103.80:9092</producerConfig>
        <!-- 如果kafka不可用，会使用此处的备用写入方案，此处输出到了控制台，生产环境应该写入文件. -->
        <appender-ref ref="STDOUT"/>
    </appender>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
        <!--<appender-ref ref="kafkaAppender"/>-->
    </root>

    <logger name="${basePackage}" level="DEBUG"/>
    <logger name="org.springframework.web" level="DEBUG"/>

    <logger name="org.mybatis.spring" level="WARN"/>
    <logger name="springfox.documentation" level="WARN"/>
    <logger name="org.springframework" level="WARN"/>
    <logger name="com.mchange.v2" level="WARN"/>
    <logger name="metrics_influxdb.measurements" level="WARN"/>
    <logger name="org.eclipse.jetty" level="WARN"/>
    <logger name="org.apache.kafka" level="INFO"/>

</configuration>
