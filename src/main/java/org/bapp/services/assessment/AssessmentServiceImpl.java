package org.bapp.services.assessment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Church;
import org.bapp.model.Registrant;
import org.bapp.repository.ChurchRepository;
import org.bapp.services.church.ChurchService;
import org.bapp.services.church.ChurchServiceImpl;
import org.bapp.services.registrant.RegistrantService;
import org.bapp.services.user.UserAuthenticationFacade;
import org.bapp.services.user.UserAuthenticationFacadeImpl;
import org.bapp.web.dto.RegistrantDTO;
import org.bapp.web.dto.RegistrantFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService, ChurchService{

    protected final Log logger = LogFactory.getLog(this.getClass());

    UserAuthenticationFacade auth = new UserAuthenticationFacadeImpl();

    @Autowired
    private ChurchRepository churchRepository;

    @Autowired
    private ChurchServiceImpl churchService;

    @Autowired
    private RegistrantService registrantService;

    @Autowired
    private CampFeeImpl campFee;

    @Override
    public List<RegistrantDTO> updateDelegateFee(RegistrantFee ar) {
        List<Registrant> nr = new ArrayList<>();
        if(ar.getRegistrants().isEmpty()){
            logger.info("No delegates to update.");
        } else {
            for (Registrant r : ar.getRegistrants()){
                Registrant i = registrantService.getRegistrant(r.getId());
                i.setSubsidy(ar.getSubsidy());
                i.setRemarks(ar.getRemarks());
                i.setRoomType(ar.getRoomType());
                if(ar.getSubsidy().equals("") || ar.getSubsidy() == null){
                    i.setFee(campFee.getCampFee()+campFee.otherFee(ar.getRoomType()));
                } else {
                    i.setFee(campFee.calculateCampFee(ar.getSubsidy(), ar.getRoomType()));
                }
                registrantService.save(i);
                nr.add(i);
            }
        }
        return RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(nr);
    }

    @Override
    public String submitForPayment(String churchId){

        Church church = churchService.findByChurchId(churchId);
        if(church != null){
            church.setAppStatus("1");
            church.setDateUpdated(LocalDateTime.now());
            church.setAssessedBy(auth.getAuthentication().getName());
            church.setAppStatusDate(LocalDateTime.now());
            churchService.save(church);
            logger.info("Church submitted to FOR PAYMENT {} " +church.getChurchId()+" - status: "+church.getAppStatus());
            return church.getChurchId();
        }
        return null;
    }

    @Override
    public List<Church> findByAppStatusAndEventNameAndChurchNameContaining(String appStatus, String eventName, String churchName) {
        return churchRepository.findByAppStatusAndEventNameAndChurchNameContaining(appStatus, eventName, churchName);
    }

    @Override
    public Church findByAppStatusAndChurchId(String status, String churchId) {
        return null;
    }
}
