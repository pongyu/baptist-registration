package org.bapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {


    @GetMapping("")
    public String index(){
        return "/index";
    }

    @GetMapping("login")
    public String login(){
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("session-invalid")
    public String invalidSession(){
        return "/invalidSession";
    }

    @GetMapping("session-expired")
    public String expiredSession(){
        return "/expiredSession";
    }
}
