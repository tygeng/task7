package edu.cmu.webapp.task7.controller;

import edu.cmu.webapp.task7.databean.CustomerAccountItemBean;
import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task7.databean.PositionBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task7.model.PositionDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * After logging, customer sees his account, name, address, date of the last trading day, cash
 * balance, number of shares of each fund owned and the value of that position (shares times price
 * of fund as of the last trading day). From this view there will be links to most other
 * operations.
 */
public class ViewMyAccountAction extends Action {

    private PositionDAO positionDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;

    public ViewMyAccountAction(AbstractDAOFactory dao) {
        positionDAO = dao.getPositionDAO();
        fundPriceHistoryDAO = dao.getFundPriceHistoryDAO();
    }

    @Override
    public String getName() {
        return "viewMyAccount.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        HttpSession customerSession = request.getSession();
        CustomerBean customer = (CustomerBean) customerSession.getAttribute("user");

        if (customer == null) {
            return "login.jsp";
        }
        customerSession.setAttribute("customer", customer);

        List<PositionBean> positionList = positionDAO
                .getPositionsByCustomerId(customer.getCustomerId());

        customerSession.setAttribute("fund", positionList);

        List<CustomerAccountItemBean> fundList = new ArrayList<>();
        for (int i = 0; i < positionList.size(); i++) {
            CustomerAccountItemBean item = new CustomerAccountItemBean();
            PositionBean position = positionList.get(i);
            item.setFundName(position.getFundName());
            item.setSymbol(position.getSymbol());
            item.setShare(String.format(".3f", position.getShares() / 1000.0));

            List<FundPriceHistoryBean> fundPriceList = fundPriceHistoryDAO
                    .findFundPriceHistoryByFundId(position.getFundId());
            double price = 0;
            if (!fundPriceList.isEmpty()) {
                price = fundPriceList.get(fundPriceList.size() - 1).getPrice() / 100;
            }
            double amount = price * position.getShares();
            item.setAmount(String.format("%.2f", amount));

            fundList.add(item);
        }
        request.setAttribute("fundList", fundList);

        return "viewMyAccount.jsp";
    }
}
