package com.example.Dashboard2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentModeArrangementItemDTO {

    private Long id;

    @JsonProperty("mode_of_payment")
    private String modeOfPayment;
}
