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

    static Church church = new Church();
    static ArrayList<Registrant> registrants = new ArrayList<>();
    static Address address = new Address();
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    RegistrantRepository registrantRepository;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String registerView(Model model){

        return "register";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam Church ch, ArrayList<Registrant> regs, Address addr){
        church.setChurchName(ch.getChurchName());
        registrants.addAll(regs);
        church.setRegistrants(registrants);
        return "register";
    }

    @RequestMapping(value = "/church", method = RequestMethod.GET)
    public String addRegistrantView(Model model){
        return "register";
    }

    @RequestMapping(value = "/add-registrant", method = RequestMethod.POST)
    public String addRegistrant(Registrant registrant){
        registrants.add(registrant);
        return "redirect:/";
    }

    @RequestMapping(value = "/update-registrant", method = RequestMethod.POST)
    public String updateRegistrant(Registrant registrant){
        for(Registrant r : registrants){
            if(r.getId().equals(registrant.getId())){
                r.setFirstName(registrant.getFirstName());
            }
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/delete-registrant", method = RequestMethod.GET)
    public String deleteRegistrant(Registrant registrant){
        registrants.remove(registrant);
        return "redirect:/";
    }

    @GetMapping(value = "/registrant")
    @ResponseBody
    public Registrant getRegistrant(@RequestParam(name="id", required = false) Long id){
        for(Registrant r : registrants){
            if(r.getId().equals(id)){
                return r;
            }
        }
        return null;
    }

}
