package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

public class SellFundFormBean extends MyFormBean {
    private String fundId;
    private String action;
    private String shares;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getShares() == null || getShares().length() == 0) {
            errors.add("Number of shares to sell is required");
        }
        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }
        if (!getAction().equals("Sell Fund")) {
            errors.add("Invalid button");
        }

        try {
            double d = Double.parseDouble(getShares());
            if (d < 0.001 || d > 1000000) {
                throw new MyException("Shares to sell must be number and within the range of 0.001 to 1,000,000");
            }
        } catch (MyException e) {
            errors.add(e.getMessage());
        }

        return errors;
    }


    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
