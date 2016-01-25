package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

public class RequestCheckFormBean extends MyFormBean {
    private String dollarAmount;
    private String confirmAmount;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getDollarAmount() == null || getDollarAmount().length() == 0) {
            errors.add("Amount is required");
        }
        if (getConfirmAmount() == null || getConfirmAmount().length() == 0) {
            errors.add("Confirm Amount is required");
        }
        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!getAction().equals("Request Check")) {
            errors.add("Invalid button");
        }
        if (errors.size() > 0) {
            return errors;
        }

        //The dollar amount user entered should be within range of (10, 1,000,000).
        try {
            String error = checkNumberFormat(getDollarAmount());
            if (!error.equals("")) {
                errors.add(error);
            }
            String error2 = checkNumberFormat(getConfirmAmount());
            if (!error2.equals("")) {
                errors.add(error2);
            }
        } catch (MyException e) {
            errors.add(e.getMessage());
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (Double.parseDouble(getDollarAmount()) != Double.parseDouble(getConfirmAmount())) {
            errors.add("Dollar amount and the confirmed don't match");
        }
        return errors;
    }

    public String getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(String dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public String getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(String confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}