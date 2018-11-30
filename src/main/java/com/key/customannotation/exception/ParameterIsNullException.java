package com.key.customannotation.exception;

import java.lang.reflect.Method;

public class ParameterIsNullException extends Exception {

    public ParameterIsNullException(String message){
        super(message);
    }

    public ParameterIsNullException(Method method, int paramentIndex) {
        super("[in method: " + method.getName() + "] the " + paramentIndex + "th parameter " + "can not be null");
    }
}
