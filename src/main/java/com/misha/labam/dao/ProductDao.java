package com.misha.labam.dao;

import com.misha.labam.conf.DatabaseConfig;
import com.misha.labam.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProductDao {
    private static final Logger logger = Logger.getLogger(ProductDao.class.getName());

    public void save(Product product) {
        String sql = "INSERT INTO product(name, price,stock) VALUES (?, ?,?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setDouble(3, product.getStock());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) product.setId(keys.getLong(1));

        } catch (SQLException e) {
            logger.severe("Error saving product: " + e.getMessage());
        }
    }

    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            logger.severe("Error finding product: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(mapRow(rs));
            }
        } catch (SQLException e) {
            logger.severe("Error finding all products: " + e.getMessage());
        }
        return products;
    }

    public void update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setLong(4, product.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error updating product: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error deleting product: " + e.getMessage());
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt( "stock")
        );
    }
}
