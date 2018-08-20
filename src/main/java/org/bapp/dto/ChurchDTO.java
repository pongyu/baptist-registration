package org.bapp.dto;


import org.bapp.model.Address;
import org.bapp.model.Email;

public class ChurchDTO {

    private String churchId;

    private String churchName;

    private String churchEmail;

    private String churchContactNumber;

    private String contactPerson;

    private String contactPersonMobileNumber;

    private Address address;

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
