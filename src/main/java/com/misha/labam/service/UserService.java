package com.misha.labam.service;

import com.misha.labam.dao.UserDao;
import com.misha.labam.dto.LoginDto;
import com.misha.labam.entity.User;
import com.misha.labam.entity.Role;
import com.misha.labam.security.PasswordService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserService {
    private final UserDao userDao = new UserDao();
    private final PasswordService passwordService = new PasswordService();
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public User register(LoginDto dto) {
        logger.info("Attempting to register user with email: " + dto.getEmail());

        if (userDao.exists(dto.getEmail())) {
            logger.warning("User with email already exists: " + dto.getEmail());
            throw new RuntimeException("User already exists");
        }

        String hashedPassword = passwordService.hashPassword(dto.getPassword());
        Role role = dto.getEmail().equals("admin@admin") ? Role.ADMIN : Role.USER;
        User user = new User(null, dto.getEmail(), hashedPassword, role);

        User savedUser = userDao.save(user);
        logger.info("User registered successfully with id: " + savedUser.getId());
        return savedUser;
    }

    public User authenticate(String email, String password) {
        logger.info("Authenticating user: " + email);

        Optional<User> userOpt = userDao.findByEmail(email);
        if (userOpt.isEmpty()) {
            logger.warning("Authentication failed: user not found");
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        if (passwordService.checkPassword(password, user.getPassword())) {
            logger.info("Authentication successful for user: " + email);
            return user;
        } else {
            logger.warning("Authentication failed: invalid password for user " + email);
            throw new RuntimeException("Invalid password");
        }
    }

    public List<User> getAllUsers() {
        logger.info("Retrieving all users");
        return userDao.findAll();
    }

    public void deleteUser(long id) {
        logger.info("Deleting user with id: " + id);
        userDao.delete(id);
    }

    public boolean exists(String email) {
        boolean exists = userDao.exists(email);
        logger.info("Checked existence for user " + email + ": " + exists);
        return exists;
    }

    public User findByEmail(String userName) {
        logger.info("Finding user by email: " + userName);
        return userDao.findByEmail(userName).orElseThrow(() -> {
            logger.warning("User not found: " + userName);
            return new RuntimeException("User not found");
        });
    }
}
