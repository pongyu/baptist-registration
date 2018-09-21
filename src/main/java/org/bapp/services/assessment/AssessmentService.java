package org.bapp.services.assessment;

import org.bapp.model.Registrant;

import java.util.List;


public interface AssessmentService {

    void updateDelegateFee(List<Registrant> registrants, String roomType, String subsidy, String remarks);

}
