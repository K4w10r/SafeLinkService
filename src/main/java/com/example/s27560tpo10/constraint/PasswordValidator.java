package com.example.s27560tpo10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordWithSafeComposition, String> {

    private String str;
    @Override
    public void initialize(PasswordWithSafeComposition constraintAnnotation) {str = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password == null || password.equals("null"))return true;

        int countLower = 0;
        int countUpper = 0;
        int countDig = 0;
        int countSpec = 0;
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        boolean minLength = password.length() >= 10;

        if(minLength){
            for(char c : password.toCharArray()){
                if(c >= 'A' && c <= 'Z'){countUpper ++;}
                else if (c >= 'a' && c <= 'z'){countLower ++;}
                else if(c >= '0' && c <= '9'){countDig ++;}
                else if (specialChars.contains("" + c)) {
                    countSpec++;
                }
            }
            return countDig >= 3 && countLower >= 1 && countUpper >= 2 && countSpec >= 4;

        }else {return false;}
    }
}
