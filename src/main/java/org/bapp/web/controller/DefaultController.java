package org.bapp.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Calendar;

@Controller
public class DefaultController {


    @Value("${bapp.eventname}")
    private String eventName;

    @Value("${bapp.eventid}")
    private Integer eventId;

    @Value("${bapp.theme}")
    private String theme;

    @Value("${bapp.themedesc}")
    private String themedesc;


    @ModelAttribute("theme")
    public String getTheme(){

       return this.theme;

    }

    @ModelAttribute("themedesc")
    public String getThemedesc(){
        return this.themedesc;
    }



    @ModelAttribute("event")
    public String getEventName(){

        String event;


        if(eventName.equals("BMPSYMP")){
            event = "BMP-Symposium ";
        } else {
            event = "BMP-Sunday School Camp ";
        }

        return event;
    }

    @ModelAttribute("eventId")
    public Integer eventType(){
        return this.eventId;
    }

    @ModelAttribute("year")
    public String year(){
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

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
