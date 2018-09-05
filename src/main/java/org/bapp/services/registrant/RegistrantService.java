package org.bapp.services.registrant;

import org.bapp.dto.RegistrantDTO;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Church;
import org.bapp.model.Registrant;
import org.bapp.repository.RegistrantRepository;
import org.bapp.services.church.ChurchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrantService {

    @Autowired
    private ChurchService churchService;

    @Autowired
    private RegistrantRepository registrantRepository;

    public List<Registrant> findAllByChurch(final Church church){
        return registrantRepository.findAllByChurch(church);
    }

    public void save(final Registrant registrant){
        this.registrantRepository.save(registrant);
    }

    public void deleteById(Long id){
        this.registrantRepository.deleteById(id);
    }

    public List<RegistrantDTO> listRegistrant(String churchId){
        Church church = churchService.findByChurchId(churchId);
        List<RegistrantDTO> list = RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(
                registrantRepository.findAllByChurch(church));
        return list;
    }

    public RegistrantDTO findOne(Long id){
        Optional<Registrant> c = registrantRepository.findById(id);
        if(c.isPresent()){
            RegistrantDTO r = RegistrantMapper.INSTANCE.registrantToRegistrantDto(c.get());
            return r;
        }
        return null;
    }

}
