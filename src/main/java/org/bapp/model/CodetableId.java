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

    public CodetableId(){

    }

    public CodetableId(String codeName, String codeValue) {
        this.codeName = codeName;
        this.codeValue = codeValue;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodetableId that = (CodetableId) o;

        if (codeName != null ? !codeName.equals(that.codeName) : that.codeName != null) return false;
        return codeValue != null ? codeValue.equals(that.codeValue) : that.codeValue == null;
    }

    @Override
    public int hashCode() {
        int result = codeName != null ? codeName.hashCode() : 0;
        result = 31 * result + (codeValue != null ? codeValue.hashCode() : 0);
        return result;
    }
}
