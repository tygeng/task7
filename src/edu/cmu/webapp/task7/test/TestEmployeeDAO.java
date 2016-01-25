package edu.cmu.webapp.task7.test;

import edu.cmu.webapp.task7.databean.EmployeeBean;
import edu.cmu.webapp.task7.model.AbstractDAOFactory;
import edu.cmu.webapp.task7.model.EmployeeDAO;

public class TestEmployeeDAO {

    public static void main(String[] args) {
        EmployeeDAO employeeDAO0 = AbstractDAOFactory.getDAOFactory().getEmployeeDAO();
        EmployeeBean employee = employeeDAO0.getEmployeeByUsername("test0");
        System.out.println(employee.getFirstName());
    }

}
