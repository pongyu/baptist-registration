package org.bapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "registrant")
public class Registrant extends AbstractEntity implements Serializable{

    private String firstName;

    private String middleName;

    private String lastName;

    private Date birthDate;

    private String gender;

    @Column(unique = true)
    private EmailAddress emailAddress;

    @ManyToOne
    @JoinColumn(name = "church_id", nullable = false)
    private Church church;

    public Registrant(){

    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
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
}
