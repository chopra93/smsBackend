package com.sms.core.dao.impl;

import com.sms.core.dao.ISMSDao;
import com.sms.core.entity.*;
import com.sms.core.objects.LoginDTO;
import com.sms.core.objects.ServiceDTO;
import com.sms.core.objects.UserDTO;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chopra
 * 05/12/17
 */
@Repository(value = "smsDaoImpl")
public class SMSDaoImpl implements ISMSDao {

    private static final Logger LOG = Logger.getLogger(SMSDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean isUniqueUsername(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u.id FROM Users u WHERE u.username =:username");
        query.setParameter("username", username);
        Object obj = query.uniqueResult();
        if (obj == null)
            return false;
        else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean isUniquePhone(String phone) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u.id FROM Users u WHERE u.phone =:phone");
        query.setParameter("phone", phone);
        Object obj = query.uniqueResult();
        if (obj == null)
            return false;
        else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean userSignUp(UserDTO user) {
        Users userData = new Users();
        userData.setName(user.getName());
        userData.setUsername(user.getUsername());
        userData.setEmail(user.getEmail());
        userData.setPhone(user.getPhone());
        userData.setPwd(user.getPwd());
        sessionFactory.getCurrentSession().save(userData);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(userData.getUsername());
        loginDTO.setPwd(userData.getPwd());
        long expiryTime = (new Date()).getTime() + 11352960000L;


        Users users = userLogin(loginDTO);

        Service service = new Service();
        service.setServiceUsers(users);
        service.setLimit("0");
        service.setServiceType("SMS");
        service.setExpiry(expiryTime);
        service.setActive(true);


        insertService(service);

        Service service1 = new Service();
        service1.setServiceUsers(users);
        service1.setLimit("0");
        service1.setServiceType("BULK");
        service1.setExpiry(expiryTime);
        service1.setActive(true);

        insertService(service1);

        return true;

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Users userLogin(LoginDTO loginDTO) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Users u WHERE u.username =:username AND u.pwd=:pwd");
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("pwd", loginDTO.getPwd());
        Object obj = query.uniqueResult();
        if (obj == null)
            return null;
        else {
            Users user = (Users) obj;
            return user;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Users fetchUserUsingUsername(String username){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Users u WHERE u.username =:username");
        query.setParameter("username", username);
        Object obj = query.uniqueResult();
        if (obj == null)
            return null;
        else {
            Users user = (Users) obj;
            return user;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public String fetchAccessTokenBasedOnUser(Users userDetail){
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT at.token FROM AccessToken at join at.accessTokenUsers atu WHERE atu.username =:username");
        query.setParameter("username",userDetail.getUsername());
        return (String)query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean insertAccessToken(AccessToken accessToken) {
        sessionFactory.getCurrentSession().save(accessToken);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Integer userLogout(String accessToken){
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM AccessToken at WHERE at.token =:token");
        query.setParameter("token", accessToken);
        return query.executeUpdate();
    }


    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean insertService(Service service){
        Users user = service.getServiceUsers();
        List<ServiceDTO> serviceDTOS = fetchAllActiveService(user.getUsername());
        int val=0;
        if (serviceDTOS != null){
            for (ServiceDTO serviceDTO: serviceDTOS){
                if (serviceDTO.getServiceType().equals(service.getServiceType())){
                    service.setId(serviceDTO.getId());
                    val+=Integer.parseInt(serviceDTO.getLimit());
                    break;
                }
            }
        }
        if (val!=0){
            service.setLimit(Integer.toString(Integer.parseInt(service.getLimit()) + val));
            updateLimit(service.getId(),service.getLimit(),service.getServiceType());
            //Query query = sessionFactory.getCurrentSession().createQuery("UPDATE Service s SET s.limit");
            return true;
        }
        sessionFactory.getCurrentSession().save(service);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<ServiceDTO> fetchAllActiveService(String username){
        Date date = new Date();
        Long currentTime = date.getTime();
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT s.id, s.serviceType, s.limit, s.expiry FROM Service s left join s.serviceUsers su WHERE s.expiry>=:expiryTime  AND s.active=1 AND su.username=:username");
        query.setParameter("username", username);
        query.setParameter("expiryTime",currentTime);
        List<Object[]> objList = (List<Object[]>) query.list();
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for (Object[] obj:objList){
            ServiceDTO serviceDTO = new ServiceDTO();
            serviceDTO.setId((int)obj[0]);
            serviceDTO.setServiceType((String) obj[1]);
            serviceDTO.setLimit((String) obj[2]);
            serviceDTO.setExpiry((Long) obj[3]);
            serviceDTOList.add(serviceDTO);
        }
        return serviceDTOList;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean addMessage(Message message){
        sessionFactory.getCurrentSession().save(message);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Message getMessage(String message){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Message m WHERE m.message =:message");
        query.setParameter("message", message);
        List<Object> objList = query.list();
        if (objList == null || objList.size() == 0)
            return null;
        else {
            Message message1 = (Message) objList.get(0);
            return message1;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean addRecord(RecordOne recordOne){
        sessionFactory.getCurrentSession().save(recordOne);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Integer updateLimit(Integer id, String currLimit, String serviceType){
        Query query = sessionFactory.getCurrentSession().createQuery("UPDATE Service s SET s.limit=:currLimit WHERE s.serviceType=:serviceType AND s.id=:id");
        query.setParameter("currLimit", currLimit);
        query.setParameter("serviceType", serviceType);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

}