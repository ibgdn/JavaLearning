package com.servlet.json;

import com.servlet.crud.bean.Human;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/27.
 */
@WebServlet(name = "GetOneObjectServlet",urlPatterns = {"/getOneObejct"})
public class GetOneObjectServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Human human = new Human();
        human.setName("Ali");
        human.setAge(22);

        JSONObject json = new JSONObject();
        json.put("human",JSONObject.fromObject(human));
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
