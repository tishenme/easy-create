package com.tishen.easycreate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${project.path}")
    private String projectPath;

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);

        // 解决方案0
        // ClassPathResource classPathResource = new ClassPathResource("freemarker");
        // log.info("======classPathResource file path: {}", classPathResource.getFile().getPath());
        // configuration.setDirectoryForTemplateLoading(classPathResource.getFile());
        // configuration.setDefaultEncoding("utf-8");

        // 解决方案1
        configuration.setClassForTemplateLoading(this.getClass(), "/freemarker");
        configuration.setDefaultEncoding("utf-8");

        // 解决方案2
        // log.info("======freemarker making");
        // File freemarkerDir = new File(projectPath, "000000/freemarker");
        // FileUtil.del(freemarkerDir);
        // FileUtil.mkdir(freemarkerDir);
        // ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // Resource[] resources = resolver.getResources("freemarker/*");
        // log.info("======resources: {}", Arrays.toString(resources));
        // for (Resource resource : resources) {
        //     log.info("======resource name: {}", resource.getFilename());
        //     String fileName = resource.getFilename();
        //     Resource[] rs = resolver.getResources("freemarker/" + fileName + "/*");
        //     File outDir = new File(freemarkerDir, fileName);
        //     for (Resource r : rs) {
        //         log.info("======r name: {}", r.getFilename());
        //         InputStream stream = r.getInputStream();
        //         FileUtil.writeFromStream(stream, new File(outDir, r.getFilename()));
        //     }
        // }
        // configuration.setDirectoryForTemplateLoading(freemarkerDir);
        // configuration.setDefaultEncoding("utf-8");


        return configuration;
    }

}
