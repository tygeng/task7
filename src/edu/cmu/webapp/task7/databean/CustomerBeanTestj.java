package edu.cmu.webapp.task7.databean;

import org.testng.annotations.Test;

public class CustomerBeanTestj {

    @Test
    public void test() {
        CustomerBean x = new CustomerBean();
        x.setUsername("username");
        x.setAddressLine1("address");
        System.out.println(x);
    }
}