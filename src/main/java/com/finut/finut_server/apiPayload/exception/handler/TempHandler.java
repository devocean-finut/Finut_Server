package com.finut.finut_server.apiPayload.exception.handler;

import com.finut.finut_server.apiPayload.code.BaseErrorCode;
import com.finut.finut_server.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
