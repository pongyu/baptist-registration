package org.bapp.web.dto;

import org.bapp.model.Registrant;

import java.util.List;

public class RegistrantFee {

    private List<Registrant> registrants;

    private String roomType;

    private String subsidy;

    private String remarks;

    public RegistrantFee(){

    }

    public RegistrantFee(List<Registrant> registrants, String roomType, String subsidy, String remarks) {
        this.registrants = registrants;
        this.roomType = roomType;
        this.subsidy = subsidy;
        this.remarks = remarks;
    }

    public List<Registrant> getRegistrants() {
        return registrants;
    }

    public void setRegistrants(List<Registrant> registrants) {
        this.registrants = registrants;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
