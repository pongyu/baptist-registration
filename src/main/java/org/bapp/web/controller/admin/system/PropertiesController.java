package org.bapp.web.controller.admin.system;

import org.bapp.model.Codetable;
import org.bapp.model.Properties;
import org.bapp.services.codetable.CodetableService;
import org.bapp.services.properties.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("properties")
public class PropertiesController {

    @Autowired
    private PropertiesService service;

    @Autowired
    private CodetableService codetableService;


    @ModelAttribute("event")
    public List<Codetable> event() {
        return codetableService.findAllById("event");
    }

    @GetMapping("")
    public String index(Model model){

        model.addAttribute("prop", service.findOne());

        return "admin/properties";
    }


    @PostMapping("")
    public String save(@ModelAttribute Properties properties){

        service.updateProp(properties);

        return "admin/properties";

    }


}
