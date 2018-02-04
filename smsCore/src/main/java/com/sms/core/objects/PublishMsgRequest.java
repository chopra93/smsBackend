package com.sms.core.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sms.core.enums.ServiceEnum;

/**
 * Created by Chopra on 09/12/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishMsgRequest {
    private String token;
    private String mobileNo;
    private String message;
    private ServiceEnum type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceEnum getType() {
        return type;
    }

    public void setType(ServiceEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PublishMsgRequest{" +
                "token='" + token + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
