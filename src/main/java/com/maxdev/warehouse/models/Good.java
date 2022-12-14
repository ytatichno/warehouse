package com.maxdev.warehouse.models;

import jakarta.persistence.*;

@Entity
@Table(name="goods")
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name, descr;
    private int totalnumber;

    public Good() {}
    public Good(int _totalnumber, String _name, String _descr){
        totalnumber = _totalnumber;
        name = _name;
        descr = _descr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(int totalnumber) {
        this.totalnumber = totalnumber;
    }
}
