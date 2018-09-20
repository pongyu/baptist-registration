package org.bapp.services.church;

import org.bapp.model.Church;

import java.util.List;

public interface ChurchService {

    List<Church> findAllByChurchNameLike(String churchName);

}
