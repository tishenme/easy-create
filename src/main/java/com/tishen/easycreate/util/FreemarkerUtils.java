package com.tishen.easycreate.util;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Auther: tishe
 * @Date: 12/4/2018 13:59
 * @Description:
 */
public class FreemarkerUtils {

    /**
     * @param root     要在 Templet 中替换的内容
     * @param template Templete 实例
     * @param filePath 生成文件的路径
     * @param fileName 生成文件的名字
     * @throws Exception
     */
    public static void printFile(
            Map<String, Object> root,
            Template template,
            String filePath,
            String fileName
    ) throws Exception {
        FileUtil.mkdir(filePath);
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        template.process(root, out);
        out.close();
    }

}
