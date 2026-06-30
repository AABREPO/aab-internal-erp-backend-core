package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_mode_arrangement_for_modules")
@Getter
@Setter
public class PaymentModeArrangementForModules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_name", nullable = false, unique = true)
    @JsonProperty("module_name")
    private String moduleName;

    @Convert(converter = PaymentModeIdListConverter.class)
    @Column(name = "payment_mode_ids", columnDefinition = "TEXT", nullable = false)
  private List<Long> paymentModeIds = new ArrayList<>();

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
