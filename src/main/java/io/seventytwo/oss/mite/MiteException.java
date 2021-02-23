package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Errors;

public class MiteException extends RuntimeException {

    private Errors errors;
    private int httpStatusCode;

    public MiteException(int httpStatusCode, Errors errors) {
        this.httpStatusCode = httpStatusCode;
        this.errors = errors;
    }

    public MiteException(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Errors getErrors() {
        return errors;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
