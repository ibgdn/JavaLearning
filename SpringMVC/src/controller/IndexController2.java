package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController2{

    @RequestMapping("/iC2")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)throws Exception{
        ModelAndView modelAndView = new ModelAndView("indexController2");
        modelAndView.addObject("message","indexController2");
        return modelAndView;
    }
}