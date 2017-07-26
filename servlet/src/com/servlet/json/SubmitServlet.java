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
 * Created by Administrator on 2017/7/26.
 */
@WebServlet(name = "SubmitServlet", urlPatterns = {"/submitServlet"})
public class SubmitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        System.out.println("服务器接收到的数据是：" + data);

        JSONObject json = JSONObject.fromObject(data);
        System.out.println("装换为 JSON 对象后的数据是：" + json);

        Human human = (Human) JSONObject.toBean(json,Human.class);
        System.out.println("转换为 Human 对象后的数据是：" + human);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
