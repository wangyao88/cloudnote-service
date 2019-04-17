package com.sxkl.project.cloudnote.etl.service.exception;

public class LoadException extends Exception {

    private static final long serialVersionUID = 8613467016096075241L;

    public LoadException() {
        super("装载数据异常");
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }

    public LoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
