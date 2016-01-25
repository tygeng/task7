package edu.cmu.webapp.task7.test;

import edu.cmu.webapp.task7.databean.CustomerBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.CustomerDAO;

public class TestCustomerDAO {

    public static void main(String[] args) {
        CustomerDAO customerDAO = AbstractDAOFactory.getDAOFactory().getCustomerDAO();
        
        CustomerBean customer0 = new CustomerBean();
        customer0.setUsername("test0");
        customer0.setPassword("1234");
        customer0.setFirstName("test");
        customer0.setLastName("case");
        customer0.setAddressLine1("5700");
        customer0.setAddressLine2("apt");
        customer0.setCity("Pitts");
        customer0.setState("PA");
        customer0.setZip("15206");
        customer0.setCash(12000);
        
        CustomerBean customer1 = new CustomerBean();
        customer1.setUsername("test1");
        customer1.setPassword("1234");
        customer1.setFirstName("test");
        customer1.setLastName("case");
        customer1.setAddressLine1("5700");
        customer1.setAddressLine2("apt");
        customer1.setCity("Pitts");
        customer1.setState("PA");
        customer1.setZip("15206");
        customer1.setCash(12000);
        
        CustomerBean customer2 = new CustomerBean();
        customer2.setUsername("test2");
        customer2.setPassword("1234");
        customer2.setFirstName("test");
        customer2.setLastName("case");
        customer2.setAddressLine1("5700");
        customer2.setAddressLine2("apt");
        customer2.setCity("Pitts");
        customer2.setState("PA");
        customer2.setZip("15206");
        customer2.setCash(12000);
        
        customerDAO.createCustomer(customer0);
        CustomerDAO customerDAO1 = AbstractDAOFactory.getDAOFactory().getCustomerDAO();
        customerDAO1.createCustomer(customer1);
        CustomerDAO customerDAO2 = AbstractDAOFactory.getDAOFactory().getCustomerDAO();
        customerDAO2.createCustomer(customer2);
    }

}
