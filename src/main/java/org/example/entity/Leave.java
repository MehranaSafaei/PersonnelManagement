package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "leave")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;
  /*  @NotNull(message = "Personnel ID cannot be null")
    private Long personnelId;*/

    private LocalDateTime loginTime;
    @ManyToOne
    @JoinColumn(name = "personnel_id", insertable = false, updatable = false)
    private Personnel personnel;



    public Leave() {
        super();

    }

    public Leave(long id, LocalDate startDate, LocalDate endDate, String description, LeaveStatus status, LocalDateTime loginTime, Personnel personnel) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
        this.loginTime = loginTime;
        this.personnel = personnel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotNull(message = "Start date cannot be null") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Start date cannot be null") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "End date cannot be null") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "End date cannot be null") LocalDate endDate) {
        this.endDate = endDate;
    }

    public @Size(max = 500, message = "Description must be less than 500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description must be less than 500 characters") String description) {
        this.description = description;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return id == leave.id && Objects.equals(startDate, leave.startDate) && Objects.equals(endDate, leave.endDate) && Objects.equals(description, leave.description) && status == leave.status && Objects.equals(loginTime, leave.loginTime) && Objects.equals(personnel, leave.personnel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, description, status, loginTime, personnel);
    }
}
