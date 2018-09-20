package org.bapp.web.dto;


import org.bapp.model.Address;

import java.util.List;

public class ChurchDTO {

    private String churchId;

    private String churchName;

    private String pastorFullName;

    private String churchEmail;

    private String churchContactNumber;

    private String contactPerson;

    private String contactPersonMobileNumber;

    private Address address;

    private List<RegistrantDTO> registrants;

    public ChurchDTO() {
    }

    public String getPastorFullName() {
        return pastorFullName;
    }

    public void setPastorFullName(String pastorFullName) {
        this.pastorFullName = pastorFullName;
    }

    public List<RegistrantDTO> getRegistrants() {
        return registrants;
    }

    public void setRegistrants(List<RegistrantDTO> registrants) {
        this.registrants = registrants;
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getChurchEmail() {
        return churchEmail;
    }

    public void setChurchEmail(String churchEmail) {
        this.churchEmail = churchEmail;
    }

    public String getChurchContactNumber() {
        return churchContactNumber;
    }

    public void setChurchContactNumber(String churchContactNumber) {
        this.churchContactNumber = churchContactNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonMobileNumber() {
        return contactPersonMobileNumber;
    }

    public void setContactPersonMobileNumber(String contactPersonMobileNumber) {
        this.contactPersonMobileNumber = contactPersonMobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
