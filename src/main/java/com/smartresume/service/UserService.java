package com.smartresume.service;

import com.smartresume.dao.UserDAO;
import com.smartresume.entity.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public String registerUser(
            String name,
            String email,
            String password) {

        if (userDAO.emailExists(email)) {
            return "EMAIL_EXISTS";
        }

        User user = new User(
                name,
                email,
                password
        );

        boolean saved = userDAO.saveUser(user);

        return saved ? "SUCCESS" : "FAILED";
    }

    public User loginUser(String email) {
        return userDAO.findUserByEmail(email);
    }
}