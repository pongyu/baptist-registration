package org.bapp.mapper;


import org.bapp.model.Email;

public class EmailMapper {

    public String asString(Email email) {
        return email.toString();
    }

    public Email asEmail(String email) {
        return new Email(email);
    }

}
