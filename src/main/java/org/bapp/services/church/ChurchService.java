package org.bapp.services.church;

import org.bapp.model.Church;

import java.util.List;

public interface ChurchService {

    List<Church> findByAppStatusAndEventNameAndChurchNameContaining(String appStatus, String eventName, String churchName);

    Church findByAppStatusAndChurchId(String status, String churchId);
}
