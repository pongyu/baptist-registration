package org.bapp.mapper;

import org.bapp.model.Church;

public class ChurchIdMapper {

    public String churchId(Church church) {
        return church.getChurchId();
    }

    public Church asChurch(String churchId) {
        return new Church();
    }
}
