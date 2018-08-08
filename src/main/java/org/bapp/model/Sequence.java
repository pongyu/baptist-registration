package org.bapp.model;

import javax.persistence.*;

@Entity
@Table(name = "sequence")
public class Sequence{

    @Id
    private Integer id;

    @Column(unique = true)
    private String event;

    private Integer year;

    private Integer sequence;

    public Sequence(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
