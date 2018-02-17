package com.sms.core.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Chopra on 17/02/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {

    private List<PaymentDTO> payment;

    public List<PaymentDTO> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentDTO> payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "payment=" + payment +
                '}';
    }
}
