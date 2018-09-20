package org.bapp.web.controller;

import org.bapp.services.assessment.CampFee;
import org.bapp.services.assessment.CampFeeImpl;
import org.bapp.util.DateTimeUtil;
import org.bapp.web.dto.ChurchDTO;
import org.bapp.web.dto.CountryDTO;
import org.bapp.web.dto.RegistrantDTO;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.*;
import org.bapp.services.church.ChurchServiceImpl;
import org.bapp.services.codetable.CodetableService;
import org.bapp.services.common.CountryService;
import org.bapp.services.registrant.RegistrantService;
import org.bapp.services.sequence.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("register")
public class RegistrantController {


    @Value("${bapp.eventid}")
    private Integer eventId;

    @Value("${bapp.eventname}")
    private String eventName;


    @Autowired
    private RegistrantService registrantService;

    @Autowired
    private ChurchServiceImpl churchService;

    @Autowired
    private SequenceServiceImpl service;

    @Autowired
    private CodetableService codetableService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CampFee campFee;

    @ModelAttribute("event")
    public Integer eventType(){
        return this.eventId;
    }

    @ModelAttribute("countries")
    public List<CountryDTO> countries(){
        return countryService.getCountries();
    }

    @ModelAttribute("churches")
    public List<Codetable> church() {
        return codetableService.findAllById("church");
    }

    @ModelAttribute("designation")
    public List<Codetable> designation() {
        return codetableService.findAllById("designation");
    }

    @ModelAttribute("classification")
    public List<Codetable> classification() {
        return codetableService.findAllById("classification");
    }

    @ModelAttribute("gender")
    public List<Codetable> gender() {
        return codetableService.findAllById("gender");
    }

    @ModelAttribute("civilstatus")
    public List<Codetable> civilStatus() {
        return codetableService.findAllById("civilstatus");
    }

    @GetMapping("")
    public String getChurch(Model model){
        model.addAttribute("church", new ChurchDTO());
        model.addAttribute("delegates", new ArrayList<RegistrantDTO>());
        return "register";
    }

    @PostMapping("")
    @ResponseBody
    public String saveChurch(@RequestBody ChurchDTO churchDTO, Errors errors){

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
                service.setEventId(eventId);
                service.setEventName(eventName);
                c.setChurchId(service.generateSequenceId());
                c.setChurchName(churchDTO.getChurchName());
                c.setPastorFullName(churchDTO.getPastorFullName());

                c.setAddress(churchDTO.getAddress());
                c.setChurchContactNumber(churchDTO.getChurchContactNumber());
                c.setChurchEmail(new Email(churchDTO.getChurchEmail()));
                c.setContactPerson(churchDTO.getContactPerson());
                c.setContactPersonMobileNumber(churchDTO.getContactPersonMobileNumber());
                c.setDateUpdated(LocalDateTime.now());

                //set status to 0 "for payment"

                c.setAppStatus("0");
                c.setAppStatusDate(LocalDateTime.now());

                churchService.save(c);

                for (RegistrantDTO r : churchDTO.getRegistrants()){
                    Registrant ar = RegistrantMapper.INSTANCE.registrantDtoToRegistrant(r);

                    // initial saving of fee
                    int age = DateTimeUtil.getAge(ar.getBirthDate());
                    if(age <= 2){

                        ar.setSubsidy("LESS THAN 2 YRS OLD");
                        ar.setRemarks("2 Yrs. Old Below");

                    } else if(age > 2 && age <= 6){

                        ar.setSubsidy("KIDS");
                        ar.setRemarks("3 - 6 Yrs. Old");

                    } else {

                        ar.setSubsidy("FULL");
                        ar.setRemarks("Regular");

                    }

                    ar.setFee(campFee.calculateCampFee(ar.getSubsidy(), null));

                    ar.setChurch(c);
                    registrantService.save(ar);
                }
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
    public List<RegistrantDTO> addRegistrant(Model model, @RequestBody  RegistrantDTO registrantDTO){
        List<RegistrantDTO> list = new ArrayList<>();
        try {
            Church church = churchService.findByChurchId(registrantDTO.getChurchId());
            Registrant r = RegistrantMapper.INSTANCE.registrantDtoToRegistrant(registrantDTO);
            r.setChurch(church);
            registrantService.save(r);
            list =  registrantService.listRegistrant(registrantDTO.getChurchId());
            for(RegistrantDTO rg : list){
                rg.setDesignation(
                        codetableService.findOne(new CodetableId("designation", rg.getDesignation())).getDesc2()+" "+
                                codetableService.findOne(new CodetableId("designation", rg.getDesignation())).getDesc1());

                rg.setGender(codetableService.findOne(new CodetableId("gender", rg.getGender())).getDesc1());
                rg.setCivilStatus(codetableService.findOne(new CodetableId("civilstatus", rg.getCivilStatus())).getDesc1());
            }
            model.addAttribute("delegates", list);
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @GetMapping("delegate/delete")
    @ResponseBody
    public List<RegistrantDTO> deleteRegistrant(@RequestParam(name = "id") Long id,
                                                @RequestParam(name = "churchId") String churchId){
        registrantService.deleteById(id);
        return registrantService.listRegistrant(churchId);
    }

    @GetMapping("delegate/findOne")
    @ResponseBody
    public RegistrantDTO findOne(@RequestParam(name = "id") Long id){
        return registrantService.findOne(id);
    }

}
