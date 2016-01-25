package edu.cmu.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.EmployeeBean;
import edu.cmu.webapp.task7.formbean.LoginFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;
import edu.cmu.webapp.task7.model.EmployeeDAO;

public class LoginAction extends Action {
    private FormBeanFactory<LoginFormBean> formBeanFactory = FormBeanFactory.getInstance(LoginFormBean.class);
    private EmployeeDAO employeeDAO;
    private CustomerDAO customerDAO;
    public LoginAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
        employeeDAO = dao.getEmployeeDAO();
    }

    public String getName() {
        return "login.do";
    }

    public String perform(HttpServletRequest request) {
//        System.out.println("LoginAction: inside perform");
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        try {
            LoginFormBean form = formBeanFactory.create(request);
//            System.out.println("form: " + form.getUsername());
//            System.out.println("form: " + form.getPassword());
//            System.out.println("request: " + request.getParameter("username"));
//            System.out.println("request: " + request.getParameter("password"));
            request.setAttribute("form", form);
            if (!form.isPresent()) {
                return "login.jsp";
            }
            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
//                System.out.println("LoginAction: has error");
                return "login.jsp";
            }
            if (form.getAction().equals("Customer Login")) {
                CustomerBean customer = customerDAO.getCustomerByUsername(form.getUsername());
                if (customer != null && customer.getPassword().equals(form.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", customer);
                    return "viewMyAccount.jsp";
                } else {
                    errors.add("User Name or Password is incorrect");
                }
            } else if (form.getAction().equals("Employee Login")) {
                System.out.println("LoginAction: action is employee login");
                EmployeeBean employee = employeeDAO.getEmployeeByUsername(form.getUsername());
                if (employee != null && employee.getPassword().equals(form.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", employee);
                    return "employeeMain.jsp";
                } else {
                    errors.add("User Name or Password is incorrect");
                }
            }
            request.setAttribute("errors", errors);
            return "login.jsp";
        } catch(Exception e) {
            return "login.jsp";
        }
    }
}
