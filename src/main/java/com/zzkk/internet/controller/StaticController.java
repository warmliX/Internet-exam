package com.zzkk.internet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author warmli
 */
@Controller
public class StaticController {
    @GetMapping("/loginIn")
    public String loginIn(){
        return "login";
    }

    @GetMapping("/loginUp")
    public String loginUp(){
        return "register";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/record")
    public String record(){
        return "record";
    }
}
