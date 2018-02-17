package com.sms.service;

import com.sms.core.objects.PaymentDTO;

import java.util.List;

/**
 * Created by Chopra on 17/02/18.
 */
public interface IPaymentService {
    List<PaymentDTO> fetchPaymentList();
}
