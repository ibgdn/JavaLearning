package com.servlet.crud.servlet;

import com.servlet.crud.bean.Human;
import com.servlet.crud.dao.HumanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@WebServlet(name = "ListServlet", urlPatterns = {"/list"})
public class ListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        List<Human> humanList = new HumanDao().list();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table align='center' border='1' cellspacing = '0'>\r\n");
        stringBuilder.append("<tr><td>id</td><td>name</td><td>age</td><td>edit</><td>delete</td></tr>\r\n");
        String format = "<tr><td>%d</td><td>%s</td><td>%d</td><td><a href='edit?id=%d'>edit</a></td><td><a href='delete?id=%d'>delete</a></td></tr>\r\n";
        for (Human human:humanList){
            String str = String.format(format,human.getId(),human.getName(),human.getAge(),human.getId(),human.getId());
            stringBuilder.append(str);
        }
        stringBuilder.append("</table>");
        resp.getWriter().write(stringBuilder.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
