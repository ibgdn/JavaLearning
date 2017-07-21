package com.servlet.crud.servlet;

import com.servlet.crud.bean.Human;
import com.servlet.crud.dao.HumanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/21.
 */
@WebServlet(name = "EditServlet", urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        int id = Integer.parseInt(req.getParameter("id"));
        Human human = new HumanDao().get(id);

        StringBuilder stringBuilder = new StringBuilder();
        resp.setContentType("text/html;charset=utf-8");

        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<form action='update' method='post'>");
        stringBuilder.append("姓名：<input type='text' name='name' value='%s'><br>");
        stringBuilder.append("年龄：<input type='text' name='age' value='%d'><br>");
        stringBuilder.append("<input type='hidden' name='id' value='%d'>");
        stringBuilder.append("<input type='submit' value='提交'></form>");

        String html = String.format(stringBuilder.toString(),human.getName(),human.getAge(),human.getId());

        resp.getWriter().write(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
