package org.bapp.services.assessment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bapp.model.Church;
import org.bapp.model.Registrant;
import org.bapp.repository.ChurchRepository;
import org.bapp.services.church.ChurchService;
import org.bapp.services.church.ChurchServiceImpl;
import org.bapp.services.registrant.RegistrantService;
import org.bapp.services.user.UserAuthenticationFacade;
import org.bapp.services.user.UserAuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private CampFee campFee;

    @Override
    public void updateDelegateFee(List<Registrant> registrants, String roomType, String subsidy, String remarks) {

        if(registrants.isEmpty()){
            logger.info("No delegates to update.");
        } else {
            for (Registrant r : registrants){
                r.setSubsidy(subsidy);
                r.setRemarks(remarks);
                r.setRoomType(roomType);
                r.setFee(campFee.calculateCampFee(subsidy, roomType));
                registrantService.save(r);
            }
        }

    }

    public void submitForPayment(String churchId){

        Church church = churchService.findByChurchId(churchId);
        if(church != null){
            church.setAppStatus("1");
            church.setDateUpdated(LocalDateTime.now());
            church.setAssessedBy(auth.getAuthentication().getName());
            church.setAppStatusDate(LocalDateTime.now());
            churchService.save(church);
            logger.info("church submitted to FOR PAYMENT "+church.getChurchId());
        }

    }

    @Override
    public List<Church> findByAppStatusAndEventNameAndChurchNameContaining(String appStatus, String eventName, String churchName) {
        return churchRepository.findByAppStatusAndEventNameAndChurchNameContaining(appStatus, eventName, churchName);
    }
}
