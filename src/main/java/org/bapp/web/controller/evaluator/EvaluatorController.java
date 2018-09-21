package org.bapp.web.controller.evaluator;

import org.bapp.model.Church;
import org.bapp.services.assessment.AssessmentServiceImpl;
import org.bapp.services.church.ChurchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

@Controller
public class EvaluatorController {

    @Value("${bapp.eventname}")
    private String eventName;

    @Value("${bapp.eventid}")
    private Integer eventId;

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

    @Autowired
    private AssessmentServiceImpl service;

    @Autowired
    private ChurchServiceImpl churchService;


    @GetMapping("/assessment")
    public String index(){
        return "assessment/index";
    }

    @GetMapping("/assessment/church")
    public String listChurch(@RequestParam(name = "church") String church, Model model){

        model.addAttribute("churches", service.findByAppStatusAndEventNameAndChurchNameContaining("0",eventName, church));

        return "assessment/listChurch";
    }

    @GetMapping("/assessment/delegates")
    public String listDelegates(){

        return "assessment/listDelegates";

    }

    @GetMapping(value = "/assessment/church/delegates")
    @ResponseBody
    public Church churchDelegates(@RequestParam(name = "churchId") String churchId){

        Church c = churchService.findByChurchId(churchId);

        if(!c.getAppStatus().equals("0")){
            return null;
        }

        return c;

    }

}
