package com.example.Dashboard2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaymentModeArrangementResponse {

    private Long id;

    @JsonProperty("module_name")
    private String moduleName;

    @JsonProperty("payment_mode_ids")
    private List<Long> paymentModeIds = new ArrayList<>();

    @JsonProperty("payment_modes")
    private List<PaymentModeArrangementItemDTO> paymentModes = new ArrayList<>();

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
