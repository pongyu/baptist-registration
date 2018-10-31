package org.bapp.web.controller.admin.system;

import org.bapp.model.Codetable;
import org.bapp.model.CodetableId;
import org.bapp.services.codetable.CodetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("codetable")
public class CodetableController {

    @Autowired
    private CodetableService service;

//    @GetMapping(value = "")
//    public String index(Model model /* @RequestParam(defaultValue = "0") int page*/){
//        //use this if you want pageable
////        model.addAttribute("codetables", service.findAll(page, 10));
//        model.addAttribute("codetables", service.findAll());
//        model.addAttribute("codetable",new Codetable());
//        model.addAttribute("codename", null);
////        model.addAttribute("currentPage", page);
//        return "admin/codetable/codetable";
//    }

    @ModelAttribute("codenames")
    public List<String> getCodenames(){
       return service.listCodenames();
    }

    @GetMapping("")
    public String getCodetableByCodename(Model model, @RequestParam(name = "codename", defaultValue = "") String codename /* @RequestParam(defaultValue = "0") int page*/){
//        model.addAttribute("codetables", service.findAllByCodeName(codename, page, 10));

        if(codename.equals("")){
            model.addAttribute("codetables", service.findAll());
        }else{
            model.addAttribute("codetables", service.findAllByCodeName(codename));
        }
        model.addAttribute("codetable",new Codetable());
        model.addAttribute("codename", codename);
        return "admin/codetable/codetable";
    }

    @PostMapping(value = "save")
    public String saveCodetable(Model model, @RequestBody Codetable codetable){
        service.save(codetable);
        model.addAttribute("codetables", service.findAllByCodeName(codetable.getId().getCodeName()));
        return "admin/codetable/codetable";
    }

    @GetMapping(value = "delete")
    public String deleteCodetable(@RequestParam(name = "codename") String codename,
                                  @RequestParam(name = "codevalue") String codevalue){
        service.delete( new CodetableId(codename, codevalue));
        return "redirect:";
    }

    @GetMapping(value = "findOne")
    @ResponseBody
    public Codetable findOne(@RequestParam(name = "codename") String codename,
                             @RequestParam(name = "codevalue") String codevalue){
        return service.findOne( new CodetableId(codename, codevalue));
    }
}
