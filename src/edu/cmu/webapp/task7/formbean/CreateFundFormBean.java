package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;


public class CreateFundFormBean extends MyFormBean {
    private String name;
    private String symbol;
    private long firstPrice;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getName() == null || getName().length() == 0) {
            errors.add("Fund Name is required");
        }
        if (getSymbol() == null || getSymbol().length() == 0) {
            errors.add("Symbol is required");
        }
        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (getName().matches(".*[<>;\"].*")) {
            errors.add("Fund Name may not contain angle brackets or quotes");
        }
        if (getSymbol().matches(".*[<>\"];.*")) {
            errors.add("Symbol Name may not contain angle brackets or quotes");
        }

        if (!getAction().equals("Create Fund")) {
            errors.add("Invalid button");
        }

        return errors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(long firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
