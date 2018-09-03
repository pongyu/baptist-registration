package org.bapp.services.common;

import org.bapp.model.Country;
import org.bapp.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public List<String> getCountries(){
        List<Country> c = repository.findAll();
        Set<String> s = new HashSet<>();
        for (Country cn : c){
            s.add(cn.getCountry());
        }

        List<String> ac = new ArrayList<>(s);
        Collections.sort(ac);

        return ac;
    }

    public List<String> getStates(String countrycode){
        List<Country> c = repository.findAllByCountrycode(countrycode);
        List<String> s = new ArrayList<>();
        for (Country cn : c){
            s.add(cn.getStateprovince());
        }
        return new ArrayList<>(new HashSet<>(s));
    }

    public List<String> getCities(String countrycode, String state){
        List<Country> c = repository.findAllByCountrycodeAndStateprovince(countrycode, state);
        List<String> s = new ArrayList<>();
        for (Country cn : c){
            s.add(cn.getCity());
        }
        return new ArrayList<>(new HashSet<>(s));
    }
}
