package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransitionDayFormBean extends MyFormBean {
    private String date;
    private String price;
    private String action;

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getDate() == null || getDate().length() == 0) {
            errors.add("Date is required");
        }

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (getAction() != "Update Price") {
            errors.add("Button is invalid");
        }
        try {
            Date transitionDate = dateFormat(getDate());
            //TODO: if date input is smaller than database last entry, return error.
        } catch (MyException e) {
            errors.add(e.getMessage());
        }

        try {
            String error = checkNumberFormat(getPrice());
            if (!error.equals("")) {
                errors.add(error);
            }
        } catch (MyException e) {
            errors.add(e.getMessage());
        }

        return errors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
//
//    public TransitionDayForm(HttpServletRequest request) {
//        action = request.getParameter("action");
//        date = request.getParameter("date");
//    }
//
//    // HashMap<String, Long> map:
//    // String:	    name in the jsp, related to fundid
//    // String:		price
//    public List<String> getValidationErrors(HashMap<String, String> map) {
//        List<String> errors = new ArrayList<>();
//
//        if (date == null || date.length() == 0) {
//            errors.add("Date is required");
//        }
//        if (action == null) {
//            errors.add("Button is required");
//        }
//
//        if (errors.size() > 0) {
//            return errors;
//        }
//        if (!action.equals("create")) {
//            errors.add("Invalid button");
//        }
//
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//            dateFormat.setLenient(false);
//            dateFormat.parse(date);
//        } catch (Exception e) {
//            errors.add("Invalid date input");
//        }
//
//        for (String price : map.values()) {
//
//            try {
//                double d = Double.parseDouble(price);
//                //2 digit allowed!
//                int lastDotIndex = price.lastIndexOf(".");
//                if (lastDotIndex != -1 &&
//                        price.substring(lastDotIndex + 1).length() > 2 &&
//                        Integer.parseInt(price.substring(lastDotIndex + 1)) != 0) {
//                    errors.add("Price format error!");
//                } else if (d < 0.01 || d > 10000) {
//                    errors.add("Price of shares must be between one cent (0.01) and ten thousand (10,000.00)");
//                }
//            } catch (Exception e) {
//                errors.add("Price format error!");
//            }
//        }
//
//        return errors;
}