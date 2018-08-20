package org.bapp.controller;

import org.bapp.dto.ChurchDTO;
import org.bapp.dto.FullRegistrantDTO;
import org.bapp.dto.RegistrantDTO;
import org.bapp.model.Address;
import org.bapp.model.Church;
import org.bapp.model.Email;
import org.bapp.model.Registrant;
import org.bapp.repository.ChurchRepository;
import org.bapp.repository.RegistrantRepository;
import org.bapp.services.sequence.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("register")
@SessionAttributes("fde")
public class RegistrantController {



    @Autowired
    RegistrantRepository registrantRepository;

    @Autowired
    private ChurchRepository churchRepository;

    @Autowired
    private SequenceServiceImpl service;

    @RequestMapping(path = "add", method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("fde",new FullRegistrantDTO());
        return "register";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("fde") @Valid FullRegistrantDTO fde, Errors errors){

        if(errors.hasErrors()){
            return "redirect:";
        }

        service.setEventId(1);
        service.setEventName("BMPSYMP");

        // this should use mapper but for now manually map
        Church church = new Church();

        church.setChurchId(service.generateSequenceId());
        church.setChurchName(fde.getChurchDTO().getChurchName());


        churchRepository.save(church);

        return "index";
    }

    @PostMapping("church/save")
    @ResponseBody
    public String saveChurch( @ModelAttribute("church") @Valid ChurchDTO churchDTO, Errors errors){

        if(errors.hasErrors()){
            return "redirect:";
        }
        String id = churchDTO.getChurchId();
        Church  church = churchRepository.findByChurchId(id);
        try {

            if(church != null){
                church.setAddress(churchDTO.getAddress());
                church.setChurchContactNumber(churchDTO.getChurchContactNumber());
                church.setChurchEmail(new Email(churchDTO.getChurchEmail()));
                church.setContactPerson(churchDTO.getContactPerson());
                church.setContactPersonMobileNumber(churchDTO.getContactPersonMobileNumber());
                church.setDateUpdated(LocalDateTime.now());
                churchRepository.save(church);
                return church.getChurchId();
            }else{
                church.setChurchId(service.generateSequenceId());
            }






        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
