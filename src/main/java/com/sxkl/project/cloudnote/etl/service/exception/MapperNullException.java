package com.sxkl.project.cloudnote.etl.service.exception;

public class MapperNullException extends Exception {

    private static final long serialVersionUID = -3850046463613412587L;

    public MapperNullException() {
        super("mapper为空，无法操作数据库");
    }

    public MapperNullException(String message) {
        super(message);
    }

    public MapperNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperNullException(Throwable cause) {
        super(cause);
    }

    public MapperNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
