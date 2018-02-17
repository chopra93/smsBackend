package com.sms.core.dao;

import com.sms.core.objects.PaymentDTO;

import java.util.List;

/**
 * Created by Chopra on 17/02/18.
 */
public interface IPaymentDao {
   List<PaymentDTO> fetchPaymentList();
}
