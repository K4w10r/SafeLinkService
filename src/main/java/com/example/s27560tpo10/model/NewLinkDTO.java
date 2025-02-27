package com.example.s27560tpo10.model;

import com.example.s27560tpo10.constraint.PasswordWithSafeComposition;
import com.example.s27560tpo10.constraint.URLNotWithSpecificProtocol;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class NewLinkDTO {
    @NotNull
    @Size(min = 5, max = 20)
    private String name;
    @PasswordWithSafeComposition
    private String password;
    @NotNull
    @URL
    @URLNotWithSpecificProtocol
    private String targetUrl;

    public NewLinkDTO(){}

    public NewLinkDTO(String name, String password, String targetUrl) {
        this.name = name;
        this.password = password;
        this.targetUrl = targetUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
