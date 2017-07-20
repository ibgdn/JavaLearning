package com.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 * Upload file function by annotation.
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"}, loadOnStartup = 5)
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String fileName = null;
        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            // 设置上传文件大小限制
            diskFileItemFactory.setSizeThreshold(1024 * 1024);

            List items = null;
            try {
                items = servletFileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (!item.isFormField()) {
                    // 根据时间戳创建文件
                    fileName = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + ".jpg";
                    String uploadDir = "D:\\WS-IDEA\\JavaLearning\\servlet\\web\\upload-files";
                    File file = new File(uploadDir, fileName);
                    file.getParentFile().mkdirs();

                    // 通过 getInputStream() 获取上传文件的输入流
                    InputStream inputStream = item.getInputStream();

                    // 复制文件
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] be = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = inputStream.read(be))) {
                        fileOutputStream.write(be, 0, length);
                    }
                    fileOutputStream.close();
                } else {
                    System.out.println("item.getFieldName():" + item.getFieldName());
                    String value = item.getString();
                    value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println("value:" + value);
                }
            }
            // todo 图片上传后没有正常显示，留待以后解决。
            String htmlStr = "<img width='200' height='200' src='upload-files/%s' />";
            response.setContentType("text/html");
            PrintWriter printWriter = response.getWriter();
            printWriter.format(htmlStr, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
