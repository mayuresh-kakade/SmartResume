package com.smartresume.service;

import com.smartresume.dao.UserDAO;
import com.smartresume.entity.User;
import com.smartresume.util.PasswordUtil;

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

        String hashedPassword =
                PasswordUtil.hashPassword(password);

        User user =
                new User(
                        name,
                        email,
                        hashedPassword
                );

        boolean saved =
                userDAO.saveUser(user);

        return saved
                ? "SUCCESS"
                : "FAILED";
    }

    public User loginUser(String email) {

        return userDAO.findUserByEmail(email);
    }

    public User getUserById(int userId) {

        return userDAO.findUserById(userId);
    }
}