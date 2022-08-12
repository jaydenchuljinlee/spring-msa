package com.bbrick.trade.comm.exceptions;

public abstract class BaseException extends RuntimeException{
    public BaseException(String message) { super(message); }

    public BaseException(Throwable cause) { super(cause); }

    public BaseException(String message, Throwable cause) { super(message, cause); }


}
