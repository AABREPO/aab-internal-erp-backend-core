package com.example.Dashboard2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentModeArrangementOrderRequest {

    @JsonProperty("payment_mode_ids")
    private List<Long> paymentModeIds;
}
