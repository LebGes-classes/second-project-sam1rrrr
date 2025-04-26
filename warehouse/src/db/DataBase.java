package db;

import java.sql.*;

public class DataBase {
    private static final String DB_PATH = "jdbc:sqlite:warehouse/resources/database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_PATH);
    }

    public static void createTables() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_PATH);
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS Employees (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "position TEXT, " +
                "salary INTEGER, " +
                "work_place_id INTEGER, " +
                "is_active INTEGER NOT NULL)");

        statement.execute("CREATE TABLE IF NOT EXISTS Clients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)");

        statement.execute("CREATE TABLE IF NOT EXISTS Cells (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "storage_id INTEGER, " +
                "product_id INTEGER, " +
                "product_amount INTEGER, " +
                "FOREIGN KEY (storage_id) REFERENCES Storages(id), " +
                "FOREIGN KEY (product_id) REFERENCES Products(id))");

        statement.execute("CREATE TABLE IF NOT EXISTS Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "status TEXT, " +
                "client_id INTEGER, " +
                "product_id INTEGER, " +
                "amount INTEGER, " +
                "total_price INTEGER, " +
                "date INTEGER, " +
                "employee_id INTEGER, " +
                "sale_point_id INTEGER, " +
                "FOREIGN KEY (client_id) REFERENCES Clients(id), " +
                "FOREIGN KEY (employee_id) REFERENCES Employees(id), " +
                "FOREIGN KEY (product_id) REFERENCES Products(id), " +
                "FOREIGN KEY (sale_point_id) REFERENCES SalePoints(id))");

        statement.execute("CREATE TABLE IF NOT EXISTS Products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "sell_price REAL, " +
                "status TEXT)");

        statement.execute("CREATE TABLE IF NOT EXISTS SalePoints (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "admin_id INTEGER, " +
                "profit REAL, " +
                "location TEXT, " +
                "FOREIGN KEY (admin_id) REFERENCES Employees(id))");

        statement.execute("CREATE TABLE IF NOT EXISTS Storages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "admin_id INTEGER, " +
                "location TEXT, " +
                "FOREIGN KEY(admin_id) REFERENCES Employees(ID))");
    }

    public static void main(String[] args) throws SQLException {
        System.setProperty("org.sqlite.lib.exported", "true");
        createTables();
    }
}
