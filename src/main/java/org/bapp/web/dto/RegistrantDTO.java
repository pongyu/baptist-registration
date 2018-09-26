package org.bapp.web.dto;

import java.util.Date;

public class RegistrantDTO {

    private Long id;

    private String designation;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date birthDate;

    private String gender;

    private String mobileNumber;

    private String email;

    private String churchId;

    private String civilStatus;

    private Integer yearsOfTeaching;

    private String classification;

    private Integer yearsOfMembership;

    private double fee;

    private String remarks;

    private String subsidy;

    private String roomType;

    private String roomDesignation;

    public RegistrantDTO() {
    }

    public String getRoomDesignation() {
        return roomDesignation;
    }

    public void setRoomDesignation(String roomDesignation) {
        this.roomDesignation = roomDesignation;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getYearsOfMembership() {
        return yearsOfMembership;
    }

    public void setYearsOfMembership(Integer yearsOfMembership) {
        this.yearsOfMembership = yearsOfMembership;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Integer getYearsOfTeaching() {
        return yearsOfTeaching;
    }

    public void setYearsOfTeaching(Integer yearsOfTeaching) {
        this.yearsOfTeaching = yearsOfTeaching;
    }
}
