package com.maxdev.warehouse.exceptions;

/**
 * Created by ytati
 * on 13.12.2022.
 */
public class WelcomeToQueueException extends RuntimeException{
    public WelcomeToQueueException(String mes){
        super(mes + " : welcome to queue");


    }
}
