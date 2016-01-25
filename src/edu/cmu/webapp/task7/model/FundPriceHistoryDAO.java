package edu.cmu.webapp.task7.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cmu.webapp.task7.databean.FundBean;
import edu.cmu.webapp.task7.databean.FundPriceHistoryBean;

public class FundPriceHistoryDAO {
    private SessionFactory sessionFactory;
    public FundPriceHistoryDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }
    public void createFundPriceHistory(FundPriceHistoryBean fundPriceHistory) {
        if (fundPriceHistory == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(fundPriceHistory);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot create a new fundPriceHistory into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateFundPriceHistory(FundPriceHistoryBean fundPriceHistory) {
        if (fundPriceHistory == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(fundPriceHistory);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot update the fundPriceHistory into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteFundPriceHistory(FundPriceHistoryBean fundPriceHistory) {
        if (fundPriceHistory == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(fundPriceHistory);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("cannot delete fundPriceHistory.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public List<FundPriceHistoryBean> findFundPriceHistoryByFundId(int fundId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct fph from FundPriceHistoryBean fph where fph.fundId=%d", fundId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<FundPriceHistoryBean> result = new ArrayList<FundPriceHistoryBean>();
            while (it.hasNext()) {
                result.add((FundPriceHistoryBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get fundPriceHistory by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public List<FundPriceHistoryBean> findFundPriceHistoryByPriceDate(String priceDate) {
        if (priceDate == null) {
            return null;
        }
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("from FundPriceHistoryBean fph where fph.priceDate=%s", priceDate);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<FundPriceHistoryBean> result = new ArrayList<FundPriceHistoryBean>();
            while (it.hasNext()) {
                FundPriceHistoryBean item = (FundPriceHistoryBean) it.next();
                result.add(item);
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get fundPriceHistory by price date from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void deleteFundPriceHistoryByFundId(int fundId) {
        
    }
    public void deleteFundPriceHistoryByPriceDate(String priceDate) {
        
    }
}
