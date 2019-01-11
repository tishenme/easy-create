package com.tishen.easycreate.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.tishen.easycreate.entity.Project;
import com.tishen.easycreate.util.FreemarkerUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tishe
 * @Date: 12/4/2018 10:19
 * @Description:
 */
@Slf4j
@Service
public class ProjectService {

    @Value("${project.path}")
    private String projectPath;

    @Autowired
    private Configuration freeMarkerConfiguration;

    /**
     * Create project
     */
    public File createProject(
            Project project
    ) throws Exception {
        // print log
        log.info("======buildTools: {}", project.getBuildTools());
        log.info("======programmingLanguage: {}", project.getProgrammingLanguage());
        log.info("======projectGroup: {}", project.getProjectGroup());
        log.info("======projectName: {}", project.getProjectName());
        log.info("======basePackage: {}", project.getBasePackage());
        log.info("======projectStructure: {}", project.getProjectStructure());
        log.info("======packMethod: {}", project.getPackMethod());
        log.info("======springBootVersion: {}", project.getSpringBootVersion());
        log.info("======springCloudVersion: {}", project.getSpringCloudVersion());
        // create guid
        String uuid = IdUtil.randomUUID();
        // create temp directory
        File tempDir = new File(projectPath + uuid);
        FileUtil.mkdir(tempDir);
        // create pack directory
        File packDir = new File(tempDir, "generate-project");
        FileUtil.mkdir(packDir);
        // create project info file
        File projectDir = new File(packDir, project.getProjectName());
        FileUtil.mkdir(projectDir);
        // record project info
        File projecInfotFile = new File(packDir, "Project-Info.txt");
        StringBuilder content = new StringBuilder();
        content.append("Build Tools: " + project.getBuildTools() + "\n");
        content.append("Programming Language: " + project.getProgrammingLanguage() + "\n");
        content.append("Project Group: " + project.getProjectGroup() + "\n");
        content.append("Project Name: " + project.getProjectName() + "\n");
        content.append("Base Package: " + project.getBasePackage() + "\n");
        content.append("Project Structure: " + project.getProjectStructure() + "\n");
        content.append("Pack Method: " + project.getPackMethod() + "\n");
        content.append("Spring Boot Version: " + project.getSpringBootVersion() + "\n");
        content.append("Spring Cloud Version: " + project.getSpringCloudVersion());
        FileUtil.writeUtf8String(content.toString(), projecInfotFile);
        // create project based on different criteria
        if ("Single".equals(project.getProjectStructure())) {
            this.mavenSingleCreate(project, projectDir);
            if ("Assembly".equals(project.getPackMethod())) {
                this.mavenAssemblyBaseOnSingleCreate(project, projectDir);
            } else if ("SpringBoot".equals(project.getPackMethod())) {
                this.mavenSpringBootBaseOnSingleCreate(project, projectDir);
            }
        } else if ("Complex".equals(project.getProjectStructure())) {
            this.mavenComplexCreate(project, projectDir);
            if ("Assembly".equals(project.getPackMethod())) {
                this.mavenAssemblyBaseOnComplexCreate(project, projectDir);
            } else if ("SpringBoot".equals(project.getPackMethod())) {
                this.mavenSpringBootBaseOnComplexCreate(project, projectDir);
            }
        }
        // zip compress
        File zip = ZipUtil.zip(packDir);
        // return zip
        return zip;
    }

    /**
     * Maven Single
     */
    private void mavenSingleCreate(
            Project project,
            File projectDir
    ) throws Exception {
        // git
        this.mavenGitCreate(project, projectDir);
        // src
        this.mavenSrcCreate(project, projectDir);
        // pom
        Map<String, Object> root = new HashMap<>(4);
        root.put("projectName", project.getProjectName());
        root.put("basePackage", project.getBasePackage().substring(0, project.getBasePackage().lastIndexOf(".")));
        root.put("springBootVersion", project.getSpringBootVersion());
        root.put("springCloudVersion", project.getSpringCloudVersion());
        Template template = freeMarkerConfiguration.getTemplate("maven/pom-single.xml.ftl");
        FreemarkerUtils.printFile(root, template, projectDir.getPath(), "pom.xml");
        // application
        this.mavenMainJavaCreate(project, projectDir);
    }

