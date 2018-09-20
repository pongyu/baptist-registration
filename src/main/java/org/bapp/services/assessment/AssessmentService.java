package org.bapp.services.assessment;

import org.bapp.model.Registrant;


public interface AssessmentService {

    void updateDelegateFee(String churchId, String roomType, String subsidy, String remarks);

}
