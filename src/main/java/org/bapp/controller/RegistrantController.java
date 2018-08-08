package org.bapp.controller;

import org.bapp.model.Address;
import org.bapp.model.Registrant;
import org.bapp.repository.RegistrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegistrantController {

    @Autowired
    RegistrantRepository repository;

    @GetMapping(value = "/register")
    public  String home(Model model){
        model.addAttribute("registrant", new Registrant());
        return "registration";
    }

    @PostMapping(value = "/register")
    public String addRegistrant(@ModelAttribute Registrant registrant) {
        repository.save(registrant);
        return "registrationResult";
    }

}
