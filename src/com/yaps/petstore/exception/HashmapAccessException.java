package com.yaps.petstore.exception;

/**
 * This exception is throw when a general problem occurs in the persistent layer.
 */
public final class HashmapAccessException extends RuntimeException {

    public HashmapAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
