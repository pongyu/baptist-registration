package org.bapp.controller.rest;

import org.bapp.model.Registrant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("register")
public class RegistrantRestController {

    @RequestMapping(path="/registrants", method=RequestMethod.GET)
    public List<Registrant> listRegistrant(){
        Registrant r = new Registrant();
        r.setId(new Long("1"));
        r.setFirstName("Pongyu");
        r.setMiddleName("de Luna");
        r.setLastName("Quindoza");
        r.setBirthDate(new Date());
        r.setGender("Male");
        //
        Registrant r2 = new Registrant();
        r2.setId(new Long("2"));
        r2.setFirstName("Joanderson");
        r2.setMiddleName("D");
        r2.setLastName("Quindoza");
        r2.setBirthDate(new Date());
        r2.setGender("Male");
        List<Registrant> registrants = new ArrayList<>();
        registrants.add(r);
        registrants.add(r2);
        return registrants;
    }
}
