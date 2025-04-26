package db.repositories;

import models.person.Client;
import db.DataBase;
import java.sql.*;
import java.util.ArrayList;

public class ClientRepository {

    // add client to database
    public void add(Client client) throws SQLException {
        String sql = "INSERT INTO Clients (name) VALUES (?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, client.name);
            pstmt.executeUpdate();
        }
    }

    // get all clients
    public ArrayList<Client> getAll() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clients";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Client client = new Client();
                client.setId(set.getInt("id"));
                client.setName(set.getString("name"));
                clients.add(client);
            }
        }

        return clients;
    }

    // get client by id
    public Client getById(int id) throws SQLException {
        Client client = null;
        String sql = "SELECT * FROM Clients WHERE id = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                client = new Client();
                client.setId(set.getInt("id"));
                client.setName(set.getString("name"));
            }
        }

        return client;
    }

    // get client id by condition
    public int getByCondition(String condition) throws SQLException {
        Client client = null;
        String sql = "SELECT * FROM Clients " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                client = new Client();
                client.setId(set.getInt("id"));
                client.setName(set.getString("name"));
            }
        }

        return client.id;
    }

}
