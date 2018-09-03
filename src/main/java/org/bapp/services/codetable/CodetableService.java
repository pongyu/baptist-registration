package org.bapp.services.codetable;

import org.bapp.model.Codetable;
import org.bapp.model.CodetableId;
import org.bapp.repository.CodetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CodetableService {

    @Autowired
    private CodetableRepository repository;

    public List<Codetable> findAllById(String codename){
        return repository.findAllById_CodeName(codename);
    }

    public Page<Codetable> findAllByCodeName(String codename, int firstResult, int maxResult){
        return repository.findAllById_CodeName(codename, PageRequest.of(firstResult, maxResult));
    }

    //list all with paging
    public Page<Codetable> findAll(int firstResult, int maxResult){
        return repository.findAll(PageRequest.of(firstResult, maxResult));
    }

    //list common
    public List<Codetable> findAll(){
        return repository.findAll();
    }

    public List<Codetable> findAllByCodeName(String codename){
        return repository.findAllById_CodeName(codename);
    }

    public List<String> listCodenames(){
        List<Codetable> ct = repository.findAll();
        List<String> s = new ArrayList<>();
        for(Codetable c : ct){
            s.add(c.getId().getCodeName());
        }
        return new ArrayList<>(new HashSet<>(s));
    }

    public void save(final Codetable codetable){
        this.repository.save(codetable);
    }

    public void delete(CodetableId id){
        this.repository.deleteById(id);
    }

    public Codetable findOne(CodetableId id){
//        System.out.println("codename: "+id.getCodeName()+" codevalue: "+id.getCodeValue());
        Optional<Codetable> c = repository.findById(id);
        if(c.isPresent()){
            return c.get();
        }
        return null;
    }
}
