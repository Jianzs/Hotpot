package cn.yzh.hotpot.exception;

public class NoAuthenticationException extends Exception {
    public NoAuthenticationException() {
    }

    public NoAuthenticationException(String message) {
        super(message);
    }
}
