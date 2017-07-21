package com.servlet.basic;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Administrator on 2017/7/19.
 * A demo to show local datetime.
 */
public class ServletTest extends HttpServlet {

    public void init(){
        System.out.println("执行 ServletTest 中的 init 方法。");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().println("<h1>Servlet!</h1>");
            resp.getWriter().println(LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
