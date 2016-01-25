package edu.cmu.webapp.task7.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.formbean.RequestCheckFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;
import edu.cmu.webapp.task7.model.TransactionDAO;


public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckFormBean> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckFormBean.class);

	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;

	public RequestCheckAction(AbstractDAOFactory model) {
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "requestCheck.do";
	}

	public String perform(HttpServletRequest request) {
		// Get seesion
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			// If user is already logged in, redirect to todolist.do
			if (session != null && session.getAttribute("user") != null &&
					session.getAttribute("user") instanceof CustomerBean) {
				RequestCheckFormBean form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				// If no params were passed, return with no errors so that the
				// form will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "requestCheck.jsp";
				}
				
				// Check form validation errors
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "requestCheck.jsp";
				}
				
				synchronized (this) {
					CustomerBean customer = (CustomerBean) session.getAttribute("user");
					customer = customerDAO.getCustomerByUsername(customer.getUsername());
					double availableBalance = customer.getCash() / 100.0;
					//transactionDAO.getValidBalance(customer.getUsername(), customer.getCash() / 100.0 );
					
					// Get Request Withdraw Amount from form
					NumberFormat formatter = new DecimalFormat("#,##0.00"); 
					double withdrawAmount = 0;
					try {
						withdrawAmount = Double.parseDouble(form.getDollarAmount());
					} catch (NumberFormatException nfe) {
						return "requestCheck.jsp";
					}
					
					// if withdrawAmount > availableBalance
					if (withdrawAmount > availableBalance) {
						errors.add("Amount requested cannot be higher than available cash balance");
						return "requestCheck.jsp";
					}
					
					availableBalance = (availableBalance - withdrawAmount) * 100;
					customer.setCash((long) availableBalance);
					customerDAO.updateCustomer(customer);
					
					request.getSession().setAttribute("msg",
							"Withdraw Sucessfully!\n"
							+ "Amount: $"+ formatter.format(withdrawAmount)+ "\n"
							+ "Please check your bank account after our transition day.\n");
					String balanceString = formatter.format(availableBalance);
					session.setAttribute("balance", balanceString);
				}
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		}
	}
}
