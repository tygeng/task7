package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;


public class CreateCustomerFormBean extends MyFormBean {
    private String username;
    private String firstName;
    private String password;
    private String confirmPassword;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zip;
    private long cash;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getUsername() == null || getUsername().length() == 0) {
            errors.add("User Name is required");
        }
        if (getFirstName() == null || getFirstName().length() == 0) {
            errors.add("First Name is required");
        }
        if (getLastName() == null || getLastName().length() == 0) {
            errors.add("Last Name is required");
        }
        if (getPassword() == null || getPassword().length() == 0) {
            errors.add("Password is required");
        }
        if (getConfirmPassword() == null || getConfirmPassword().length() == 0) {
            errors.add("Confirm password is required");
        }
        if (!getPassword().equals(getConfirmPassword())) {
            errors.add("Passwords are not the same");
        }
        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (getUsername().matches(".*[<>\"].*")) {
            errors.add("User Name may not contain angle brackets or quotes");
        }
        if (getFirstName().matches(".*[<>\"].*")) {
            errors.add("First Name may not contain angle brackets or quotes");
        }
        if (getLastName().matches(".*[<>\"].*")) {
            errors.add("Last Name may not contain angle brackets or quotes");
        }
        if (getPassword().matches(".*[<>\"].*")) {
            errors.add("Password may not contain angle brackets or quotes");
        }
        if ((getAddressLine1() != null && getAddressLine1().matches(".*[<>\"].*"))
                || (getAddressLine2() != null && getAddressLine2().matches(".*[<>\"].*"))) {
            errors.add("Address may not contain angle brackets or quotes");
        }
        if (getCity() != null && getCity().matches(".*[<>\"].*")) {
            errors.add("City may not contain angle brackets or quotes");
        }
        if (getState() != null && getState().matches(".*[<>\"].*")) {
            errors.add("State may not contain angle brackets or quotes");
        }
        if (getZip() != null && getState().matches(".*[<>\"].*")) {
            errors.add("Zip Code may not contain angle brackets or quotes");
        }

        if (!getAction().equals("Create Customer")) {
            errors.add("Invalid button");
        }

        return errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
