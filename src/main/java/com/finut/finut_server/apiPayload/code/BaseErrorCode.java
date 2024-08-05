package com.finut.finut_server.apiPayload.code;

public interface BaseErrorCode {
    public ErrorReasonDTO getReason();
    public ErrorReasonDTO getReasonHttpStatus();
}
