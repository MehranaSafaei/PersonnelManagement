package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class Leave implements Serializable {

    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private LeaveStatus leaveStatus;
    private LocalDateTime loginTime;
    private Personnel personnel;


    public Leave() {
        super();

    }

    public Leave(long id, LocalDate startDate, LocalDate endDate, String description, LeaveStatus leaveStatus, LocalDateTime loginTime, Personnel personnel) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.leaveStatus = leaveStatus;
        this.loginTime = loginTime;
        this.personnel = personnel;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }
    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Personnel getPersonnel() {
        return personnel;
    }
    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", leaveStatus=" + leaveStatus +
                ", loginTime=" + loginTime +
                ", personnel=" + personnel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return id == leave.id && Objects.equals(startDate, leave.startDate) && Objects.equals(endDate, leave.endDate) && Objects.equals(description, leave.description) && leaveStatus == leave.leaveStatus && Objects.equals(loginTime, leave.loginTime) && Objects.equals(personnel, leave.personnel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, description, leaveStatus, loginTime, personnel);
    }
}

  /*  @NotNull(message = "Personnel ID cannot be null")
    private Long personnelId;*/