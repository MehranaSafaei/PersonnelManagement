package org.example.entity.request;

import java.util.Objects;

public class LeaveRequest {
    private int id;
    private String startDate;
    private String endDate;
    private String description;

    public LeaveRequest(int id, String employeeName, String startDate, String endDate, String description) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LeaveRequest that = (LeaveRequest) o;
        return id == that.id && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, description);
    }
}
