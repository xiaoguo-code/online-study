package com.gyr.studymanage.render.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @desc: 主页菜单跳转
 * @Author: guoyr
 * @Date: 2021-03-28 19:50
 */
@Controller
@RequestMapping("/admin")
public class MainController {

    @GetMapping("/index")
    public String toIndex(){
        return "index";
    }

}
