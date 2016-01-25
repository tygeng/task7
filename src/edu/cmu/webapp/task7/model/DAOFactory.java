package edu.cmu.webapp.task7.model;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAOFactory extends AbstractDAOFactory {
    public static SessionFactory CreateSessionFactory() throws Exception {
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            return sessionFactory;
        } catch (HibernateException e) {
            System.out.println("Cannot get session factory.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAO();
    }
    @Override
    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAO();
    }
    @Override
    public PositionDAO getPositionDAO() {
        return new PositionDAO();
    }
    @Override
    public TransactionDAO getTransactionDAO() {
        return new TransactionDAO();
    }
    @Override
    public FundDAO getFundDAO() {
        return new FundDAO();
    }
    @Override
    public FundPriceHistoryDAO getFundPriceHistoryDAO() {
        return new FundPriceHistoryDAO();
    }
}
