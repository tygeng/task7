package edu.cmu.webapp.task7.model;

import edu.cmu.webapp.task7.databean.TransactionBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionDAO {
    private SessionFactory sessionFactory;

    public TransactionDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }

    public void createTransaction(TransactionBean transaction) {
        if (transaction == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(transaction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot create a new transaction into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateTransaction(TransactionBean transaction) {
        if (transaction == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(transaction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot update the transaction into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteTransaction(TransactionBean transaction) {
        if (transaction == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("cannot delete transaction.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<TransactionBean> findTransactionsByNullExecuteDate() {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            List list = session.createQuery("select distinct tx from TransactionBean tx where tx.executeDate is null").list();
            Iterator it = list.iterator();
            List<TransactionBean> result = new ArrayList<TransactionBean>();
            while (it.hasNext()) {
                result.add((TransactionBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get TransactionBean by null execute date.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<TransactionBean> findTransactionsByCustomerId(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct tx from TransactionBean tx where tx.customerId=%d", customerId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<TransactionBean> result = new ArrayList<TransactionBean>();
            while (it.hasNext()) {
                result.add((TransactionBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get TransactionBean by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<TransactionBean> findTransactionsByFundId(int fundId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct tx from TransactionBean tx where tx.fundId=%d", fundId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<TransactionBean> result = new ArrayList<TransactionBean>();
            while (it.hasNext()) {
                result.add((TransactionBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get TransactionBean by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void deleteTransactionsByCustomerId(int customerId) {

    }

    public void deleteTransactionsByFundId(int fundId) {

    }
}
