package edu.cmu.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.EmployeeBean;
import edu.cmu.webapp.task7.databean.FundBean;
import edu.cmu.webapp.task7.databean.TransactionBean;
import edu.cmu.webapp.task7.formbean.BuyFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;
import edu.cmu.webapp.task7.model.EmployeeDAO;
import edu.cmu.webapp.task7.model.FundDAO;
import edu.cmu.webapp.task7.model.PositionDAO;
import edu.cmu.webapp.task7.model.TransactionDAO;

public class BuyFundAction extends Action {
    private FormBeanFactory<BuyFormBean> formBeanFactory = 
    		FormBeanFactory.getInstance(BuyFormBean.class);
    
    private CustomerDAO customerDAO;
    private FundDAO fundDAO;
	private TransactionDAO transactionDAO;

    public BuyFundAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
        fundDAO = dao.getFundDAO();
        transactionDAO = dao.getTransactionDAO();
    }
	
	public String getName() {
		return "buyFund.do";
	}
	
	public String perform(HttpServletRequest request) {
		// Set errors attributes
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession(false);
		
		try {
			// Only customer can buy fund
			if (session.getAttribute("user") == null ||
					session.getAttribute("user") instanceof EmployeeBean) {
				errors.add("Please Use Customer Login");
				return "login.jsp";
			}
			
			// Get BuyFund FormBean from request
			BuyFormBean form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return "buyFund.jsp";
			}
			errors.addAll(form.getValidationErrors());
			request.setAttribute("errors", errors);
			if (errors.size() > 0) {
				return "buyFund.jsp";
			}
			
			String fundName = form.getFund();
			double amountToBuy = Double.parseDouble(form.getDollarAmount());
			String availableBalanceString = "";
			
			// Set up user cases list in navigation bar
			// request.setAttribute("customerList", customerDAO.match());
			
			// Get current customer information
			CustomerBean customer = (CustomerBean) session.getAttribute("user");
			synchronized (this) { 
				customer = customerDAO.getCustomerByUsername(customer.getUsername());

				// Retrieve current available cash balance
				double availableBalance = customer.getCash() / 100;
						//transactionDAO.getValidBalance(user.getUsername(), user.getCash() / 100.0);
				
				// Compare the buy fund amount with balance
				// If input is invalid - exceed the current balance,
				// return errors and ask to input a valid number
				if (availableBalance < amountToBuy) {
					errors.add("The balance is not enough.");
					return "buyFund.jsp";
				}
				
				// if the fund is not found, return errors
				FundBean fundBean = fundDAO.getFundByName(fundName);
				if (fundBean == null) {
					errors.add("Fund name does not exist.");
					return "buyFund.jsp";
				}
				
				// Otherwise, update available cash balance and queue up a pending transaction to DB
				availableBalance -= amountToBuy;
				customer.setCash((long) availableBalance * 100);
				customerDAO.updateCustomer(customer);
				
				// Once transition day action occurs, verify these buying fund action.
				TransactionBean transaction = new TransactionBean();
				transaction.setCustomerId(customer.getCustomerId());
				transaction.setAmount((long) (Double.parseDouble(form.getDollarAmount()) * 100));
				transaction.setTransactionType(TransactionBean.BUY_FUND);
				transaction.setFundId(fundBean.getFundId());
				// how to set execute date without explicitly passing parameter?
				// ****Need revise******
				transaction.setExecuteDate(null);
				//transaction.setShares(0);
				transactionDAO.createTransaction(transaction);
				
				DecimalFormat df2 = new DecimalFormat("#,##0.00");
				availableBalanceString = "$" + df2.format(availableBalance);
				request.setAttribute("balance", availableBalanceString);
			}
			
			List<FundBean> fundList = fundDAO.getFundList();
			System.out.println(fundList);
			request.setAttribute("fundList", fundList);
			
//			user = customerDAO.read(user.getUsername());
//			if (! transactionDAO.buyFund(user.getUsername(), user.getCash()/100, amount, fb[0].getFundId()) ){
//				errors.add("You do not have enough cash available.");
//				return "buyFund.jsp";	
//			}

//			availableBalance = transactionDAO.getValidBalance(user.getUsername(), user.getCash() / 100.0);
			
			request.setAttribute("msg",
					"Buy fund successfully!\n" +
					"Amount: $" + form.getDollarAmount() + "\n" +
					"Fund:    " + form.getFund() + "\n");
			
//			if (errors.size() > 0) { 
//				return "buyFund.jsp";
//			}
			
			return "success.do";
		}
//		catch (RollbackException e) {
//			errors.add(e.getMessage());
//			return "buyFund.jsp";
//		}
		catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
	}

}
