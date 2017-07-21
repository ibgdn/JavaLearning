package com.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/7/19.
 * Implementing the login function.
 */
public class LoginServlet extends HttpServlet {
    public LoginServlet(){
        System.out.println("LoginServlet 构造方法和其他 Servlet 的构造方法都是单例的，只会执行一次。");
    }

    public void init(){
        System.out.println("init() 也只会执行一次，即便多次使用 LoginServlet。");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        /*request.getRequestURL(): 浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有)" +
        request.getRequestURI(): 浏览器发出请求的资源名部分，去掉了协议和主机名" +
        request.getQueryString(): 请求行中的参数部分，只能显示以get方式发出的参数，post方式的看不到
        request.getRemoteAddr(): 浏览器所处于的客户机的IP地址
        request.getRemoteHost(): 浏览器所处于的客户机的主机名
        request.getRemotePort(): 浏览器所处于的客户机使用的网络端口
        request.getLocalAddr(): 服务器的IP地址
        request.getLocalName(): 服务器的主机名
        request.getMethod(): 得到客户机请求方式一般是GET或者POST*/
        System.out.println("request.getRequestURL(): 浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有)："+req.getRequestURL());
        System.out.println("request.getRequestURI(): 浏览器发出请求的资源名部分，去掉了协议和主机名："+req.getRequestURI());
        System.out.println("request.getQueryString(): 请求行中的参数部分，只能显示以get方式发出的参数，post方式的看不到："+req.getQueryString());
        System.out.println("request.getRemoteAddr(): 浏览器所处于的客户机的IP地址："+req.getRemoteAddr());
        System.out.println("request.getRemoteHost(): 浏览器所处于的客户机的主机名："+req.getRemoteHost());
        System.out.println("request.getRemotePort(): 浏览器所处于的客户机使用的网络端口："+req.getRemotePort());
        System.out.println("request.getLocalAddr(): 服务器的IP地址："+req.getLocalAddr());
        System.out.println("request.getLocalName(): 服务器的主机名："+req.getLocalName());
        System.out.println("request.getMethod(): 得到客户机请求方式一般是GET或者POST："+req.getMethod());


        String name = req.getParameter("name");
        String password = req.getParameter("password");

        System.out.println("用户名：" + name);
        System.out.println("密码：" + password);

        // version 0 原页面显示信息
        /*String htmlStr = null;
        if ("admin".equals(name) && "Aa000000".equals(password)) {
            htmlStr = "<div style='color:green'>登录成功——SUCCESS</div>";
        } else {
            htmlStr = "<div style='color:red'>登录失败——FAIL</div>";
        }
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter(); // 服务器端向浏览器端发送的输出流
        printWriter.println(htmlStr);*/

        // version 1 跳转到对应页面
        if("admin".equals(name)&&"Aa000000".equals(password)){
            // 服务端跳转可以看到浏览器的地址依然是/login 路径，并不会变成success.html
            req.getRequestDispatcher("success.html").forward(req,resp);
        }else{
            // version 2 在Servlet中进行客户端跳转,可以观察到，浏览器地址发生了变化
            // resp.sendRedirect("fail.html");

            // 或者使用如下方法
            resp.setStatus(301);
            resp.setHeader("Location","fail.html");
        }
    }
}
