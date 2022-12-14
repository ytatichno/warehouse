package com.maxdev.warehouse.exceptions;

/**
 * Created by ytati
 * on 13.12.2022.
 */
public class UnsafeOperationException extends RuntimeException{
    public UnsafeOperationException(String operation){
        super(operation+" : unsafe operation");
    }
}
