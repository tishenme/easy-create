package com.tishen.easycreate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: tishe
 * @Date: 12/3/2018 16:01
 * @Description: 项目对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private String buildTools;

    private String programmingLanguage;

    private String projectGroup;

    private String projectName;

    private String basePackage;

    private String projectStructure;

    private String packMethod;

    private String springBootVersion;

    private String springCloudVersion;

}
