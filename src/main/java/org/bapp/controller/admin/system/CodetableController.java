package org.bapp.controller.admin.system;

import org.bapp.model.Codetable;
import org.bapp.model.CodetableId;
import org.bapp.repository.CodetableRepository;
import org.bapp.services.codetable.CodetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/system/codetable")
public class CodetableController {

    @Autowired
    private CodetableService service;

    @RequestMapping(value = "")
    public String index(Model model, @RequestParam(defaultValue = "0") int page){
        model.addAttribute("codetables", service.findAll(page, 10));
        model.addAttribute("codetable", new Codetable());
        model.addAttribute("codename", null);
        model.addAttribute("currentPage", page);
        return "admin/codetable/codetable";
    }

    @ModelAttribute("codenames")
    public List<String> getCodenames(){
       return service.listCodenames();
    }

    @GetMapping("codename")
    public String getCodetableByCodename(Model model, @RequestParam(value = "codename") String codename, @RequestParam(defaultValue = "0") int page){
        model.addAttribute("codetables", service.findAllByCodeName(codename, page, 10));
        model.addAttribute("codename", codename);
        return "admin/codetable/codetable";
    }

    @PostMapping(value = "/save")
    public String saveCodetable(Model model, @ModelAttribute @Valid Codetable codetable, Errors errors){

        if(errors.hasErrors()){
            return "redirect:/";
        }

        service.save(codetable);

        return "redirect:/admin/system/codetable";
    }

    @GetMapping(value = "/delete")
    public String deleteCodetable(Model model, CodetableId id, Errors errors){

        if(errors.hasErrors()){
            return "redirect:/";
        }

        service.delete(id);

        return "redirect:";
    }

    @GetMapping(value = "/findOne")
    @ResponseBody
    public Codetable findOne(Model model,@RequestParam(name = "codename") String codename,
                             @RequestParam(name = "codevalue") String codevalue){
        CodetableId id = new CodetableId(codename, codevalue);
        return service.findOne(id);
    }
}
