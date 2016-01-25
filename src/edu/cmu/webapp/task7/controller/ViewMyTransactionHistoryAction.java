package edu.cmu.webapp.task7.controller;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.FundDAO;
import edu.cmu.webapp.task7.model.TransactionDAO;
import edu.cmu.webapp.task7.controller.Action;
import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.FundBean;
import edu.cmu.webapp.task7.databean.TransactionBean;
import edu.cmu.webapp.task7.databean.TransactionRecordBean;

public class ViewMyTransactionHistoryAction extends Action {
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	DecimalFormat sharePriceDF = new DecimalFormat("#,##0.00");
	DecimalFormat amountDF = new DecimalFormat("#,##0.00");
	DecimalFormat sharesDF = new DecimalFormat("#,##0.000");
	
	public ViewMyTransactionHistoryAction(AbstractDAOFactory dao) {
		transactionDAO = dao.getTransactionDAO();
		fundDAO = dao.getFundDAO();
	}

	public String getName() {
		return "viewMyTransactionHistory.do";
	}

	public String perform(HttpServletRequest request) {
        HttpSession customerSession = request.getSession();
        CustomerBean customer = (CustomerBean) customerSession.getAttribute("user");

        List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

        if (customer == null) {
            return "login.jsp";
        }
        try {
        	customerSession.setAttribute("customer", customer);        

        	List<TransactionBean> history = (List<TransactionBean>) transactionDAO.findTransactionsByCustomerId(customer.getCustomerId());
        	if (history.isEmpty()) {
				errors.add("No Transaction History exists !");
				return "viewMyTransactionHistory.jsp";
			}

        	List<TransactionRecordBean> historyRecord = new ArrayList<TransactionRecordBean>();

            for (int i = 0; i < history.size(); i++) {
            	TransactionRecordBean trb = new TransactionRecordBean();

            	trb.setExecutedate(history.get(i).getExecuteDate());
            	switch (history.get(i).getTransactionType()) {
            	case 1:
            		trb.setTransactionType("Buy");
            		break;
            	case 2:
            		trb.setTransactionType("Sell");
            		break;
            	case 3:
            		trb.setTransactionType("Request Check");
            		break;
            	case 4:
            		trb.setTransactionType("Deposit Check");
            		break;
            	}

            	if(history.get(i).getFundId() != 0) {
            	FundBean fb= fundDAO.getFundById(history.get(i).getFundId());
            	trb.setFundName(fb.getName());}

            	if(history.get(i).getShares() != 0) {
            	String shares = sharesDF.format((history.get(i).getShares() / 1000));
            	trb.setShares(shares);
            	}

            	if(history.get(i).getShares() != 0 && history.get(i).getAmount() != 0) {
            	String sharePrice = sharePriceDF.format((history.get(i).getAmount()/history.get(i).getShares()/ 100));
            	trb.setSharePrice(sharePrice);
            	}
            	
            	if(history.get(i).getAmount() != 0) {
            	String amount = amountDF.format((history.get(i).getAmount() / 100));
            	trb.setAmount(amount);
            	}
            	

            	historyRecord.add(trb);
            }

            request.setAttribute("historyRecord", historyRecord);
        	return "viewMyTransactionHistory.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "viewMyTransactionHistory.jsp";
		}
	}
}
