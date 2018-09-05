package org.bapp.model;

import javax.persistence.Entity;

@Entity
public class Address extends AbstractEntity{
    private String lotNumber, street, barangay, city, state, country;

    public Address(){}

    public Address(String lotNumber, String street, String barangay, String city, String state, String country) {
        this.lotNumber = lotNumber;
        this.street = street;
        this.barangay = barangay;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Address(String street, String city, String state, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "lotNumber='" + lotNumber + '\'' +
                ", street='" + street + '\'' +
                ", barangay='" + barangay + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String shortAddress() {
        return this.street+" "+this.city+" "+this.state+" "+this.country;
    }
}
