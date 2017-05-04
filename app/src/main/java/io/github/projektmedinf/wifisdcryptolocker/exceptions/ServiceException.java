package io.github.projektmedinf.wifisdcryptolocker.exceptions;

/**
 * Created by stiefel40k on 04.05.17.
 */
public class ServiceException extends Exception {

    public ServiceException() { super(); }
    public ServiceException(String message) { super(message); }
    public ServiceException(String message, Throwable cause) { super(message, cause); }
    public ServiceException(Throwable cause) { super(cause); }
}
