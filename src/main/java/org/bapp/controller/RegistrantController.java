package org.bapp.controller;

import org.bapp.dto.ChurchDTO;
import org.bapp.dto.RegistrantDTO;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Address;
import org.bapp.model.Church;
import org.bapp.model.Email;
import org.bapp.model.Registrant;
import org.bapp.repository.AddressRepository;
import org.bapp.repository.ChurchRepository;
import org.bapp.repository.RegistrantRepository;
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
    private RegistrantRepository registrantRepository;

    @Autowired
    private ChurchRepository churchRepository;

    @Autowired
    private SequenceServiceImpl service;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("church")
    public String getChurch(Model model){
        model.addAttribute("church", new ChurchDTO());
        model.addAttribute("churchId");
        return "register";
    }

    @PostMapping("church")
    @ResponseBody
    public String saveChurch(@RequestBody ChurchDTO churchDTO, Errors errors, Model model){

        if(errors.hasErrors()){
            return "redirect:";
        }


        String id = churchDTO.getChurchId();
        Church  church = churchRepository.findByChurchId(id);
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
                churchRepository.save(church);

                model.addAttribute("churchId",church.getChurchId());
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
                churchRepository.save(c);

                model.addAttribute("churchId",c.getChurchId());
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
        Church church = churchRepository.findByChurchId(churchId);
        List<RegistrantDTO> list = RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(
                registrantRepository.findAllByChurch(church));
        return list;
    }

    @PostMapping("delegate/save")
    @ResponseBody
    public List<RegistrantDTO> addRegistrant(@RequestBody  RegistrantDTO registrantDTO){
        Church church = churchRepository.findByChurchId(registrantDTO.getChurchId());
        Registrant r = RegistrantMapper.INSTANCE.registrantDtoToRegistrant(registrantDTO);
        r.setChurch(church);
        registrantRepository.save(r);
        return listRegistrant(registrantDTO.getChurchId());
    }

    @PostMapping("delegate/delete")
    @ResponseBody
    public void editRegistrant(@RequestParam(name = "id") Long id){
        registrantRepository.deleteById(id);
    }

    private List<RegistrantDTO> listRegistrant(String churchId){
        Church church = churchRepository.findByChurchId(churchId);
        List<RegistrantDTO> list = RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(
                registrantRepository.findAllByChurch(church));
        return list;
    }
}