    /**
     * Maven Complex
     */
    private void mavenComplexCreate(
            Project project,
            File projectDir
    ) throws Exception {
        // git
        this.mavenGitCreate(project, projectDir);
        // src
        File apiDir = new File(projectDir, project.getProjectName() + "-api");
        FileUtil.mkdir(apiDir);
        this.mavenSrcCreate(project, apiDir);
        File coreDir = new File(projectDir, project.getProjectName() + "-core");
        FileUtil.mkdir(coreDir);
        this.mavenSrcCreate(project, coreDir);
        // pom
        Map<String, Object> root = new HashMap<>(6);
        root.put("projectName", project.getProjectName());
        root.put("basePackage", project.getBasePackage().substring(0, project.getBasePackage().lastIndexOf(".")));
        root.put("moudle_api", project.getProjectName() + "-api");
        root.put("moudle_core", project.getProjectName() + "-core");
        root.put("springBootVersion", project.getSpringBootVersion());
        root.put("springCloudVersion", project.getSpringCloudVersion());
        Template template = freeMarkerConfiguration.getTemplate("maven/pom-complex.xml.ftl");
        FreemarkerUtils.printFile(root, template, projectDir.getPath(), "pom.xml");
        // pom-api
        template = freeMarkerConfiguration.getTemplate("maven/pom-complex-api.xml.ftl");
        FreemarkerUtils.printFile(root, template, apiDir.getPath(), "pom.xml");
        // pom-core
        template = freeMarkerConfiguration.getTemplate("maven/pom-complex-core.xml.ftl");
        FreemarkerUtils.printFile(root, template, coreDir.getPath(), "pom.xml");
        // application
        this.mavenMainJavaCreate(project, coreDir);
    }

    /**
     * Maven Single Assembly
     */
    private void mavenAssemblyBaseOnSingleCreate(
            Project project,
            File projectDir
    ) throws Exception {
        // assembly
        File mainPath = new File(projectDir, "src/main");
        this.mavenAssemblyCreate(project, mainPath);
        // resource
        File resourcesPath = new File(projectDir, "src/main/resources");
        this.mavenResourcesCreate(project, resourcesPath);
    }

    /**
     * Maven Single SpringBoot
     */
    private void mavenSpringBootBaseOnSingleCreate(
            Project project,
            File projectDir
    ) throws Exception {
        // resource
        File resourcesPath = new File(projectDir, "src/main/resources");
        this.mavenResourcesCreate(project, resourcesPath);
    }

    /**
     * Maven Complex Assembly
     */
    private void mavenAssemblyBaseOnComplexCreate(
            Project project,
            File projectDir
    ) throws Exception {
        // assembly
        File MainPath = new File(projectDir, project.getProjectName() + "-core" + "/src/main");
        this.mavenAssemblyCreate(project, MainPath);
        // resource
        File resourcesPath = new File(projectDir, project.getProjectName() + "-core" + "/src/main/resources");
        this.mavenResourcesCreate(project, resourcesPath);
    }

    /**
     * Maven Complex SpringBoot
     */
    private void mavenSpringBootBaseOnComplexCreate(
            Project project,
            File projectDir
    ) throws Exception {
        // resource
        File resourcesPath = new File(projectDir, project.getProjectName() + "-core" + "/src/main/resources");
        this.mavenResourcesCreate(project, resourcesPath);
    }

    private void mavenGitCreate(
            Project project,
            File parentDir
    ) throws Exception {
        Template template = null;
        Map<String, Object> root = null;
        root = new HashMap<>(0);
        template = freeMarkerConfiguration.getTemplate("git/.gitignore.ftl");
        FreemarkerUtils.printFile(root, template, parentDir.getPath(), ".gitignore");
    }

    private void mavenSrcCreate(
            Project project,
            File parentDir
    ) throws Exception {
        File srcDir = new File(parentDir, "src");
        FileUtil.mkdir(srcDir);
        String[] paths = {"main", "test"};
        for (String path : paths) {
            File pathDir = new File(srcDir, path);
            FileUtil.mkdir(pathDir);
            File javaDir = new File(pathDir, "java");
            FileUtil.mkdir(javaDir);
            // create base package
            String basePackage = project.getBasePackage().replace(".", "/");
            File basePackageDir = new File(javaDir, basePackage);
            FileUtil.mkdir(basePackageDir);
            // something limit
            if ("main".equals(path)) {
                File resourcesDir = new File(pathDir, "resources");
                FileUtil.mkdir(resourcesDir);
            }
        }
    }

