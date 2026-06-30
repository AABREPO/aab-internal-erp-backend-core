package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WeeklyExpensesSummaryFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("summary_bill_copy_url")
    private String summaryBillCopyUrl;
    @JsonProperty("week_number")
    private String weekNumber;
    @JsonProperty("is_deleted")
    private boolean isDeleted;
    @JsonProperty("year")
    private String year;
}
