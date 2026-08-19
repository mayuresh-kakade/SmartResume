package com.smartresume.test;

import com.smartresume.service.UserService;

public class HibernateTest {

    public static void main(String[] args) {

        UserService userService = new UserService();

        String result = userService.registerUser(
                "Test User",
                "test999@gmail.com",
                "12345"
        );

        System.out.println("Registration Result: " + result);

        if ("SUCCESS".equals(result)) {

            System.out.println("==============================");
            System.out.println("USER REGISTERED SUCCESSFULLY");
            System.out.println("==============================");

        } else if ("EMAIL_EXISTS".equals(result)) {

            System.out.println("==============================");
            System.out.println("EMAIL ALREADY EXISTS");
            System.out.println("==============================");

        } else {

            System.out.println("==============================");
            System.out.println("REGISTRATION FAILED");
            System.out.println("==============================");
        }
    }
}