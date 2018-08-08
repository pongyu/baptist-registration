package org.bapp.model;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
public class EmailAddress implements Serializable{

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    @Column(name = "email")
    private String emailAddress;

    public EmailAddress(String emailAddress){
        Assert.isTrue(isValid(emailAddress), "Invalid email address");
        this.emailAddress = emailAddress;
    }

    protected EmailAddress(){}

    public boolean isValid(String candidate) {
        return PATTERN.matcher(candidate).matches();
    }

}
