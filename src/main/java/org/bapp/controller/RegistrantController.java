package org.bapp.controller;

import org.bapp.model.Address;
import org.bapp.model.Church;
import org.bapp.model.Email;
import org.bapp.model.Registrant;
import org.bapp.repository.RegistrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("register")
public class RegistrantController {

    @Autowired
    RegistrantRepository registrantRepository;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String register(){
        return "register";
    }

}
