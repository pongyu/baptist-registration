package org.bapp.controller;

import org.bapp.model.Church;
import org.bapp.repository.ChurchRepository;
import org.bapp.services.sequence.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("church")
public class ChurchController {

    @Autowired
    private ChurchRepository churchRepository;

    @Autowired
    private SequenceServiceImpl service;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("churches", churchRepository.findAll());
        model.addAttribute("title", "Church");
        return "church/church-main";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title","Add Church");
        model.addAttribute(new Church());
        return "church/church-details";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Church church, Errors errors){

        // if there are errors then return to the add church page
        if(errors.hasErrors()){
            model.addAttribute("title","Add Church");
            return "church/church-details";
        }

        service.setEventId(1);
        service.setEventName("BMPSYMP");

        church.setChurchId(service.generateSequenceId());
        churchRepository.save(church);

        return "redirect:";
    }
}
