package org.bapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "registrant")
public class Registrant extends AbstractEntity implements Serializable{

    private String designation;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date birthDate;

    private String gender;

    private String mobileNumber;

    private String civilStatus;

    private Integer yearsOfTeaching;

    private String classification;

    private Integer yearsOfMembership;

    private double fee;

    private String remarks;

    private String subsidy;

    private String roomType;

    @Column(unique = true)
    private Email email;

    @ManyToOne
    @JoinColumn(name = "church_id", nullable = false)
    private Church church;

    public Registrant(){

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

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public Integer getYearsOfTeaching() {
        return yearsOfTeaching;
    }

    public void setYearsOfTeaching(Integer yearsOfTeaching) {
        this.yearsOfTeaching = yearsOfTeaching;
    }
}
