package com.misha.labam.dao;

import com.misha.labam.conf.DatabaseConfig;
import com.misha.labam.entity.Order;
import com.misha.labam.entity.Product;
import com.misha.labam.entity.User;
import com.misha.labam.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private final Connection connection =  DatabaseConfig.getConnection();

    public OrderDao() throws SQLException {
    }


    public void save(Order order) {
        String sql = "INSERT INTO orders ( user_id, product_id) VALUES ( ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, order.getUser().getId());
            stmt.setLong(2, order.getProduct().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order", e);
        }
    }

    public List<Order> findByUserId(Long userId) {
        String sql = "SELECT  user_id, product_id FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User userId1 = new UserDao().findById(rs.getLong("user_id"));
                Product product = new ProductDao().findById(rs.getLong("product_id")).get();
                Order order = new Order(
                        userId1,
                        product
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding orders by userId", e);
        }
        return orders;
    }
    public void deleteById(long id, Long userId) {
        String sql = "DELETE FROM orders WHERE product_id = ? and user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order by ID", e);
        }
    }

    public void deleteAll(Long userId) {
        String sql = "DELETE FROM orders where user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting all orders", e);
        }
    }

}
