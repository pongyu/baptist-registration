package org.bapp.services.billing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bapp.model.Church;
import org.bapp.services.church.ChurchServiceImpl;
import org.bapp.services.user.UserAuthenticationFacade;
import org.bapp.services.user.UserAuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BillingServiceImpl implements BillingService{

    protected final Log logger = LogFactory.getLog(this.getClass());

    UserAuthenticationFacade auth = new UserAuthenticationFacadeImpl();

    @Autowired
    private ChurchServiceImpl churchService;

    @Override
    public void submitToRegistered(String churchId) {
        Church c = churchService.findByAppStatusAndChurchId("1", churchId);
        if(c != null){
            c.setAppStatus("2");
            c.setAppStatusDate(LocalDateTime.now());
            c.setCashier(auth.getAuthentication().getName());
            c.setDatePaid(LocalDateTime.now());
            churchService.save(c);
            logger.info("Church moved status to registered : "+churchId);
        } else {
            logger.error("Church not found with status : 1 and church id : "+churchId);
        }

    }
}
