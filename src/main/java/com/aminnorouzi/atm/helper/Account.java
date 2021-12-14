package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.Main;
import com.aminnorouzi.atm.database.CardDatabase;
import com.aminnorouzi.atm.database.UserDatabase;
import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.Transaction;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PasswordUtils;
import com.aminnorouzi.atm.util.TextUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private User user;
    private final UserDatabase userDatabase;
    private final CardDatabase cardDatabase;

    public Account(User user) {
        this.user = user;
        this.userDatabase = new UserDatabase(Main.getConnection());
        this.cardDatabase = new CardDatabase(Main.getConnection());
    }

    public Account() {
        this.userDatabase = new UserDatabase(Main.getConnection());
        this.cardDatabase = new CardDatabase(Main.getConnection());
    }

    // USER

    public boolean validateUsername(String username) {
        return getUserByUsername(username) != null;
    }

    public boolean validateUserPassword(String password) {
        return decodePassword(user.getPassword(), user.getUsername()).equals(password);
    }

    public void loadUserAccount(String username) {
        user = getUserByUsername(username);
    }

    public boolean updateUserPassword(String password) {
        try {
            user.setPassword(password);
            return userDatabase.updateUser(user);
        } catch (SQLException exception) {
            return false;
        }
    }

    public User getUserByUsername(String username) {
        try {
            return userDatabase.getUser(username);
        } catch (SQLException exception) {
            return null;
        }
    }

    public boolean isNewUser(String username) {
        try {
            List<User> users = userDatabase.getAllUsers();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    // GENERAL

    public String encodePassword(String password, String salt) {
        return PasswordUtils.encodePassword(password, salt);
    }

    public String decodePassword(String hashedPassword, String salt) {
        return PasswordUtils.decodePassword(hashedPassword, salt);
    }

    public String generatePassword() {
        return PasswordUtils.generatePassword(4, false, true);
    }

    public boolean isNewEmail(String email) {
        try {
            List<User> users = this.userDatabase.getAllUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean validateEmailPattern(String email) {
        return EmailValidator.getInstance().isValid(email) && email.matches("[A-Za-z0-9@\\-_.]*");
    }

    public boolean validateUsernamePattern(String username) {
        return username.matches("[A-Za-z0-9]*") && username.length() >= 5;
    }

    // GETTER & SETTER

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public CardDatabase getCardDatabase() {
        return cardDatabase;
    }
}
