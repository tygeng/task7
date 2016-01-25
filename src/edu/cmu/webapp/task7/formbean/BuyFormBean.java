package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

public class BuyFormBean extends MyFormBean {
    private String dollarAmount;
    private String fund;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        if (getDollarAmount() == null || getDollarAmount().length() == 0) {
            errors.add("Please enter a dollar amount");
        }

        try {
//        	if (getDollarAmount().matches(".[<>\"].")) {
            double x = Double.parseDouble(getDollarAmount());
        } catch (NumberFormatException nfe) {
            errors.add("Illegal format for dollar amount");
        }

        if (getFund() == null || getFund().length() == 0) {
            errors.add("Please choose a fund");
        }

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }
        //The dollar amount user entered should be within range of (10, 1,000,000).
        try {
            String error = checkNumberFormat(dollarAmount);
            if (!error.equals("")) {
                errors.add(error);
            }
        } catch (MyException e) {
            errors.add(e.getMessage());
        }

        if (!getAction().equals("Buy Fund")) {
            errors.add("Invalid button");
        }
        return errors;
    }

    public String getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(String dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
