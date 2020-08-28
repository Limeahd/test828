package com.sp.controller;

import com.sp.entity.UserEntity;
import com.sp.service.UserService;
import com.sun.deploy.net.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "sign")
    public String toLogin(){return "login";}
    @RequestMapping(value = "/login")
    public String login(Model model, @RequestParam("name")String name, @RequestParam("password")String password,
                        HttpServletResponse response,HttpServletRequest request){

        List<UserEntity> list = userService.select(name, password);
        if (list!=null) {
            try {
//                String username= URLEncoder.encode(name, "UTF-8");
                Cookie cookie=new Cookie("name",URLEncoder.encode(name,"utf-8"));
                Cookie cookie1=new Cookie("password",password);
                cookie.setMaxAge(60);
                cookie1.setMaxAge(60);
                response.addCookie(cookie);
                response.addCookie(cookie1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//          request.getSession().setAttribute("name",name);
//          request.getSession().setAttribute("password",password);
//          request.getSession().setMaxInactiveInterval(30);
//          Object username=request.getSession().getAttribute("name");
//          System.out.println(username);
          return "success";
        }
        else {
            model.addAttribute("msg","用户名密码错误");
            return "login";
        }
    }
}
