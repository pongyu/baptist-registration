package org.bapp.controller.admin.system;

import org.bapp.repository.CodetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/system/codetable")
public class CodetableController {

    @Autowired
    private CodetableRepository repository;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("codetables", repository.findAll());
        return "admin/index";
    }

    @GetMapping(path = "/{codename}")
    public String getCodetableByCodename(Model model, @PathVariable(value = "codename") String codename){
        model.addAttribute("codetables", repository.findAllById(codename));
        return "redirect:";
    }
}
