package com.baizhi.hyh.controller;

import com.baizhi.hyh.code.CreateValidateCode;
import com.baizhi.hyh.entity.Admin;
import com.baizhi.hyh.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("AdminController")
public class AdminController {
    @Autowired
    AdminService adminService;

    /*登陆*/
    @ResponseBody
    @RequestMapping("/login")
    public String login(Admin admin, String code, HttpSession session) {
        Object code1 = session.getAttribute("code");
        String mis = null;
        Admin admin1 = adminService.loginAdmin(admin);
        if (admin1 != null) {
            if (code1.equals(code)) {
                session.setAttribute("admin", admin1);
                return mis = null;
            } else {
                return mis = "验证码不对";
            }
        } else {
            return mis = "账户或密码错误";
        }
    }

    /*验证码*/
    @RequestMapping("code")
    public void login(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode createValidateCode = new CreateValidateCode();
        String code = createValidateCode.getCode();
        createValidateCode.write(response.getOutputStream());
        session.setAttribute("code", code);
    }

    /*安全退出*/
    @RequestMapping("outLogin")
    public String outLogin(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/jsp/login.jsp";
    }
}
