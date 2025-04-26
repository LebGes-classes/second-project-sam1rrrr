package db.repositories;

import models.storage.Cell;
import db.DataBase;
import java.sql.*;
import java.util.ArrayList;

public class CellRepository {
    // add cell to storage
    public void addToStorage(Cell cell) throws SQLException {
        String sql = "INSERT INTO Cells (storage_id, product_id, product_amount, storage_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, cell.storageId);
            pstmt.setInt(2, cell.productId);
            pstmt.setInt(3, cell.productAmount);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
        }
    }

    // add cell to sale_point
    public void addToSalePoint(Cell cell) throws SQLException {
        String sql = "INSERT INTO Cells (storage_id, product_id, product_amount, storage_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, cell.storageId);
            pstmt.setInt(2, cell.productId);
            pstmt.setInt(3, cell.productAmount);
            pstmt.setInt(4, cell.storageId);
            pstmt.executeUpdate();
        }
    }

    // update cell
    public void update(Cell cell) throws SQLException {
        String sql = "UPDATE Cells SET storage_id = ?, product_id = ?, product_amount = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, cell.storageId);
            pstmt.setInt(2, cell.productId);
            pstmt.setInt(3, cell.productAmount);
            pstmt.setInt(4, cell.id);
            pstmt.executeUpdate();
        }
    }

    // delete cell
    public void delete(Cell cell) throws SQLException {
        String sql = "DELETE FROM Cells WHERE ID = " + cell.id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // delete cell by id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Cells WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // get all cells
    public ArrayList<Cell> getAll() throws SQLException {
        ArrayList<Cell> cells = new ArrayList<>();
        String sql = "SELECT * FROM Cells";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Cell cell = new Cell();
                cell.setId(set.getInt("id"));
                cell.setStorageId(set.getInt("storage_id"));
                cell.setProductId(set.getInt("product_id"));
                cell.setProductAmount(set.getInt("product_amount"));
                cells.add(cell);
            }
        }

        return cells;
    }

    // get all cells by condition
    public ArrayList<Cell> getAllByCondition(String condition) throws SQLException {
        ArrayList<Cell> cells = new ArrayList<>();
        String sql = "SELECT * FROM Cells " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Cell cell = new Cell();
                cell.setId(set.getInt("id"));
                cell.setStorageId(set.getInt("storage_id"));
                cell.setProductId(set.getInt("product_id"));
                cell.setProductAmount(set.getInt("product_amount"));
                cells.add(cell);
            }
        }

        return cells;
    }

    // get cell by id
    public Cell getById(int id) throws SQLException {
        Cell cell = null;
        String sql = "SELECT * FROM Cells WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                cell = new Cell();
                cell.setId(set.getInt("id"));
                cell.setStorageId(set.getInt("storage_id"));
                cell.setProductId(set.getInt("product_id"));
                cell.setProductAmount(set.getInt("product_amount"));
            }
        }

        return cell;
    }

    // get cell by id
    public Cell getByCondition(String condition) throws SQLException {
        Cell cell = null;
        String sql = "SELECT * FROM Cells " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                cell = new Cell();
                cell.setId(set.getInt("id"));
                cell.setStorageId(set.getInt("storage_id"));
                cell.setProductId(set.getInt("product_id"));
                cell.setProductAmount(set.getInt("product_amount"));
            }
        }

        return cell;
    }

    // get all product amount
    public int getTotalAmountOfProduct(int productId, int salepoint_id) throws SQLException {
        int amount = 0;

        ArrayList<Cell> cells = getAllByCondition("WHERE product_id = " + productId + " AND storage_id = " + salepoint_id);
        for (Cell cell : cells) {
            amount += cell.productAmount;
        }

        return amount;
    }

}
