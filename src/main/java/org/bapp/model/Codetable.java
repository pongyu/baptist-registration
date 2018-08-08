package org.bapp.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Codetable {
    @EmbeddedId
    CodetableId id;

    @Column(length = 500)
    private String desc1;

    @Column(length = 500)
    private String desc2;

    @Column(length = 1000)
    private String remarks;

    public CodetableId getId() {
        return id;
    }

    public void setId(CodetableId id) {
        this.id = id;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
