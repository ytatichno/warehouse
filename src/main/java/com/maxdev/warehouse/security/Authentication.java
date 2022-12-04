package com.maxdev.warehouse.security;

import org.springframework.stereotype.Component;


public class Authentication {


    private boolean isActual;
    private String login;
    private String pwd;
    private String roles;
    public Authentication(String login, String pwd){
        this.login = login;
        this.pwd = pwd;
    }

    public Authentication setValidWithRoles(String roles){
        this.isActual = true;
        this.roles = roles;
        return this;
    }
    public boolean isActual() {
        return isActual;
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
}
