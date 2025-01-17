package com.dxc.clients.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ControllerException extends Exception {
    private static final long serialVersionUID = 1L;

    public ControllerException(String message){
        super(message);
    }
}