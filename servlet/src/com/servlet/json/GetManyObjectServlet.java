package com.servlet.json;

import com.servlet.crud.bean.Human;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */
@WebServlet(name = "GetManyObjectServlet", urlPatterns = {"/getManyObject"})
public class GetManyObjectServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        List<Human> humans = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Human human = new Human();
            human.setName("name" + i);
            human.setAge(i + 10);
            humans.add(human);
        }

        String result = JSONSerializer.toJSON(humans).toString();

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(result);
    }

    // 查看生成的 JSON 字符串，测试用
    public static void main(String[] args){
        List<Human> humans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Human human = new Human();
            human.setName("name" + i);
            human.setAge(i + 10);
            humans.add(human);
        }
        System.out.println(JSONSerializer.toJSON(humans).toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
