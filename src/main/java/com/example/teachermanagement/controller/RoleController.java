package com.example.teachermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {

    @PostMapping("/selectRole")
    public String selectRole(@RequestParam("role") String role, Model model) {
        model.addAttribute("role", role);
        if ("student".equals(role)) {
            return "redirect:/slogin"; // 学生的登录页面
        } else if ("teacher".equals(role)) {
            return "redirect:/login"; // 老师的登录页面
        }
        return "index"; // 返回选择页面
    }
}
