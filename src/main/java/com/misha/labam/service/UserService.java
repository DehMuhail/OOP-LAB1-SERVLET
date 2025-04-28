package com.misha.labam.service;


import com.misha.labam.dto.LoginDto;
import com.misha.labam.entity.Role;
import com.misha.labam.entity.User;
import com.misha.labam.security.PasswordService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

@ApplicationScoped
public class UserService {

    private  PasswordService passwordService = new PasswordService();




    public User findByEmail(String email) {
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager()) {
            em.clear();
            User user = em.createQuery("SELECT u FROM User u where u.email = :email", User.class).setParameter("email", email).getSingleResult();
            em.refresh(user);
            return user;
        }catch (RuntimeException e){
            return null;
        }
    }

    public List<User> findAll() {
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT u FROM User u ",User.class).getResultList();
        }
    }



    public boolean exists(String email) {
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager()) {
            Long count = em.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return count > 0;
        }
    }



    public User save(LoginDto loginDto) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
             EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            User user = new User(
                    null,
                    loginDto.getEmail(),
                    passwordService.hashPassword(loginDto.getPassword()),
                    loginDto.getEmail().equals("admin@admin")?Role.ADMIN:Role.USER
            );
            em.persist(user);
            transaction.commit();
            return user;
        }
    }

    public User authenticate(String email, String password) {
        User byEmailWithPW = findByEmail(email);
        System.out.println(byEmailWithPW);
        System.out.println(byEmailWithPW.getPassword());
        if (passwordService.checkPassword( password,byEmailWithPW.getPassword())) {
            return byEmailWithPW;
        }else {
            throw new RuntimeException("Wrong password");
        }
    }

    public void deleteUser(long userId) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
             EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            // Find the user within the same transaction
            User user = em.find(User.class, userId);
            if (user != null) {
                em.remove(user);
            }

            transaction.commit();
        }
    }

    public User findById(Long id) {
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager()) {
            em.clear();
            User user = em.createQuery("SELECT u FROM User u where u.id = :id", User.class).setParameter("id", id).getSingleResult();
            em.refresh(user);
            return user;
        }
    }

    public User saveAsAdmin(User admin) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
             EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            admin.setPassword(passwordService.hashPassword(admin.getPassword()));
            em.persist(admin);
            transaction.commit();
            return admin;
        }
    }
}

