package db.repositories;

import models.person.Employee;
import db.DataBase;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeRepository {
    // add employee to database
    public void add(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employees (name, position, salary, work_place_id, is_active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, employee.name);
            pstmt.setString(2, employee.position);
            pstmt.setInt(3, employee.salary);
            pstmt.setInt(4, employee.workPlaceId);
            pstmt.setBoolean(5, employee.isActive);
            pstmt.executeUpdate();
        }
    }

    // update employee
    public void update(Employee employee) throws SQLException {
        String sql = "UPDATE Employees SET name = ?, position = ?, salary = ?, work_place_id = ?, is_active = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, employee.name);
            pstmt.setString(2, employee.position);
            pstmt.setInt(3, employee.salary);
            pstmt.setInt(4, employee.workPlaceId);
            pstmt.setBoolean(5, employee.isActive);
            pstmt.setInt(6, employee.id);
            pstmt.executeUpdate();
        }
    }

    // delete employee
    public void delete(Employee employee) throws SQLException {
        String sql = "DELETE FROM Employees WHERE ID = " + employee.id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // delete employee by id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Employees WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // get all employees
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Employee employee = new Employee();
                employee.setId(set.getInt("id"));
                employee.setName(set.getString("name"));
                employee.setPosition(set.getString("position"));
                employee.setSalary(set.getInt("salary"));
                employee.setWorkPlaceId(set.getInt("work_place_id"));
                employee.setIsActive(set.getBoolean("is_active"));
                employees.add(employee);
            }
        }

        return employees;
    }

    // get all employees
    public ArrayList<Employee> getAllByCondition(String condition) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Employee employee = new Employee();
                employee.setId(set.getInt("id"));
                employee.setName(set.getString("name"));
                employee.setPosition(set.getString("position"));
                employee.setSalary(set.getInt("salary"));
                employee.setWorkPlaceId(set.getInt("work_place_id"));
                employee.setIsActive(set.getBoolean("is_active"));
                employees.add(employee);
            }
        }

        return employees;
    }

    // get employee by id
    public Employee getById(int id) throws SQLException {
        Employee employee = null;
        String sql = "SELECT * FROM Employees WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                employee = new Employee();
                employee.setId(set.getInt("id"));
                employee.setName(set.getString("name"));
                employee.setPosition(set.getString("position"));
                employee.setSalary(set.getInt("salary"));
                employee.setWorkPlaceId(set.getInt("work_place_id"));
                employee.setIsActive(set.getBoolean("is_active"));
            }
        }

        return employee;
    }
}
