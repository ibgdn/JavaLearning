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
@WebServlet(name = "UpdateServlet",urlPatterns = {"/update"})
public class UpdateServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Human human = new Human();
        human.setId(Integer.parseInt(request.getParameter("id")));
        human.setName(request.getParameter("name"));
        human.setAge(Integer.parseInt(request.getParameter("age")));

        new HumanDao().update(human);
        response.sendRedirect("/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
