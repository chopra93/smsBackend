package com.sms.service;


import com.sms.core.objects.PublishMsgRequest;
import com.sms.core.objects.PublishResponse;

/**
 * @author chopra
 * 29/11/17
 */
public interface IPublishService {
    PublishResponse sendOtp(PublishMsgRequest publishMsgRequest);
    PublishResponse validateOtp(String mobileNo, String otp);
}
