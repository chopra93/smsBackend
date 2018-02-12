package com.sms.core.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Chopra on 10/02/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstamojoRequest {
    private String buyer_phone;
    private String offer_slug;

    public String getBuyer_phone() {
        return buyer_phone;
    }

    public void setBuyer_phone(String buyer_phone) {
        this.buyer_phone = buyer_phone;
    }

    public String getOffer_slug() {
        return offer_slug;
    }

    public void setOffer_slug(String offer_slug) {
        this.offer_slug = offer_slug;
    }

    @Override
    public String toString() {
        return "InstamojoRequest{" +
                "buyer_phone='" + buyer_phone + '\'' +
                ", offer_slug='" + offer_slug + '\'' +
                '}';
    }
}
