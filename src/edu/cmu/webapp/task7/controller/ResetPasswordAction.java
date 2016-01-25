package edu.cmu.webapp.task7.controller;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.EmployeeBean;
import edu.cmu.webapp.task7.formbean.ResetPwdFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Logged-in employees can reset the password for a customer's account.
 */
public class ResetPasswordAction extends Action {
    private FormBeanFactory<ResetPwdFormBean> formBeanFactory = FormBeanFactory.getInstance(ResetPwdFormBean.class);
    private CustomerDAO customerDAO;

    public ResetPasswordAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "resetPassword.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        HttpSession employeeSession = request.getSession();
        request.setAttribute("errors", errors);

        try {
            ResetPwdFormBean form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            EmployeeBean employee = (EmployeeBean) employeeSession.getAttribute("user");

            if (employee == null) {
                return "login.jsp";
            }
            if (!form.isPresent()) {
                return "resetPassword.do";
            }
            if (form.getAction().equals("Change Password")) {
                errors.addAll(form.getValidationErrors());
                if (errors.size() > 0) {
                    return "resetCustomerPassword.jsp";
                }
                CustomerBean customer = (CustomerBean) request.getAttribute("customer");
                if (customer == null) {
                    errors.add("The customer doesn't exist.");
                    return "selectCustomerAccount.jsp";
                }
                customer.setPassword(form.getNewPassword());
                customerDAO.updateCustomer(customer);
            }

        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "resetCustomerPassword.jsp";
        }
        return "resetCustomerPassword.jsp";
    }
}
