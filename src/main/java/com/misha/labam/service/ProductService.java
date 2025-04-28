package com.misha.labam.service;

import com.misha.labam.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {

    public EntityManager entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();

    public void save(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public void update(Product product) {
        entityManager.merge(product);
    }

    public void delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        entityManager.getTransaction().begin();
        if (product != null) {
            entityManager.remove(product);
        }
        entityManager.getTransaction().commit();
        entityManager.close();

    }
}