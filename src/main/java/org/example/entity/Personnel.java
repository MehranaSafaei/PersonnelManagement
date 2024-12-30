package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Personnel")
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "User name must be less than 50 characters")
    private String userName;

    @Size(max = 15, message = "Mobile number must be less than 15 characters")
    private String mobile;

    private Long personnelCode;

    @Size(max = 100, message = "Email must be less than 100 characters")
    @NotNull
    private String email;

    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Override
    public String toString() {
        return "Personnel{" + "id=" + id + ", userName='" + userName + '\'' + ", mobile='" + mobile + '\'' + ", personnelCode=" + personnelCode + '\'' + ", email='" + email + '}';
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
