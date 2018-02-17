package com.sms.service.impl;

import com.sms.core.dao.IPaymentDao;
import com.sms.core.objects.PaymentDTO;
import com.sms.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chopra on 17/02/18.
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentDao paymentDao;

    @Override
    public List<PaymentDTO> fetchPaymentList() {
        return paymentDao.fetchPaymentList();
    }
}
