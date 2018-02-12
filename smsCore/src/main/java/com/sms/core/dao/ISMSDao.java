package com.sms.core.dao;


import com.sms.core.entity.*;
import com.sms.core.objects.LoginDTO;
import com.sms.core.objects.ServiceDTO;
import com.sms.core.objects.UserDTO;

import java.util.List;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSDao {
    boolean isUniqueUsername(String username);
    boolean isUniquePhone(String phone);
    boolean userSignUp(UserDTO user);
    Users userLogin(LoginDTO loginDTO);
    Users fetchUserUsingUsername(String username);
    Integer userLogout(String accessToken);
    boolean insertAccessToken(AccessToken accessToken);
    boolean insertService(Service service);
    List<ServiceDTO> fetchAllActiveService(String username);
    String fetchAccessTokenBasedOnUser(Users userDetail);
    boolean addMessage(Message message);
    boolean addRecord(RecordOne recordOne);
    Message getMessage(String message);
    Integer updateLimit(Integer id, String currLimit, String serviceType);
}
