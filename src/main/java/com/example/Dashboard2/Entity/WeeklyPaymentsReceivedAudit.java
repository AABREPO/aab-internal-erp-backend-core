package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class WeeklyPaymentsReceivedAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("weekly_payments_received_id")
    private Long weeklyPaymentsReceivedId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    @JsonProperty("weekly_number")
    private String weeklyNumber;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("old_type")
    private String oldType;
    @JsonProperty("old_amount")
    private String oldAmount;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("new_type")
    private String newType;
    @JsonProperty("new_amount")
    private String newAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeeklyPaymentsReceivedId() {
        return weeklyPaymentsReceivedId;
    }

    public void setWeeklyPaymentsReceivedId(Long weeklyPaymentsReceivedId) {
        this.weeklyPaymentsReceivedId = weeklyPaymentsReceivedId;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public LocalDateTime getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(LocalDateTime editedDate) {
        this.editedDate = editedDate;
    }

    public String getWeeklyNumber() {
        return weeklyNumber;
    }

    public void setWeeklyNumber(String weeklyNumber) {
        this.weeklyNumber = weeklyNumber;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(String newAmount) {
        this.newAmount = newAmount;
    }
}
