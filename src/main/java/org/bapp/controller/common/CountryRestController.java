package org.bapp.controller.common;

import org.bapp.model.Country;
import org.bapp.services.common.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common/util/country")
public class CountryRestController {

    @Autowired
    private CountryService service;

    @GetMapping("countries")
    public List<String> getCountries(){
        return service.getCountries();
    }

    @GetMapping("states")
    public List<String> getStates(@RequestParam(name = "countrycode")String countrycode){
        return service.getStates(countrycode);
    }

    @GetMapping("cities")
    public List<String> getCities(@RequestParam(name = "countrycode")String countrycode,
                                  @RequestParam(name = "state")String state){
        return service.getCities(countrycode, state);
    }
}
