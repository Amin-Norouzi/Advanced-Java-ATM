package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.model.User;

import java.sql.SQLException;

public class Settings extends Account {

    public Settings(User user) {
        super(user);
    }

    // USER PASSWORD SECTION

    public void changeUserPassword(String newPassword) {
        String password = encodePassword(newPassword, getUser().getUsername());
        updateUserPassword(password);
    }

    // CARD PASSWORD SECTION

    public void changeCardPassword(String newPassword) {
        String password = encodePassword(newPassword, getUser().getCard().getCardNumber());
        updateCardPassword(password);
    }

    public boolean updateCardPassword(String password) {
        try {
            getUser().getCard().setPassword(password);
            return getCardDatabase().updateCard(getUser().getCard());
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean validateCardPassword(String password) {
        return decodePassword(getUser().getCard().getPassword(), getUser().getCard().getCardNumber()).equals(password);
    }

    // USER INFO SECTION

    public void editUserInfo(String fullName, String email, String which) {
        switch (which) {
            case "fullName":
                updateUserInfo(fullName, getUser().getEmail());
                break;
            case "both":
                updateUserInfo(fullName, email);
                break;
        }
    }

    public boolean updateUserInfo(String fullName, String email) {
        try {
            getUser().setFullName(fullName);
            getUser().setEmail(email);
            return getUserDatabase().updateUser(getUser());
        } catch (SQLException exception) {
            return false;
        }
    }

    // DELETE USER SECTION

    public boolean deleteUser() {
        try {
            return getUserDatabase().deleteUser(getUser().getUsername());
        } catch (SQLException exception) {
            return false;
        }
    }
}
