package org.bapp.web.controller.evaluator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EvaluatorController {

    @GetMapping("/assessment")
    public String index(){
        return "assessment/index";
    }


}
