package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.util.TextUtils;

public class Login extends Account {

    public boolean validateLogin(String username, String password) {
        if (validateUsername(username)) {
            loadUserAccount(username);
            return validateUserPassword(password);
        }
        return false;
    }

    public String getUserInfo() {
        return TextUtils.capitalize(getUser().getFullName());
    }
}
