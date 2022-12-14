package com.maxdev.warehouse.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Incoming implements LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name="usercard")
    private Usercard usercard;

    private Timestamp datetime;

    @OneToOne
    @JoinColumn(name="rack")
    private Rack rack;

    @OneToOne
    @JoinColumn(name="good")
    private Good good;

    private Integer number;
    private Boolean satisfied;

    public Incoming(){}
    public Incoming(Usercard _usercard, Timestamp _datetime, Rack _rack, Good _good, Integer _number, Boolean _satisfied){
        usercard=_usercard;
        datetime=_datetime;
        rack=_rack;
        good=_good;
        number=_number;
        satisfied=_satisfied;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usercard getUsercard() {
        return usercard;
    }

    public void setUsercard(Usercard usercard) {
        this.usercard = usercard;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(Boolean satisfied) {
        this.satisfied = satisfied;
    }
}

