package org.bapp.web.controller.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bapp.mapper.ChurchMapper;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Church;
import org.bapp.model.Codetable;
import org.bapp.model.Registrant;
import org.bapp.services.assessment.AssessmentServiceImpl;
import org.bapp.services.church.ChurchServiceImpl;
import org.bapp.services.codetable.CodetableService;
import org.bapp.services.registrant.RegistrantService;
import org.bapp.web.dto.ChurchDTO;
import org.bapp.web.dto.RegistrantDTO;
import org.bapp.web.dto.RegistrantFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@Controller
public class EvaluatorController {

    protected final Log logger = LogFactory.getLog(this.getClass());

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
    @Autowired
    private CodetableService codetableService;

    @ModelAttribute("eventId")
    public Integer eventType(){
        return this.eventId;
    }

    @ModelAttribute("year")
    public String year(){
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    @ModelAttribute("subsidy")
    public List<Codetable> subsidy() {
        return codetableService.findAllById("subsidy");
    }

    @ModelAttribute("room")
    public List<Codetable> room() {
        return codetableService.findAllById("additionalfee");
    }

    @Autowired
    private AssessmentServiceImpl service;

    @Autowired
    private ChurchServiceImpl churchService;

    @Autowired
    private RegistrantService registrantService;

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
    public List<RegistrantDTO> churchDelegates(@RequestParam(name = "churchId") String churchId){

        Church c = churchService.findByChurchId(churchId);

        if(c == null){
            logger.error("Failed retrieving church in assessment.");
        }

        if(!c.getAppStatus().equals("0") && !c.getEventName().equals(eventName)){
            logger.error("Church is not for assessment");
            return null;
        }

        List<RegistrantDTO> r = RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(c.getRegistrants());

        return r;

    }

    @PostMapping("/assessment/update_fee")
    @ResponseBody
    public List<RegistrantDTO> updateFee(@RequestBody RegistrantFee fee){
      return service.updateDelegateFee(fee);
    }

    @PostMapping("/assessment/submit")
    @ResponseBody
    public String submit(@RequestBody String churchId){

        return service.submitForPayment(churchId);

    }

    @GetMapping("/assessment/find_one_church")
    @ResponseBody
    public ChurchDTO getChurch(@RequestParam(name = "churchId") String churchId){
        Church c = churchService.findByChurchId(churchId);
        return ChurchMapper.INSTANCE.toChurchDto(c);
    }

}
