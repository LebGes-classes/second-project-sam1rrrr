package db.repositories;

import models.Order;
import db.DataBase;

import java.sql.*;
import java.util.ArrayList;
public class OrderRepository {
    // add order
    public void add(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (status, client_id, product_id, amount, total_price, date, employee_id, sale_point_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, order.status);
            pstmt.setInt(2, order.clientId);
            pstmt.setInt(3, order.productId);
            pstmt.setInt(4, order.amount);
            pstmt.setDouble(5, order.totalPrice);
            pstmt.setLong(6, order.date);
            pstmt.setInt(7, order.employeeId);
            pstmt.setInt(8, order.salePointId);
            pstmt.executeUpdate();
        }
    }

    // update order
    public void update(Order order) throws SQLException {
        String sql = "UPDATE Orders SET status = ?, client_id = ?, product_id = ?, amount = ?, total_price = ?, date = ?, employee_id = ?, sale_point_id = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, order.status);
            pstmt.setInt(2, order.clientId);
            pstmt.setInt(3, order.productId);
            pstmt.setInt(4, order.amount);
            pstmt.setDouble(5, order.totalPrice);
            pstmt.setLong(6, order.date);
            pstmt.setInt(7, order.employeeId);
            pstmt.setInt(8, order.salePointId);
            pstmt.setInt(9, order.id);
            pstmt.executeUpdate();
        }
    }

    // delete order
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Orders WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // get all orders
    public ArrayList<Order> getAll() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("id"));
                order.setStatus(set.getString("status"));
                order.setClientId(set.getInt("client_id"));
                order.setProductId(set.getInt("product_id"));
                order.setAmount(set.getInt("amount"));
                order.setTotalPrice(set.getDouble("total_price"));
                order.setDate(set.getLong("date"));
                order.setEmployeeId(set.getInt("employee_id"));
                order.setSalePointId(set.getInt("sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    // get order by id
    public Order getById(int id) throws SQLException {
        Order order = null;

        String sql = "SELECT * FROM Orders WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                order = new Order();
                order.setId(set.getInt("id"));
                order.setStatus(set.getString("status"));
                order.setClientId(set.getInt("client_id"));
                order.setProductId(set.getInt("product_id"));
                order.setAmount(set.getInt("amount"));
                order.setTotalPrice(set.getDouble("total_price"));
                order.setDate(set.getLong("date"));
                order.setEmployeeId(set.getInt("employee_id"));
                order.setSalePointId(set.getInt("sale_point_id"));
            }
        }

        return order;
    }

    // get all orders by client
    public ArrayList<Order> getOrdersByClient(int clientId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders WHERE client_id = " + clientId;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("id"));
                order.setStatus(set.getString("status"));
                order.setClientId(set.getInt("client_id"));
                order.setProductId(set.getInt("product_id"));
                order.setAmount(set.getInt("amount"));
                order.setTotalPrice(set.getDouble("total_price"));
                order.setDate(set.getLong("date"));
                order.setEmployeeId(set.getInt("employee_id"));
                order.setSalePointId(set.getInt("sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    // get all orders by product id
    public ArrayList<Order> getOrdersByProduct(int productId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders WHERE product_id = " + productId;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("id"));
                order.setStatus(set.getString("status"));
                order.setClientId(set.getInt("client_id"));
                order.setProductId(set.getInt("product_id"));
                order.setAmount(set.getInt("amount"));
                order.setTotalPrice(set.getDouble("total_price"));
                order.setDate(set.getLong("date"));
                order.setEmployeeId(set.getInt("employee_id"));
                order.setSalePointId(set.getInt("sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    // get all orders by product id
    public ArrayList<Order> getOrdersByCondition(String condition) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("id"));
                order.setStatus(set.getString("status"));
                order.setClientId(set.getInt("client_id"));
                order.setProductId(set.getInt("product_id"));
                order.setAmount(set.getInt("amount"));
                order.setTotalPrice(set.getDouble("total_price"));
                order.setDate(set.getLong("date"));
                order.setEmployeeId(set.getInt("employee_id"));
                order.setSalePointId(set.getInt("sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }
}
