package edu.cmu.webapp.task7.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cmu.webapp.task7.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task7.databean.PositionBean;

public class PositionDAO {
    private SessionFactory sessionFactory;
    public PositionDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }
    public void createPosition(PositionBean position) {
        if (position == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(position);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot create a new position into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updatePosition(PositionBean position) {
        if (position == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(position);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot update the position into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public PositionBean getPosition(int customerId, int fundId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct pos from PositionBean pos where pos.customerId=%d and pos.fundId=%d", customerId, fundId);
            session.beginTransaction();
            PositionBean position = (PositionBean) session.createQuery(hql).uniqueResult();
            return position;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get the position from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public List<PositionBean> getPositionsByCustomerId(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct pos from PositionBean pos where pos.customerId=%d", customerId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<PositionBean> result = new ArrayList<PositionBean>();
            while (it.hasNext()) {
                result.add((PositionBean) it.next());
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
    public List<PositionBean> getPositionsByFundId(int fundId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct pos from PositionBean pos where pos.fundId=%d", fundId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<PositionBean> result = new ArrayList<PositionBean>();
            while (it.hasNext()) {
                result.add((PositionBean) it.next());
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
    public void deletePosition(PositionBean position) {
        if (position == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(position);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("cannot delete position.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deletePositionsByCustomerId(int customerId) {
        
    }
    public void deletePositionsByFundId(int fundId) {
        
    }
}
