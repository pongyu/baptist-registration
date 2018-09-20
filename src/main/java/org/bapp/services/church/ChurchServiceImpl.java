package org.bapp.services.church;

import org.bapp.model.Church;
import org.bapp.repository.ChurchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChurchServiceImpl implements ChurchService{

    @Autowired
    private ChurchRepository churchRepository;

    public Church findByChurchId(String churchId){
        return this.churchRepository.findByChurchId(churchId);
    }

    public void save(final Church church){
        this.churchRepository.save(church);
    }

    @Override
    public List<Church> findAllByChurchNameLike(String churchName) {
        return churchRepository.findByChurchNameIsLike(churchName);
    }
}
