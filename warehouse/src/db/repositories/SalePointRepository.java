package db.repositories;

import java.sql.*;
import java.util.ArrayList;

import models.storage.SalePoint;
import db.DataBase;

public class SalePointRepository {
    // add sale point
    public void add(SalePoint salePoint) throws SQLException {
        String sql = "INSERT INTO SalePoints (admin_id, profit, location) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, salePoint.adminId);
            pstmt.setDouble(2, salePoint.profit);
            pstmt.setString(3, salePoint.location);
            pstmt.executeUpdate();
        }
    }

    // update sale point info
    public void update(SalePoint salePoint) throws SQLException {
        String sql = "UPDATE SalePoints SET admin_id = ?, profit = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, salePoint.adminId);
            pstmt.setDouble(2, salePoint.profit);
            pstmt.setInt(3, salePoint.id);
            pstmt.executeUpdate();
        }
    }

    // delete sale points
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM SalePoints WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    //получение списка всех пунктов продаж
    public ArrayList<SalePoint> getAll() throws SQLException {
        ArrayList<SalePoint> salePoints = new ArrayList<>();
        String sql = "SELECT * FROM SalePoints";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                SalePoint salePoint = new SalePoint();
                salePoint.setId(set.getInt("id"));
                salePoint.setLocation(set.getString("location"));
                salePoint.setAdminId(set.getInt("admin_id"));
                salePoint.setProfit(set.getDouble("profit"));
                salePoints.add(salePoint);
            }
        }

        return salePoints;
    }

    // get sale point by id
    public SalePoint getById(int id) throws SQLException {
        SalePoint salePoint = null;
        String sql = "SELECT * FROM SalePoints WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                salePoint = new SalePoint();
                salePoint.setId(set.getInt("id"));
                salePoint.setAdminId(set.getInt("admin_id"));
                salePoint.setProfit(set.getDouble("profit"));
            }
        }

        return salePoint;
    }

    // get salepoint_id by condition
    public SalePoint getByCondition(String condition) throws SQLException {
        int id = -1;
        String sql = "SELECT ID FROM SalePoints " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return getById(id);
    }
}
