package com.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/19.
 * Implementing register function by annotations.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"}, loadOnStartup = 4)
public class RegisterServlet extends HttpServlet {

    public void init() {
        System.out.println("执行 RegisterServlet 中的 init() 方法。");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("获取单值参数 id:" + request.getParameter("id"));

        String[] hobbies = request.getParameterValues("hobbies");
        System.out.println("获取多值参数 hobbies：" + Arrays.asList(hobbies));

        System.out.println("通过 getParameterMap 遍历所有参数：");
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> parameterNames = parameters.keySet();
        for (String param : parameterNames) {
            String[] value = parameters.get(param);
            System.out.println(param + ":" + Arrays.asList(value));
        }

        // 获取浏览器的头信息
        /*host: 主机地址
        user-agent: 浏览器基本资料
        accept: 表示浏览器接受的数据类型
        accept-language: 表示浏览器接受的语言
        accept-encoding: 表示浏览器接受的压缩方式，是压缩方式，并非编码
        connection: 是否保持连接
        cache-control: 缓存时限*/
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            System.out.printf("%s\t%s%n", header, value);
        }
    }
}
