package db.repositories;

import models.storage.Storage;

import java.util.ArrayList;
import java.sql.*;

import db.DataBase;
import models.storage.Storage;

public class StorageRepository {
    // add storage
    public void add(Storage storage) throws SQLException {
        String sql = "INSERT INTO Storages (location, admin_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, storage.location);
            pstmt.setInt(2, storage.adminId);
            pstmt.executeUpdate();
        }
    }

    // update storage info
    public void update(Storage storage) throws SQLException {
        String sql = "UPDATE Storages SET admin_id= ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, storage.adminId);
            pstmt.setInt(2, storage.id);
            pstmt.executeUpdate();
        }
    }

    // delete storage
    public void delete(Storage storage) throws SQLException {
        String sql = "DELETE FROM Storages WHERE ID = " + storage.id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // delete storage by id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Storages WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // get all storages
    public ArrayList<Storage> getAll() throws SQLException {
        ArrayList<Storage> storages = new ArrayList<>();
        String sql = "SELECT * FROM Storages";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Storage storage = new Storage();
                storage.setId(set.getInt("id"));
                storage.setAdminId(set.getInt("admin_id"));
                storage.setLocation(set.getString("location"));
                storages.add(storage);
            }
        }

        return storages;
    }

    // get storage by id
    public Storage getById(int id) throws SQLException {
        Storage storage = null;
        String sql = "SELECT * FROM Storages WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                storage = new Storage();
                storage.setId(set.getInt("id"));
                storage.setLocation(set.getString("location"));
                storage.setAdminId(set.getInt("admin_id"));
            }
        }

        return storage;
    }

    // get storage by condition
    public Storage getByCondition(String condition) throws SQLException {
        int id = -1;
        String sql = "SELECT ID FROM Storages " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return getById(id);
    }
}
