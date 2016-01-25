package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

public class ResetPwdFormBean extends MyFormBean {

    private String customer;
    private String newPassword;
    private String confirmPassword;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getNewPassword() == null || getNewPassword().length() == 0) {
            errors.add("New password is required");
        }

        if (getConfirmPassword() == null || getConfirmPassword().length() == 0) {
            errors.add("Confirm Password is required");
        }
        if (getAction() == null || getAction().length() == 0) {
            errors.add("button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!getNewPassword().equals(getConfirmPassword())) {
            errors.add("New password and confirm password don't match");
        }

        if (!getAction().equals("Reset Password")) {
            errors.add("Invalid button");
        }
        return errors;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}


