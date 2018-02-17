package com.sms.core.dao.impl;

import com.sms.core.dao.IPaymentDao;
import com.sms.core.objects.PaymentDTO;
import com.sms.core.objects.ServiceDTO;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chopra on 17/02/18.
 */
@Repository(value = "paymentDaoImpl")
public class PaymentDaoImpl implements IPaymentDao {

    private static final Logger LOG = Logger.getLogger(PaymentDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<PaymentDTO> fetchPaymentList() {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT p.paymentUrl, p.type FROM Payment p WHERE p.active = '1'");
        List<Object[]> objList = (List<Object[]>) query.list();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for (Object[] obj:objList){
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentUrl((String)obj[0]);
            paymentDTO.setType((String)obj[1]);
            paymentDTOList.add(paymentDTO);
        }

        return paymentDTOList;
    }
}
