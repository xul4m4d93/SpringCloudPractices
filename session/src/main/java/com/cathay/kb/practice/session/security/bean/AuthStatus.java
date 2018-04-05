package com.cathay.kb.practice.session.security.bean;

public class AuthStatus {

    public static AuthStatus initAuthStatus(String authMessage) {
        return new AuthStatus(authMessage);
    }

    public static AuthStatus initAuthStatus(String authMessage, String errorType) {
        return new AuthStatus(authMessage, errorType);
    }

    private AuthStatus(String authMessage) {
        this.authMessage = authMessage;
    }

    private AuthStatus(String authMessage, String errorType) {
        this.authMessage = authMessage;
        this.errorType = errorType;
    }

    private String authMessage;
    private String errorType;

    public String getAuthMessage() {
        return authMessage;
    }

    public void setAuthMessage(String authMessage) {
        this.authMessage = authMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
