package edu.cmu.webapp.task7.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.databean.FundBean;

public class CustomerDAO {
    private SessionFactory sessionFactory;
    public CustomerDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }
    public void createCustomer(CustomerBean customer) {
        if (customer == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot create a new customer into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateCustomer(CustomerBean customer) {
        if (customer == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(customer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot update the customer into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public boolean usernameAvailable(String username) {
        if (username == null || username.length() == 0) {
            return false;
        }
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct c from CustomerBean c where c.username='%s'", username);
            session.beginTransaction();
            CustomerBean customer = (CustomerBean) session.createQuery(hql).uniqueResult();
            return customer == null;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot whether username available.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
    public CustomerBean getCustomerByUsername(String username) {
        if (username == null || username.length() == 0) {
            return null;
        }
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct c from CustomerBean c where c.username='%s'", username);
            session.beginTransaction();
            CustomerBean customer = (CustomerBean) session.createQuery(hql).uniqueResult();
            return customer;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get the CustomerBean from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public CustomerBean getCustomerById(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct c from CustomerBean c where c.customerId=%d", customerId);
            session.beginTransaction();
            CustomerBean customer = (CustomerBean) session.createQuery(hql).uniqueResult();
            return customer;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get the CustomerBean from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void deleteCustomerById(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            CustomerBean customer = (CustomerBean) session.get(CustomerBean.class, customerId);
            session.delete(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("cannot delete customer by customer id.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteCustomer(CustomerBean customer) {
        if (customer == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("cannot delete customer.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
