package com.sms.service.impl;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.sms.constant.SmsConstants;
import com.sms.core.dao.ISMSDao;
import com.sms.core.entity.AccessToken;
import com.sms.core.entity.Message;
import com.sms.core.entity.RecordOne;
import com.sms.core.entity.Users;
import com.sms.core.enums.ServiceEnum;
import com.sms.core.objects.*;
import com.sms.service.IRedisService;
import com.sms.service.ISMSService;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author chopra
 * 05/12/17
 */
@Service
public class SMSServiceImpl implements ISMSService {

    private static final Logger LOGGER = Logger.getLogger(SMSServiceImpl.class);
    private static final Gson GSON = new Gson();


    @Autowired
    private ISMSDao smsDao;

    @Autowired
    private IRedisService redisService;

    @Override
    public boolean isUniqueUsername(String username) {
        return smsDao.isUniqueUsername(username);
    }

    @Override
    public boolean isUniqueNumber(String number){
        return smsDao.isUniquePhone(number);
    }

    @Override
    public boolean userSignUp(UserDTO users) {
        if ((users.getPwd()).equals(users.getPwdCompare()))
        {
            try {
                return smsDao.userSignUp(users);

            }
            catch (Exception e ){
                return false;
            }
        }
        else
            return false;
    }

    @Override
    public LoginResponse userLogin(LoginDTO loginDTO){
        if (loginDTO == null || loginDTO.getUsername() == null || loginDTO.getPwd() == null){
            return null;
        }
        LoginResponse loginResponse = new LoginResponse();
        Users user = smsDao.userLogin(loginDTO);
        if(user == null){
            LOGGER.info("Invalid username and password");
            loginResponse.setMessage("Username and password does not match");
            loginResponse.setStatus(500);
            return loginResponse;
        }
        String tokenFromDB = smsDao.fetchAccessTokenBasedOnUser(user);
        if (tokenFromDB!= null ){
            String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,tokenFromDB);
            if (valueJsonUsername == null){
                redisService.putInMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,tokenFromDB, GSON.toJson(convertFromUserDTOTOUsers(user)));
            }
            else {
                UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
                if (!tokenFromDB.equals(userDetail.getAccessToken())){
                    redisService.putInMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,tokenFromDB, GSON.toJson(convertFromUserDTOTOUsers(user)));
                }
            }
            loginResponse.setMessage("success");
            loginResponse.setToken(tokenFromDB);
            loginResponse.setStatus(200);
            return loginResponse;
        }
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        Date date = new Date();
        long expiryTime = date.getTime() + 11352960000L;

        AccessToken accessToken = new AccessToken();
        accessToken.setToken(token);
        accessToken.setExpiry(expiryTime);
        accessToken.setAccessTokenUsers(user);
        accessToken.setActive(true);

        boolean accessTokenInsertResponse = smsDao.insertAccessToken(accessToken);
        if (accessTokenInsertResponse){
            redisService.putInMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,token, GSON.toJson(user));
            loginResponse.setMessage("success");
            loginResponse.setToken(token);
            loginResponse.setStatus(200);
        }
        else {
            loginResponse.setMessage("failure");
            loginResponse.setStatus(500);
        }
        return loginResponse;
    }

    @Override
    public LogoutResponse userLogout(String token){
        LogoutResponse logoutResponse = new LogoutResponse();
        Long val = redisService.deleteFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,token);
        if (val == 0){
            logoutResponse.setMessage("failure");
            logoutResponse.setStatusCode(500);
        }
        else {
            int tokenDeleteResponse = smsDao.userLogout(token);
            if (tokenDeleteResponse !=0){
                logoutResponse.setMessage("success");
                logoutResponse.setStatusCode(200);
            }
            else {
                logoutResponse.setMessage("failure");
                logoutResponse.setStatusCode(200);
            }
        }
        return logoutResponse;
    }

    @Override
    public ServiceResponse updateServiceAfterPayment(ServiceDTO serviceDTO, String number){

        Users users = smsDao.fetchUserUsingUsername(number);
        com.sms.core.entity.Service serviceEntity = new com.sms.core.entity.Service();
        serviceEntity.setServiceUsers(users);
        serviceEntity.setLimit(serviceDTO.getLimit());
        serviceEntity.setServiceType(serviceDTO.getServiceType());
        long expiryTime = (new Date()).getTime() + 11352960000L;
        serviceEntity.setExpiry(expiryTime);
        serviceEntity.setActive(true);

        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            Boolean serviceInsertionResponse = smsDao.insertService(serviceEntity);
            if (serviceInsertionResponse){
                serviceResponse.setMessage("Success");
                serviceResponse.setStatusCode(200);
                return serviceResponse;
            }
            else {
                serviceResponse.setMessage("Failure");
                serviceResponse.setStatusCode(500);
                return serviceResponse;
            }
        }
        catch (Exception e){
            serviceResponse.setMessage("Exception");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }


    }

    @Override
    public ServiceResponse createNewService(ServiceDTO serviceDTO){
        ServiceResponse serviceResponse = new ServiceResponse();
        if (serviceDTO == null || serviceDTO.getToken() == null){
            serviceResponse.setMessage("Object cannot be null");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,serviceDTO.getToken());
        if (StringUtils.isEmpty(valueJsonUsername)){
            serviceResponse.setMessage("Invalid Token");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        else {
            com.sms.core.entity.Service serviceEntity = new com.sms.core.entity.Service();
            Date date = new Date();
            long expiryTime = date.getTime() + 11352960000L;

            serviceEntity.setExpiry(expiryTime);
            serviceEntity.setLimit(serviceDTO.getLimit());
            serviceEntity.setActive(true);
            if ((ServiceEnum.OTP.toString()).equals(serviceDTO.getServiceType())){
                serviceEntity.setServiceType(ServiceEnum.OTP.toString());
            }
            else
            if((ServiceEnum.BULK.toString()).equals(serviceDTO.getServiceType()))
            {
                serviceEntity.setServiceType(ServiceEnum.BULK.toString());
            }
            else {
                serviceEntity.setServiceType(ServiceEnum.SMS.toString());
            }

            UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
            Users users = smsDao.fetchUserUsingUsername(userDetail.getUsername());
            if (userDetail == null){
                serviceResponse.setMessage("Token Expired");
                serviceResponse.setStatusCode(500);
                return serviceResponse;
            }
            serviceEntity.setServiceUsers(users);

            try {
                Boolean serviceInsertionResponse = smsDao.insertService(serviceEntity);
                if (serviceInsertionResponse){
                    serviceResponse.setMessage("Success");
                    serviceResponse.setStatusCode(200);
                    return serviceResponse;
                }
                else {
                    serviceResponse.setMessage("Failure");
                    serviceResponse.setStatusCode(500);
                    return serviceResponse;
                }
            }
            catch (Exception e){
                serviceResponse.setMessage("Exception");
                serviceResponse.setStatusCode(500);
                return serviceResponse;
            }

        }
    }

    @Override
    public ServiceResponse fetchAllActiveServices(String token){
        ServiceResponse serviceResponse = new ServiceResponse();
        String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,token);
        if (StringUtils.isEmpty(valueJsonUsername)){
            serviceResponse.setMessage("Invalid Token");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
        List<ServiceDTO> serviceDTOS = smsDao.fetchAllActiveService(userDetail.getUsername());

//        for (ServiceDTO serviceDTO: serviceDTOS){
//            if (serviceDTO.getServiceType().equalsIgnoreCase("SMS")){
//                serviceDTO.setUrl(redisService.getValue("SMS"));
//            }
//            else
//            if (serviceDTO.getServiceType().equalsIgnoreCase("OTP")){
//                serviceDTO.setUrl(redisService.getValue("OTP"));
//            }
//            else
//            if (serviceDTO.getServiceType().equalsIgnoreCase("BULK")){
//                serviceDTO.setUrl(redisService.getValue("BULK"));
//            }
//        }

        if (serviceDTOS.isEmpty()){
            serviceResponse.setMessage("No Active Service");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        serviceResponse.setMessage("Service List");
        serviceResponse.setStatusCode(200);
        serviceResponse.setServiceList(serviceDTOS);
        return serviceResponse;
    }

    private UserDTO convertFromUserDTOTOUsers(Users users){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(users.getName());
        userDTO.setUsername(users.getUsername());
        userDTO.setAccessToken(users.getAccessToken().getToken());
        userDTO.setEmail(users.getEmail());
        userDTO.setPhone(users.getPhone());
        return userDTO;
    }

    @Override
    public boolean addMobileNumberAndMessage(String token, FileItemStream item, String message) {
        try {
            Message messageObject = smsDao.getMessage(message);
            if (messageObject == null){
                LOGGER.info("null message respone received");
                Message insetToMessage = new Message();
                insetToMessage.setActive(true);
                insetToMessage.setMessage(message);
                boolean responseFromMessageInsertion = smsDao.addMessage(insetToMessage);
                if (!responseFromMessageInsertion){
                    return false;
                }
                messageObject = smsDao.getMessage(message);
            }

            String valueJsonUsername = redisService.getFromMap(SmsConstants.USERTOKEN_USERNAME_MAP_NAME,token);
            UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
            Users users = smsDao.fetchUserUsingUsername(userDetail.getUsername());

            InputStreamReader inputStreamReader = new InputStreamReader(item.openStream());
            CSVReader reader = new CSVReader(inputStreamReader);
            String[] line;
            while ((line = reader.readNext()) != null) {
                int currentLenth = line.length;
                for (int i=0;i<currentLenth;i++){
                    LOGGER.info("size of numbers = "+line.length);
                    if (line[i].length() <13 && line[i].length()>4){

                        RecordOne recordOne = new RecordOne();
                        recordOne.setActive(true);
                        recordOne.setMessage(messageObject);
                        recordOne.setMobile(line[i]);
                        recordOne.setRecordOneUser(users);

                        smsDao.addRecord(recordOne);

                    }
                }
            }
            return true;

        } catch (IOException e) {
            LOGGER.info("Exception Occured");
            return false;
        }
    }

}
