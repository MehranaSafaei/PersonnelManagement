package org.example.entity.request;

import org.example.entity.Leave;

import java.util.List;
import java.util.Objects;

public class PersonnelRequest {

    private Long id;
    private String userName;
    private String mobile;
    private Long personnelCode;
    private String email;
    private List<Leave> leaves;

    public PersonnelRequest() {
    }

    public PersonnelRequest(Long id, String userName, String mobile, Long personnelCode, String email, List<Leave> leaves) {
        this.id = id;
        this.userName = userName;
        this.mobile = mobile;
        this.personnelCode = personnelCode;
        this.email = email;
        this.leaves = leaves;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonnelRequest that = (PersonnelRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName) && Objects.equals(mobile, that.mobile) && Objects.equals(personnelCode, that.personnelCode) && Objects.equals(email, that.email) && Objects.equals(leaves, that.leaves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, mobile, personnelCode, email, leaves);
    }
}
