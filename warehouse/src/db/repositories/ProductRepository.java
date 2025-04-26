package db.repositories;

import java.sql.*;
import java.util.ArrayList;

import models.Product;
import db.DataBase;

public class ProductRepository {
    // add product
    public void add(Product product) throws SQLException {
        String sql = "INSERT INTO Products " +
                "(name, price, sell_price, " +
                "status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, product.name);
            pstmt.setDouble(2, product.price);
            pstmt.setDouble(3, product.sellPrice);
            pstmt.setString(4, product.status);
            pstmt.executeUpdate();
        }
    }

    // update product info
    public void update(Product product) throws SQLException {
        String sql = "UPDATE Products SET name = ?, price = ?, sell_price = ?, status = ? WHERE id = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, product.name);
            pstmt.setDouble(2, product.price);
            pstmt.setDouble(3, product.sellPrice);
            pstmt.setString(4, product.status);
            pstmt.setInt(5, product.id);
            pstmt.executeUpdate();
        }
    }

    // delete product
    public void delete(Product product) throws SQLException {
        String sql = "DELETE FROM Products WHERE ID = " + product.id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // delete product by id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Products WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // get all products
    public ArrayList<Product> getAll() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
                product.setPrice(set.getDouble("price"));
                product.setSellPrice(set.getDouble("sell_price"));
                product.setStatus(set.getString("status"));
                products.add(product);
            }
        }

        return products;
    }

    // get all products by condition
    public ArrayList<Product> getAllByCondition(String condition) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
                product.setPrice(set.getDouble("price"));
                product.setSellPrice(set.getDouble("sell_price"));
                product.setStatus(set.getString("status"));
                products.add(product);
            }
        }

        return products;
    }

    // get product by id
    public Product getById(int id) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM Products WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                product = new Product();
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
                product.setPrice(set.getDouble("price"));
                product.setSellPrice(set.getDouble("sell_price"));
                product.setStatus(set.getString("status"));
            }
        }

        return product;
    }

    // get product_id by condition
    public int getByCondition(String condition) throws SQLException {
        int id = -1;
        String sql = "SELECT ID FROM Products " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }

    public int getLastAddedId() throws SQLException {
        int lastAddedId = 1;
        String sql = "SELECT MAX(ID) FROM Products";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }
}
