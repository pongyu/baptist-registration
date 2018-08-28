package org.bapp.services.codetable;

import org.bapp.model.Codetable;
import org.bapp.repository.CodetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodetableService {

    @Autowired
    private CodetableRepository repository;

    public List<Codetable> findAllById(String codename){
        return repository.findAllById(codename);
    }
}
