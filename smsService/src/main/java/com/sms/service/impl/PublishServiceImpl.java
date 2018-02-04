package com.sms.service.impl;

import com.google.gson.Gson;
import com.sms.config.SNSServiceUtil;
import com.sms.constant.SmsConstants;
import com.sms.core.dao.ISMSDao;
import com.sms.core.entity.Users;
import com.sms.core.enums.ServiceEnum;
import com.sms.core.objects.PublishMsgRequest;
import com.sms.core.objects.PublishResponse;
import com.sms.core.objects.ServiceDTO;
import com.sms.core.objects.UserDTO;
import com.sms.service.IPublishService;
import com.sms.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author chopra
 * 29/11/17
 */
@Service(value = "otpServiceImpl")
public class PublishServiceImpl implements IPublishService {

    private static final Gson GSON = new Gson();

    @Autowired
    private SNSServiceUtil snsServiceUtil;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private ISMSDao smsDao;

    @Override
    public PublishResponse sendOtp(PublishMsgRequest publishMsgRequest) {
        PublishResponse publishResponse = new PublishResponse();

        String token = publishMsgRequest.getToken();
        if (!token.equalsIgnoreCase(SmsConstants.DEFAULT_TOKEN)) {
            String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME, token);
            if (valueJsonUsername == null) {
                publishResponse.setMessage("Invalid User");
                publishResponse.setStatusCode(500);
                return publishResponse;
            }
        }


        String mobileNo = publishMsgRequest.getMobileNo();
        String message = publishMsgRequest.getMessage();
        ServiceEnum type = publishMsgRequest.getType();

        if (mobileNo.length()!=10 || !mobileNo.matches(".*\\d+.*")){
            publishResponse.setMessage("Not a valid number");
            publishResponse.setStatusCode(500);
            return publishResponse;
        }
        if (ServiceEnum.OTP == type){
            return sendOTPToMobile(message,mobileNo,token,type);
        }
        else
        if (ServiceEnum.SMS == type || ServiceEnum.BULK == type) {
            return sendSMS(message,mobileNo,token,type);
        }
        return null;
    }

    private PublishResponse sendOTPToMobile(String message,String mobileNo, String token, ServiceEnum type){
        PublishResponse publishResponse = new PublishResponse();
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String otp = String.valueOf(n);

        if (!message.contains(SmsConstants.OTP_TXT)){
            publishResponse.setMessage("#PIN# not found in Message");
            publishResponse.setStatusCode(500);
            return publishResponse;
        }

        if (token.equalsIgnoreCase(SmsConstants.DEFAULT_TOKEN)){
            try {
                message = message.replace(SmsConstants.OTP_TXT,otp);
                mobileNo = "+91"+mobileNo;
                snsServiceUtil.sendMessage(message,mobileNo);
                redisService.setValue("LOAN-"+ mobileNo,otp,600000);
                publishResponse.setMessage("success");
                publishResponse.setStatusCode(200);
            }
            catch (Exception e){
                publishResponse.setMessage("failure");
                publishResponse.setStatusCode(500);
            }
        }
        else {
            String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,token);
            UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
            String username = userDetail.getUsername();
            Users users = smsDao.fetchUserUsingUsername(username);
            List<ServiceDTO> serviceDTOList = smsDao.fetchAllActiveService(username);
            String otpLimit = "0";
            Integer serviceId = 0;
            for (ServiceDTO serviceDTO: serviceDTOList){
                if ((serviceDTO.getServiceType()).equalsIgnoreCase(type.toString())){
                    otpLimit = serviceDTO.getLimit();
                    serviceId = serviceDTO.getId();
                    break;
                }
            }

            int currLimit = Integer.valueOf(otpLimit);
            if(currLimit > 1){
                currLimit = currLimit - 1;
            }
            else {
                publishResponse.setMessage("Limit reached!! Buy further plan");
                publishResponse.setStatusCode(500);
                return publishResponse;
            }

            message = message.replace(SmsConstants.OTP_TXT,otp);
            mobileNo = "+91"+mobileNo;

            String valueReceivedFromRedis = redisService.getValue(mobileNo);
            if (valueReceivedFromRedis !=null){
                redisService.deleteValue(mobileNo);
            }

            try {
                snsServiceUtil.sendMessage(message,mobileNo);
                Integer updatedResponse = smsDao.updateLimit(serviceId,String.valueOf(currLimit),ServiceEnum.OTP.toString());
                redisService.setValue(mobileNo,otp,600000);
                publishResponse.setMessage("success");
                publishResponse.setStatusCode(200);

            }
            catch (Exception e){
                publishResponse.setMessage("failure");
                publishResponse.setStatusCode(500);
            }
        }
        return publishResponse;
    }

    private PublishResponse sendSMS(String message, String mobileNo, String token, ServiceEnum type){
        PublishResponse publishResponse = new PublishResponse();

        // checking limit

        String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,token);
        UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
        String username = userDetail.getUsername();
        Users users = smsDao.fetchUserUsingUsername(username);
        List<ServiceDTO> serviceDTOList = smsDao.fetchAllActiveService(username);
        String otpLimit = "0";
        Integer serviceId = 0;
        for (ServiceDTO serviceDTO: serviceDTOList){
            if ((serviceDTO.getServiceType()).equalsIgnoreCase(type.toString())){
                otpLimit = serviceDTO.getLimit();
                serviceId = serviceDTO.getId();
                break;
            }
            if ((serviceDTO.getServiceType()).equalsIgnoreCase(type.toString())){
                otpLimit = serviceDTO.getLimit();
                serviceId = serviceDTO.getId();
                break;
            }
        }

        int currLimit = Integer.valueOf(otpLimit);
        if(currLimit > 1){
            currLimit = currLimit - 1;
        }
        else {
            publishResponse.setMessage("Limit reached!! Buy further plan");
            publishResponse.setStatusCode(500);
            return publishResponse;
        }


        mobileNo = "+91"+mobileNo;

        try {
            snsServiceUtil.sendMessage(message,mobileNo);
            if (ServiceEnum.BULK == type){
                Integer updatedResponse = smsDao.updateLimit(serviceId,String.valueOf(currLimit),ServiceEnum.BULK.toString());
            }
            else if (ServiceEnum.SMS == type){
                Integer updatedResponse = smsDao.updateLimit(serviceId,String.valueOf(currLimit),ServiceEnum.SMS.toString());
            }
            publishResponse.setMessage("success");
            publishResponse.setStatusCode(200);
        }
        catch (Exception e){
            publishResponse.setMessage("failure");
            publishResponse.setStatusCode(500);
        }
        return publishResponse;
    }

    @Override
    public PublishResponse validateOtp(String mobileNo, String otp) {
        PublishResponse publishResponse = new PublishResponse();
        if (mobileNo.length()!=10 || !mobileNo.matches(".*\\d+.*")){
            publishResponse.setMessage("Not a valid number");
            publishResponse.setStatusCode(500);
            return publishResponse;
        }
        mobileNo = "+91"+mobileNo;
        String valueReceivedFromRedis = redisService.getValue(mobileNo);
        if (valueReceivedFromRedis == null){
            publishResponse.setMessage("First send OTP");
            publishResponse.setStatusCode(400);
        }
        if (otp.equalsIgnoreCase(valueReceivedFromRedis)){
            redisService.deleteValue(mobileNo);
            publishResponse.setMessage("Successfully verified");
            publishResponse.setStatusCode(200);
        }
        else {
            publishResponse.setMessage("Wrong OTP");
            publishResponse.setStatusCode(400);
        }
        return publishResponse;
    }
}
