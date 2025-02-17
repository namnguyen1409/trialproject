package com.hsf302.trialproject.common.controller;


import com.hsf302.trialproject.common.enums.ViewEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home" })
    public String home() {
        return ViewEnum.HOME.getViewName();
    }
}

