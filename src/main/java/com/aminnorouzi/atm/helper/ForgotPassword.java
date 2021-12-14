package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.util.EmailUtils;
import com.aminnorouzi.atm.util.TextUtils;

public class ForgotPassword extends Account {
    private String code;

    // PASSWORD SECTION

    public void resetPassword() {
        String password = encodePassword(generatePassword(), getUser().getUsername());
        updateUserPassword(password);
    }

    public String getPasswordInfo() {
        String thePlainPassword = decodePassword(getUser().getPassword(), getUser().getUsername());
        return "New password: " + thePlainPassword;
    }

    // CODE SECTION

    public void sendCode() {
        code = generateCode();
        String subject = "Reset account password";
        String text = "Your code is: " + code;
        String receiver = getUser().getEmail();

        EmailUtils.sendEmail(subject, text, receiver);
    }

    public boolean validateCode(String code) {
        return this.code.equals(code);
    }

    private String generateCode() {
        return TextUtils.random(6, false, true);
    }
}
