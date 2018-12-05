package com.tishen.easycreate.util;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @author: tishen
 * @date: 2018/3/5
 * @description: 提供Http下载
 */
@Slf4j
public class HttpDownloadUtils {

    /**
     * 文件下载
     *
     * @param response
     * @param filename
     * @param cellData
     * @return
     * @throws IOException
     */
    public static int fileDownload(HttpServletResponse response, String filename, byte[] cellData) throws IOException {
        log.info("提供文件下载");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(cellData);
        outputStream.flush();
        outputStream.close();
        return 0;
    }

    /**
     * 文件下载
     *
     * @param response
     * @param filename
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static int fileDownload(HttpServletResponse response, String filename, InputStream inputStream) throws IOException {
        log.info("提供文件下载");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IoUtil.copy(inputStream, outputStream, IoUtil.DEFAULT_BUFFER_SIZE);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return 0;
    }

}
