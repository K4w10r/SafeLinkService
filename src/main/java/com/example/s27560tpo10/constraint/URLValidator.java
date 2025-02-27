package com.example.s27560tpo10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class URLValidator implements ConstraintValidator<URLNotWithSpecificProtocol, String> {
    private String protocol;

    @Override
    public void initialize(URLNotWithSpecificProtocol constraintAnnotation){protocol = constraintAnnotation.protocol();}

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context){return url.startsWith(protocol);}
}
