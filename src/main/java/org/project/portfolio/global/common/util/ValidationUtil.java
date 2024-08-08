package org.project.portfolio.global.common.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String MOBILE_REGEX = "^010-\\d{4}-\\d{4}$";
    private static final String NICKNAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_REGEX = "^(?=(?:[^0-9]*[0-9]){5})(?=(?:[^!@#$%^&*()_+]*[!@#$%^&*()_+]){2})(?=.*[a-z])(?=.*[A-Z]).{5,}$";


    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidMobile(String mobile) {
        return mobile.matches(MOBILE_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidNickname(String nickname) {
        return nickname.matches(NICKNAME_REGEX);
    }

}
