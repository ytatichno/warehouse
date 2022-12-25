package com.maxdev.warehouse.models;

import jakarta.persistence.*;

@Entity
@Table(name="racks")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String addr;
    @OneToOne
    @JoinColumn(name="good")
    private Good good;
    private Integer number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getNumber() {
        return number!=null?number:0;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
