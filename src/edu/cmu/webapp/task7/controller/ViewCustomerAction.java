package edu.cmu.webapp.task7.controller;

import edu.cmu.webapp.task7.databean.*;
import edu.cmu.webapp.task7.formbean.ViewCustomerFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;
import edu.cmu.webapp.task7.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task7.model.PositionDAO;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Logged-in employees view a customer's account information
 * including a customer's name, address, date of the last trading day, cash balance,
 * number of shares of each fund owned and the value of that position.
 */
public class ViewCustomerAction extends Action {
    private FormBeanFactory<ViewCustomerFormBean> formBeanFactory = FormBeanFactory.getInstance(ViewCustomerFormBean.class);
    private CustomerDAO customerDAO;
    private PositionDAO positionDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;

    public ViewCustomerAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
        positionDAO = dao.getPositionDAO();
        fundPriceHistoryDAO = dao.getFundPriceHistoryDAO();
    }

    @Override
    public String getName() {
        return "viewCustomer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        HttpSession employeeSession = request.getSession();
        try {
            ViewCustomerFormBean form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            EmployeeBean employee = (EmployeeBean) employeeSession.getAttribute("user");
            //Check employee login.
            if (employee == null) {
                return "login.jsp";
            }

            if (!form.isPresent()) {
                return "selectCustomerAccount.jsp";
            }

            if (form.getAction().equals("View Customer Account")) {
                errors.addAll(form.getValidationErrors());
                //check form input.
                if (errors.size() > 0) {
                    return "selectCustomerAccount.jsp";
                }
                CustomerBean customer = customerDAO.getCustomerByUsername(form.getUsername());
                //check customer existence.
                if (customer == null) {
                    errors.add("The customer doesn't exist");
                    return "selectCustomerAccount.jsp";
                }
                //print out viewMyAccount.
                request.setAttribute("customer", customer);
                List<PositionBean> positionList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
                List<CustomerAccountItemBean> fundList = new ArrayList<>();
                for (int i = 0; i < positionList.size(); i++) {
                    CustomerAccountItemBean item = new CustomerAccountItemBean();
                    PositionBean position = positionList.get(i);
                    item.setFundName(position.getFundName());
                    item.setSymbol(position.getSymbol());
                    item.setShare(String.format(".2f", position.getShares() / 1000.0));

                    List<FundPriceHistoryBean> fundPriceList = fundPriceHistoryDAO.findFundPriceHistoryByFundId(position
                            .getFundId());
                    double price = 0;
                    if (!fundPriceList.isEmpty()) {
                        price = fundPriceList.get(fundPriceList.size() - 1).getPrice() / 100;
                    }
                    double amount = price * position.getShares();
                    item.setAmount(String.format("%.2f", amount));

                    fundList.add(item);
                }
                request.setAttribute("fundlist", fundList);

                return "viewCustomerAccount.jsp";
            }
        } catch (FormBeanException e) {
            return "selectCustomerAccount.jsp";
        }
        return "selectCustomerAccount.jsp";
    }
}
