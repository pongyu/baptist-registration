package org.bapp.controller;

import org.bapp.dto.ChurchDTO;
import org.bapp.dto.RegistrantDTO;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Church;
import org.bapp.model.Codetable;
import org.bapp.model.Email;
import org.bapp.model.Registrant;
import org.bapp.services.church.ChurchService;
import org.bapp.services.codetable.CodetableService;
import org.bapp.services.registrant.RegistrantService;
import org.bapp.services.sequence.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("register")
public class RegistrantController {



    @Autowired
    private RegistrantService registrantService;

    @Autowired
    private ChurchService churchService;

    @Autowired
    private SequenceServiceImpl service;

    @Autowired
    private CodetableService codetableService;

    @ModelAttribute("churches")
    public List<Codetable> populateChurch() {
        return codetableService.findAllById("church");
    }

    @GetMapping("")
    public String getChurch(Model model){
        model.addAttribute("church", new ChurchDTO());
        return "register";
    }

    @PostMapping("")
    @ResponseBody
    public String saveChurch(@RequestBody ChurchDTO churchDTO, Errors errors, Model model){

        if(errors.hasErrors()){
            return "redirect:";
        }


        String id = churchDTO.getChurchId();
        Church  church = churchService.findByChurchId(id);
        try {

            if(church != null){

                church.setChurchName(churchDTO.getChurchName());

                //address
                churchDTO.getAddress().setId(church.getAddress().getId());
                church.setAddress(churchDTO.getAddress());
                church.setChurchContactNumber(churchDTO.getChurchContactNumber());
                church.setChurchEmail(new Email(churchDTO.getChurchEmail()));
                church.setContactPerson(churchDTO.getContactPerson());
                church.setContactPersonMobileNumber(churchDTO.getContactPersonMobileNumber());
                church.setDateUpdated(LocalDateTime.now());
                churchService.save(church);

                return church.getChurchId();
            }else{
                Church c = new Church();
                service.setEventId(1);
                service.setEventName("BMPSYMP");
                c.setChurchId(service.generateSequenceId());
                c.setChurchName(churchDTO.getChurchName());

                c.setAddress(churchDTO.getAddress());
                c.setChurchContactNumber(churchDTO.getChurchContactNumber());
                c.setChurchEmail(new Email(churchDTO.getChurchEmail()));
                c.setContactPerson(churchDTO.getContactPerson());
                c.setContactPersonMobileNumber(churchDTO.getContactPersonMobileNumber());
                c.setDateUpdated(LocalDateTime.now());
                churchService.save(c);

                return c.getChurchId();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("delegates")
    @ResponseBody
    public List<RegistrantDTO> getRegistrant(@RequestParam(name = "churchId") String churchId){
        Church church = churchService.findByChurchId(churchId);
        List<RegistrantDTO> list = RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(
                registrantService.findAllByChurch(church));
        return list;
    }

    @PostMapping("delegate/save")
    @ResponseBody
    public List<RegistrantDTO> addRegistrant(@RequestBody  RegistrantDTO registrantDTO){
        try {
            Church church = churchService.findByChurchId(registrantDTO.getChurchId());
            Registrant r = RegistrantMapper.INSTANCE.registrantDtoToRegistrant(registrantDTO);
            r.setChurch(church);
            registrantService.save(r);
        }catch (Exception e){
            e.printStackTrace();
        }

        return registrantService.listRegistrant(registrantDTO.getChurchId());
    }

    @PostMapping("delegate/delete")
    @ResponseBody
    public List<RegistrantDTO> deleteRegistrant(@RequestParam(name = "id") Long id,
                                                @RequestParam(name = "churchId") String churchId){
        registrantService.deleteById(id);
        return registrantService.listRegistrant(churchId);
    }


}
