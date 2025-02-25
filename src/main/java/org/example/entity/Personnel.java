package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Personnel implements Serializable {

    private Long id;
    private String userName;
    private String mobile;
    private Long personnelCode;
    private String email;
    private List<Leave> leaves;


    public Personnel() {
    }

    public Personnel(Long id, String userName, String mobile, Long personnelCode, String email) {
        this(); //call Personnel()
        System.out.println("3 arguments constructor is running");
        this.id = id;
        this.userName = userName;
        this.mobile = mobile;
        this.personnelCode = personnelCode;
        this.email = email;
        this.leaves = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getPersonnelCode() {
        return personnelCode;
    }
    public void setPersonnelCode(Long personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Leave> getLeaves() {
        return leaves;
    }
    public void setLeaves(List<Leave> leaves) {
        this.leaves = leaves;
    }


    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", personnelCode=" + personnelCode +
                ", email='" + email + '\'' +
                ", leaves=" + leaves +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnel personnel = (Personnel) o;
        return Objects.equals(id, personnel.id) && Objects.equals(userName, personnel.userName) && Objects.equals(mobile, personnel.mobile) && Objects.equals(personnelCode, personnel.personnelCode) && Objects.equals(email, personnel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, mobile, personnelCode, email);
    }
}
