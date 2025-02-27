package com.example.s27560tpo10.model;

import com.example.s27560tpo10.constraint.PasswordWithSafeComposition;
import com.example.s27560tpo10.constraint.URLNotWithSpecificProtocol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

@Entity
public class Link {
    @Id
    @Column(name = "Id")
    @NotNull
    private String id;
    @Column(name = "Name")
    @NotNull
    private String name;
    @Column(name = "TarUrl")
    @NotNull
    @URL
    @URLNotWithSpecificProtocol
    private String targetUrl;
    @Column(name = "RedUrl")
    @NotNull
    private String redirectUrl;
    @Column(name = "Visits")
    @Min(0)
    private int visits;
    @Column(name = "Password")
    @PasswordWithSafeComposition
    private String password;

    public Link(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
