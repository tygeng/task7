package edu.cmu.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task7.databean.EmployeeBean;
import edu.cmu.webapp.task7.databean.FundBean;
import edu.cmu.webapp.task7.formbean.CreateFundFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.FundDAO;

public class CreateFundAction extends Action {
    private FormBeanFactory<CreateFundFormBean> formBeanFactory = FormBeanFactory.getInstance(CreateFundFormBean.class);

    private FundDAO fundDAO;

    public CreateFundAction(AbstractDAOFactory dao) {
        fundDAO = dao.getFundDAO();
    }

    public String getName() {
        return "createFund.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            // If user is employee and logged in already,
            // process and return success
            if (session.getAttribute("user") != null && session.getAttribute("user") instanceof EmployeeBean) {
                CreateFundFormBean form = formBeanFactory.create(request);
                request.setAttribute("form", form);

                // If no parameters were passed in the form,
                // return createCustomer.jsp
                if (!form.isPresent()) {
                    return "createFund.jsp";
                }

                // If any validation errors, return createCustomer.jsp
                errors.addAll(form.getValidationErrors());
                if (errors.size() != 0) {
                    return "createFund.jsp";
                }

                // given name and symbol, search in DB first to find any
                // duplicate
                // If the name/symbol username has been created before,
                // return errors

                if (fundDAO.getFundByName(form.getName()) != null) {
                    errors.add("This username already exists");
                    return "createCustomer.jsp";
                }

                // If everything is correct, create new fund
                // using fundDAO to create new fundbean
                FundBean newFund = new FundBean();
                newFund.setName(form.getName());
                newFund.setSymbol(form.getSymbol());

                fundDAO.createFund(newFund);

                // remove form attribute and show success reminder

//                request.removeAttribute("form");
                System.out.println("newFund has been created! ");
                request.setAttribute("form", null);
                request.getSession().setAttribute("msg", "New fund is created successfully");

                return "viewCustomerAccount.do";

            }

            else {

                // if the user is customer,
                // he/she is not allowed to create new fund
                // don't show to him/her
                if (session.getAttribute("user") != null)
                    session.removeAttribute("user");
                return "login.do";
            }
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "createFund.jsp";
        }

    }
}
