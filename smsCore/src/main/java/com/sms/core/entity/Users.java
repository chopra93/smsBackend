package com.sms.core.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author chopra
 * 05/12/17
 */
@Entity
@Table(name = "users")
public class Users extends BaseEntity{
    private String name;
    private String username;
    private String phone;
    private String email;
    private String pwd;
    private AccessToken accessToken;
    private Set<Service> service;
    private Set<RecordOne> recodeOne;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(mappedBy = "accessTokenUsers", cascade = CascadeType.ALL)
    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @OneToMany(mappedBy = "serviceUsers", cascade = CascadeType.ALL)
    public Set<Service> getService() {
        return service;
    }

    public void setService(Set<Service> service) {
        this.service = service;
    }

    @Column(name = "pwd", nullable = false)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @OneToMany(mappedBy = "recordOneUser", cascade = CascadeType.ALL)
    public Set<RecordOne> getRecodeOne() {
        return recodeOne;
    }

    public void setRecodeOne(Set<RecordOne> recodeOne) {
        this.recodeOne = recodeOne;
    }
}
