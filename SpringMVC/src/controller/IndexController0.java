package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
    实现 Controller 接口，提供 handleRequest() 方法处理请求（处理 /index 的请求）
    SpringMVC 通过 ModelAndView 对象把模型和视图结合在一起。
 */
public class IndexController0 implements Controller{

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        ModelAndView mav = new ModelAndView("indexController0.jsp"); // 视图 index.jsp  only indexController0.
        ModelAndView mav = new ModelAndView("indexController0"); // 视图 index.jsp add indexController1.
        mav.addObject("message","indexController0"); // 模型数据 message，内容 Hello Spring MVC
        return mav;
    }
}