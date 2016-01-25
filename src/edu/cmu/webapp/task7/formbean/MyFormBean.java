package edu.cmu.webapp.task7.formbean;

import org.mybeans.form.FormBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Siqi Wang siqiw1 on 1/14/16.
 */
public abstract class MyFormBean extends FormBean {
    public abstract List<String> getValidationErrors();

    public String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }

    public class MyException extends Exception {
        private static final long serialVersionUID = 1L;

        public MyException(Exception e) {
            super(e);
        }

        public MyException(String s) {
            super(s);
        }
    }

    public String checkNumberFormat(String number) throws MyException {
        try {
            double num = Double.parseDouble(number);
            Pattern NUM_FORMAT = Pattern.compile("^[1-9][0-9]{1,6}\\\\.?[0-9]{0,2}$");
            Boolean hasError = NUM_FORMAT.matcher(number).matches();
            if (hasError) {
                return "Dollar input should within the range of $0.01 to $1,000,000.00";
            } else {
                return "";
            }
        } catch (NumberFormatException nfe) {
            throw new MyException("Please input digits only");
        }
    }

    public Date dateFormat(String date) throws MyException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new MyException("Please input date" + date + "in the format of \"MM/dd/yyyy\"");
        }
    }

}
