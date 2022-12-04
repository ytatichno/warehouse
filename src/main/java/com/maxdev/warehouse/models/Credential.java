package com.maxdev.warehouse.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name="credentials")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String salt;
    @Value("${some.key:0}")
    private short saltmode;
    private String pwd;
    @OneToOne
    @JoinColumn(name = "usercard")
    private Usercard usercard;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public short getSaltmode() {
        return saltmode;
    }

    public void setSaltmode(short saltmode) {
        this.saltmode = saltmode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Usercard getUsercard() {
        return usercard;
    }

    public void setUsercard(Usercard usercard) {
        this.usercard = usercard;
    }
}





