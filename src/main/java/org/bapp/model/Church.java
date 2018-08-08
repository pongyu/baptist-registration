package org.bapp.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "church")
public class Church {

    @Id
    @Column(name = "church_id", nullable = false, length = 20)
    private String churchId;

    private String churchFullName;

    @OneToOne
    private Address address;

    @Column(unique = true)
    private EmailAddress churchEmailAddress;

    private String churchContactNumber;

    private String contactPerson;

    private String contactPersonMobileNumber;

    private String eventName;

    private Date eventDate;

    private String encodedBy;

    private LocalDateTime encodedDate;

    private LocalDateTime appStatusDate;

    private LocalDateTime dateUpdated;

    private String appStatus;

    private String assessedBy;

    private String cashier;

    private LocalDateTime datePaid;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "church")
    private List<Registrant> registrants = new ArrayList<>();

    public Church(){
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getChurchFullName() {
        return churchFullName;
    }

    public void setChurchFullName(String churchFullName) {
        this.churchFullName = churchFullName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EmailAddress getChurchEmailAddress() {
        return churchEmailAddress;
    }

    public void setChurchEmailAddress(EmailAddress churchEmailAddress) {
        this.churchEmailAddress = churchEmailAddress;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEncodedBy() {
        return encodedBy;
    }

    public void setEncodedBy(String encodedBy) {
        this.encodedBy = encodedBy;
    }

    public LocalDateTime getEncodedDate() {
        return encodedDate;
    }

    public void setEncodedDate(LocalDateTime encodedDate) {
        this.encodedDate = encodedDate;
    }

    public LocalDateTime getAppStatusDate() {
        return appStatusDate;
    }

    public void setAppStatusDate(LocalDateTime appStatusDate) {
        this.appStatusDate = appStatusDate;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getAssessedBy() {
        return assessedBy;
    }

    public void setAssessedBy(String assessedBy) {
        this.assessedBy = assessedBy;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public LocalDateTime getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(LocalDateTime datePaid) {
        this.datePaid = datePaid;
    }

    public List<Registrant> getRegistrants() {
        return registrants;
    }

    public void setRegistrants(List<Registrant> registrants) {
        this.registrants = registrants;
    }
}