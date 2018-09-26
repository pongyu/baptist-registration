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

    public List<Church> findAll(){
        return this.churchRepository.findAll();
    }

    public void save(final Church church){
        this.churchRepository.save(church);
    }

    @Override
    public List<Church> findByAppStatusAndEventNameAndChurchNameContaining(String appStatus, String eventName, String churchName) {
        return churchRepository.findByAppStatusAndEventNameAndChurchNameContaining(appStatus,eventName, churchName);
    }

    @Override
    public Church findByAppStatusAndChurchId(String status, String churchId) {
        return churchRepository.findByAppStatusAndChurchId(status, churchId);
    }

}
