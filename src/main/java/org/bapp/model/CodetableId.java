package org.bapp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CodetableId implements Serializable{

    @Column(name = "codename", nullable = false, length = 50)
    private String codeName;

    @Column(name = "codevalue", nullable = false, length = 30)
    private String codeValue;

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
