package com.tishen.easycreate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Auther: tishe
 * @Date: 12/4/2018 13:25
 * @Description:
 */
@Slf4j
@Configuration
public class FreeMarkerConfig {

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);

        // 模板加载 文件系统方式
        // ClassPathResource classPathResource = new ClassPathResource("freemarker");
        // log.info("======classPathResource file path: {}", classPathResource.getFile().getPath());
        // configuration.setDirectoryForTemplateLoading(classPathResource.getFile());
        // configuration.setDefaultEncoding("utf-8");

        // 模板加载 类加载方式
        configuration.setClassForTemplateLoading(this.getClass(), "/freemarker");
        configuration.setDefaultEncoding("utf-8");

        return configuration;
    }

}
