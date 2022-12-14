package com.maxdev.warehouse.exceptions;

import com.maxdev.warehouse.models.Usercard;
import org.apache.catalina.User;

import java.util.function.Supplier;

public class AccessDeniedException extends RuntimeException {
    boolean showLoginPage;
    public AccessDeniedException(String deniedOperation, boolean showLoginPage){
        super(deniedOperation + " : access denied");
        this.showLoginPage=showLoginPage;

    }
    public boolean showLoginPage(){
        return showLoginPage;
    }

}
