package com.maxdev.warehouse.exceptions;

/**
 * Created by ytati
 * on 08.12.2022.
 */
public class NotFindException extends RuntimeException{
    public NotFindException(String findOperation){
        super(findOperation + " : can't find");


    }

}
