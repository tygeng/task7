package edu.cmu.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.FundBean;
import edu.cmu.webapp.task7.databean.PositionBean;
import edu.cmu.webapp.task7.databean.TransactionBean;
import edu.cmu.webapp.task7.formbean.SellFundFormBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;
import edu.cmu.webapp.task7.model.FundDAO;
import edu.cmu.webapp.task7.model.PositionDAO;
import edu.cmu.webapp.task7.model.TransactionDAO;

public class SellFundAction extends Action {
	private FormBeanFactory<SellFundFormBean> formBeanFactory = FormBeanFactory.getInstance(SellFundFormBean.class);
	
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	
	public SellFundAction(AbstractDAOFactory model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
    	positionDAO = model.getPositionDAO();
    	transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "sellFund.do";
	}
	
    public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			
			System.out.println("SellFundAction: perform: init");
			
			SellFundFormBean form = formBeanFactory.create(request);
			
			
			
			// If user is customer and logged in already,
			// process sell fund action and return success.
			if (session != null && session.getAttribute("user") != null &&
					session.getAttribute("user") instanceof CustomerBean) {
				
				if (!form.isPresent()) {
					return "sellFund.jsp";
				}
				errors.addAll(form.getValidationErrors());
		        if (errors.size() > 0) {
		        	return "sellFund.jsp";
		        }
		        
				// Set decimal format
				DecimalFormat df3 = new DecimalFormat("#,###");
				
				int fundId = 0;
				try {
					fundId = Integer.parseInt(form.getFundId());
				} catch (Exception e) {
					errors.add("Please input a valid fundId");
					return "sellFund.jsp";
				}
				
				CustomerBean customer;
				PositionBean position;
				long sharePosition = 0L;
				synchronized (this) {
					// 1. Get current customer bean information
					// Get current fund bean information (****** not yet ******)
					customer = (CustomerBean) request.getSession(false).getAttribute("user");
					customer = customerDAO.getCustomerByUsername(customer.getUsername());
					
					// 2. Retrieve the fund position list that the customer owns currently.
					// 	  if the fund list doesn't contains fundId from request, return false;
					List<PositionBean> fundPositionList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
					//match(MatchArg.equals("username", customer.getUsername()));
					boolean found = false;
					if (fundPositionList != null && fundPositionList.size() > 0) {
						for (PositionBean pb : fundPositionList)
							if (pb.getFundId() == fundId) {
								found = true;
								sharePosition = pb.getShares();
								break;
							}
					}
					if (!found) {
						errors.add("You can't sell a fund that you don't hold.");
			        	return "sellFund.jsp"; 
					}
					
					// 3. get the corresponding available fund share balance
					position = positionDAO.getPosition(customer.getCustomerId(), fundId);
					
					// 4. Get SellFund form from request (we need #No. of share to sell)
					//	return errors and ask to input a valid number
					double shareToSell = 0;
					try {
						shareToSell = Double.parseDouble(form.getShares());
					} catch (NumberFormatException nfe) {
						errors.add("Please input a valid share number");
						return "sellFund.jsp";
					}
					
					// 5. If input exceeds the current balance
					// 		return errors and ask to input a valid number
					if (shareToSell > sharePosition / 1000) {
						errors.add("The shares to sell exceed the shares held");
						return "sellFund.jsp";
					}
					
					// 6. Otherwise, update available cash balance and queue up a pending transaction to DB
					long newShareBalance = (long) (sharePosition - shareToSell * 1000);
					position.setShares(newShareBalance);
					positionDAO.updatePosition(position);
					
					// 7. Once transition day action occurs, verify these buying fund action.
					TransactionBean transaction = new TransactionBean();
					transaction.setCustomerId(customer.getCustomerId());
					transaction.setFundId(fundId);
					transaction.setShares(newShareBalance);
					transaction.setTransactionType(TransactionBean.SELL_FUND);
					// how to set execute date without explicitly passing parameter?
					// ****Need revise******
					transaction.setExecuteDate(null);
//					transaction.setAmount(0);
					transactionDAO.createTransaction(transaction);

					// set balance attributes
					DecimalFormat df2 = new DecimalFormat("#,##0.00");
					request.setAttribute("balance", "$ " + df2.format(customer.getCash()));
				}
				
				// show fund position list of the customer
				List<PositionBean> fundList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
				request.setAttribute("fundList", fundList);
//				if (fundPositionList != null && fundPositionList.length > 0) {
//					fundList = new ArrayList<FundDisplay>();
//					
//					for (int i = 0; i < fundPositionList.length; i++) {
//						FundDisplay fd = new FundDisplay();
//						
//						FundBean fund = fundDAO.read(fundPositionList[i].getFundId());
//						
//						fd.setFundId(fundPositionList[i].getFundId());
//						fd.setFundName(fund.getName());
//						fd.setTicker(fund.getSymbol());
//						double validShare = transactionDAO.getValidShares(customer.getUsername() , fundPositionList[i].getShares() / 1000.0, fund.getFundId());
//						fd.setShares(df3.format(validShare));
//						
//						if (validShare >= 0.001) {
//							fundList.add(fd);
//						}
//					}
//				}
				
		        request.getSession().setAttribute("msg",
		        		form.getShares() +" shares sold successfully.");		        
		        request.removeAttribute("form");
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null) {
					session.removeAttribute("user");
				}
				return "login.do";
			}
		}
//		catch (RollbackException e) {
//			errors.add(e.getMessage());
//			return "sellFund.jsp";
//		}
		catch (Exception e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		}
    }
}