    private void mavenAssemblyCreate(
            Project project,
            File parentDir
    ) throws Exception {

        Template template = null;
        Map<String, Object> root = null;

        // Assembly
        File assemblyDir = new File(parentDir, "assembly");
        FileUtil.mkdir(assemblyDir);
        root = new HashMap<>(0);
        template = freeMarkerConfiguration.getTemplate("assembly/assembly.xml.ftl");
        FreemarkerUtils.printFile(root, template, assemblyDir.getPath(), "assembly.xml");

        // Bin
        File binDir = new File(parentDir, "bin");
        FileUtil.mkdir(binDir);
        root = new HashMap<>(2);
        root.put("projectName", project.getProjectName());
        root.put("basePackage", project.getBasePackage());
        template = freeMarkerConfiguration.getTemplate("bin/restart.sh.ftl");
        FreemarkerUtils.printFile(root, template, binDir.getPath(), "restart.sh");
        template = freeMarkerConfiguration.getTemplate("bin/server.sh.ftl");
        FreemarkerUtils.printFile(root, template, binDir.getPath(), "server.sh");
        template = freeMarkerConfiguration.getTemplate("bin/start.sh.ftl");
        FreemarkerUtils.printFile(root, template, binDir.getPath(), "start.sh");
        template = freeMarkerConfiguration.getTemplate("bin/stop.sh.ftl");
        FreemarkerUtils.printFile(root, template, binDir.getPath(), "stop.sh");

        // Config
        File configDir = new File(parentDir, "config");
        FileUtil.mkdir(configDir);
        File devConfigDir = new File(configDir, "dev");
        FileUtil.mkdir(devConfigDir);
        File proConfigDir = new File(configDir, "pro");
        FileUtil.mkdir(proConfigDir);
        root = new HashMap<>(2);
        root.put("projectName", project.getProjectName());
        root.put("basePackage", project.getBasePackage());
        template = freeMarkerConfiguration.getTemplate("resources/application.properties.ftl");
        FreemarkerUtils.printFile(root, template, devConfigDir.getPath(), "application.properties");
        FreemarkerUtils.printFile(root, template, proConfigDir.getPath(), "application.properties");
        template = freeMarkerConfiguration.getTemplate("log/logback-spring.xml.ftl");
        FreemarkerUtils.printFile(root, template, devConfigDir.getPath(), "logback-spring.xml");
        FreemarkerUtils.printFile(root, template, proConfigDir.getPath(), "logback-spring.xml");

        // Docker
        File dockerDir = new File(parentDir, "docker");
        FileUtil.mkdir(dockerDir);
        root = new HashMap<>(0);
        template = freeMarkerConfiguration.getTemplate("docker/Dockerfile.ftl");
        FreemarkerUtils.printFile(root, template, dockerDir.getPath(), "Dockerfile");

    }

    private void mavenResourcesCreate(
            Project project,
            File parentDir
    ) throws Exception {
        File metaInfDir = new File(parentDir, "META-INF");
        FileUtil.mkdir(metaInfDir);
        File staticDir = new File(parentDir, "static");
        FileUtil.mkdir(staticDir);
        File templatesDir = new File(parentDir, "templates");
        FileUtil.mkdir(templatesDir);
        File mapperDir = new File(parentDir, "mapper");
        FileUtil.mkdir(mapperDir);
        if ("SpringBoot".equals(project.getPackMethod())) {
            Map<String, Object> root = new HashMap<>(2);
            root.put("projectName", project.getProjectName());
            root.put("basePackage", project.getBasePackage());
            Template template = freeMarkerConfiguration.getTemplate("resources/application.properties.ftl");
            FreemarkerUtils.printFile(root, template, parentDir.getPath(), "application.properties");
            template = freeMarkerConfiguration.getTemplate("log/logback-spring.xml.ftl");
            FreemarkerUtils.printFile(root, template, parentDir.getPath(), "logback-spring.xml");
        }
    }

    private void mavenMainJavaCreate(
            Project project,
            File parentDir
    ) throws Exception {
        Template template = null;
        Map<String, Object> root = null;
        root = new HashMap<>(1);
        root.put("basePackage", project.getBasePackage());
        template = freeMarkerConfiguration.getTemplate("java/Application.java.ftl");
        String basePackage = project.getBasePackage().replace(".", "/");
        FreemarkerUtils.printFile(root, template, parentDir.getPath() + "/src/main/java/" + basePackage, "Application.java");
    }

}
