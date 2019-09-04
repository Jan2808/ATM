package de.danh.atm.exceptions;

public class InvalidPinException extends RuntimeException {
        public InvalidPinException() {
            super();
        }

    public InvalidPinException(String message) {
        super(message);
    }

    public InvalidPinException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPinException(Throwable cause) {
        super(cause);
    }

    protected InvalidPinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
