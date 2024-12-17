package org.example.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Leave {
    private long id;
    private Date startDate;
    private Date endDate;
    private String description;
    private Long personnelId;
    private LocalDateTime loginTime;

    public Leave() {
        super();

    }

    public Leave(long id, Date startDate, Date endDate, String description, Long personnelId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.personnelId = personnelId;
//        this.loginTime = loginTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", personnelId=" + personnelId +
                ", loginTime=" + loginTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return id == leave.id && Objects.equals(startDate, leave.startDate) && Objects.equals(endDate, leave.endDate) && Objects.equals(description, leave.description) && Objects.equals(personnelId, leave.personnelId) && Objects.equals(loginTime, leave.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, description, personnelId, loginTime);
    }
}
