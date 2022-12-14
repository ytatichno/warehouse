package com.maxdev.warehouse.security;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


public class Authentication {


    private boolean isActual;
    private String login;
    private String pwd;
    private String roles;
    private Date expirationDate;
    private String remoteAddr;

    public Authentication(String login, String pwd, String remoteAddr){
        this.login = login;
        this.pwd = pwd;
        this.remoteAddr = remoteAddr;
    }

    public Authentication setValidWithRoles(String roles){

        this.isActual = true;
        this.roles = roles;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+8);
        this.expirationDate = calendar.getTime();
        return this;
    }
    public boolean isActual() {
        return isActual = expirationDate.after(Calendar.getInstance().getTime());
    }

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public String getRole() {
        return roles;
    }

    public void setRole(String role) {
        this.roles = role;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

}
