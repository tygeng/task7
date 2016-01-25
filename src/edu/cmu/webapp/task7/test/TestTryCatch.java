package edu.cmu.webapp.task7.test;

public class TestTryCatch {
    public static void main(String[] args) {
        try {
            System.out.println(1);
        } catch (Exception e) {
            System.out.println("inside catch");
            e.printStackTrace();
        } finally {
            System.out.println("inside finnaly");
        }
        System.out.println("the last sentence");
    }
}
