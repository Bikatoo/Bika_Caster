package com.bikatoo.lancer.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    protected Integer status;
    protected String message;
    protected String userHint;

    public GlobalException(String message) {
        this.message = message;
        this.status = 55555;
    }

    public GlobalException(Integer status, String message, String userHint) {
        this.status = status;
        this.message = message;
        this.userHint = userHint;
    }
}
