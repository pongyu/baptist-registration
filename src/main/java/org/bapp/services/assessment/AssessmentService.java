package org.bapp.services.assessment;

import org.bapp.web.dto.RegistrantDTO;
import org.bapp.web.dto.RegistrantFee;

import java.util.List;

public interface AssessmentService {

    String submitForPayment(String churchId);

    List<RegistrantDTO> updateDelegateFee(RegistrantFee registrantFee);

}
