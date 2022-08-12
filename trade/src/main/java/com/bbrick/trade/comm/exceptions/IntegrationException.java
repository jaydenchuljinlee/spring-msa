package com.bbrick.trade.comm.exceptions;

public class IntegrationException extends BaseException{
    public IntegrationException(String message) { super(message); }

    public IntegrationException(Throwable casue) { super(casue); }

    public IntegrationException(String message, Throwable cause) { super(message, cause); }
}
