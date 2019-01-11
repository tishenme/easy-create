package com.tishen.easycreate.controller;

import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tishen.easycreate.entity.Project;
import com.tishen.easycreate.service.ProjectService;
import com.tishen.easycreate.util.HttpDownloadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Auther: tishe
 * @Date: 12/3/2018 16:04
 * @Description: 项目处理
 */
@Slf4j
@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Enter home page
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(
            Model model
    ) {
        model.addAttribute("projectGroup", "com.tishen");
        model.addAttribute("projectName", "demo");
        return "index";
    }

    /**
     * Generate project
     */
    @RequestMapping(value = "/generate-project.zip", method = RequestMethod.POST)
    public void generateProject(
            HttpServletResponse response,
            String buildTools,
            String programmingLanguage,
            String projectGroup,
            String projectName,
            String basePackage,
            String projectStructure,
            String packMethod,
            String springBootVersion,
            String springCloudVersion
    ) throws Exception {
        // object wrapper
        Project project = new Project();
        project.setBuildTools(buildTools);
        project.setProgrammingLanguage(programmingLanguage);
        project.setProjectGroup(projectGroup);
        project.setProjectName(projectName);
        project.setBasePackage(basePackage);
        project.setProjectStructure(projectStructure);
        project.setPackMethod(packMethod);
        project.setSpringBootVersion(springBootVersion);
        project.setSpringCloudVersion(springCloudVersion);
        // create project
        File file = projectService.createProject(project);
        // provide download
        HttpDownloadUtils.fileDownload(response, "generate-project.zip", IoUtil.toStream(file));
    }

}
