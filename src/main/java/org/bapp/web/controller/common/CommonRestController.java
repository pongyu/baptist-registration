package org.bapp.web.controller.common;

import org.bapp.model.Codetable;
import org.bapp.model.CodetableId;
import org.bapp.services.codetable.CodetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("common/util")
public class CommonRestController {

    @Autowired
    private CodetableService service;

    @GetMapping(value = "cdtbl/findOne")
    @ResponseBody
    public Codetable findOne(@RequestParam(name = "codename") String codename,
                             @RequestParam(name = "codevalue") String codevalue){
        return service.findOne( new CodetableId(codename, codevalue));
    }

}
