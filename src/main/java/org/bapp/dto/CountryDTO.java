package org.bapp.dto;

public class CountryDTO {
    private String code;

    private String country;

    public CountryDTO() {
    }

    public CountryDTO(String code, String country) {
        this.code = code;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
