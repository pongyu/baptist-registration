package org.bapp.services.common;

import org.bapp.dto.CountryDTO;
import org.bapp.model.Country;
import org.bapp.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public List<CountryDTO> getCountries(){
        List<Country> c = repository.listCountries();
        List<CountryDTO> s = new ArrayList<>();
        for (Country cn : c){
            s.add(new CountryDTO(cn.getCode(), cn.getCountry()));
        }

        return s;
    }

    public List<String> getStates(String code){
        List<Country> c = repository.findAllByCode(code);
        List<String> s = new ArrayList<>();
        for (Country cn : c){
            s.add(cn.getStateprovince());
        }
        return s;
    }

    public List<String> getCities(String code, String state){
        List<Country> c = repository.findAllByCodeAndStateprovince(code, state);
        List<String> s = new ArrayList<>();
        for (Country cn : c){
            s.add(cn.getCity());
        }
        return new ArrayList<>(new HashSet<>(s));
    }
}
