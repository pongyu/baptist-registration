package org.bapp.services.properties;

import org.bapp.model.Properties;
import org.bapp.repository.PropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertiesService {

    @Autowired
    private PropertiesRepository repository;

    public Properties findOne(){

        Optional<Properties> properties = repository.findById(1);

        if(properties.isPresent()){
            return properties.get();
        }

        return null;

    }

    public void updateProp(Properties p){
        repository.save(p);
    }

}
