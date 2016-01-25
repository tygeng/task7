package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

public class LoginFormBean extends MyFormBean {
    private String username;
    private String password;
    private String action;

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getUsername() == null || getUsername().length() == 0) {
            errors.add("Username is required");
        }
        if (getPassword() == null || getPassword().length() == 0) {
            errors.add("Password is required");
        }
        if (getAction() == null) {
            errors.add("Invalid button");
        }

        if (errors.size() > 0) {
            return errors;
        }
        if (getUsername().matches(".*[<>\"].*")) {
            errors.add("User Name may not contain angle brackets or quotes");
        }

        return errors;
    }

    public boolean isEmployee() {
        return getAction() != null && getAction().equals("Employee Login");
    }

    public boolean isCustomer() {
        return getAction() != null && getAction().equals("Customer Login");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}