package com.example.s27560tpo10.model;

import com.example.s27560tpo10.constraint.PasswordWithSafeComposition;
import com.example.s27560tpo10.constraint.URLNotWithSpecificProtocol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public class LinkDTO {
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    @URL
    @URLNotWithSpecificProtocol
    private String targetUrl;
    private String redirectUrl;
    @Min(0)
    private int visits;
    @JsonIgnore
    @NotNull
    @PasswordWithSafeComposition
    private String password;

    public LinkDTO(){}

    public LinkDTO(String name, String targetUrl, String redirectUrl, String password) {
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.password = password;
    }

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
