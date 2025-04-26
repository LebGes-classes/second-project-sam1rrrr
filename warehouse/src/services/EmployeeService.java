package services;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

import db.repositories.*;
import models.person.Employee;

import models.storage.SalePoint;
import models.storage.Storage;
import services.*;

import static services.SalePointService.printSalePoints;
import static services.StorageService.printStorages;


public class EmployeeService {
    private static Scanner scanner = new Scanner(System.in);

    static EmployeeRepository employeeRepository = new EmployeeRepository();
    static StorageRepository storageRepository = new StorageRepository();
    static SalePointRepository salePointRepository = new SalePointRepository();

    public static void hireEmployee() throws SQLException {
        Employee employee = new Employee();

        System.out.println("Введите имя сотрудника: ");
        String name = scanner.nextLine();
        employee.setName(name);

        System.out.println("Введите должность сотрудника: ");
        String position = scanner.nextLine();
        employee.setPosition(position);

        System.out.println("Введите зарплату сотрудника: ");
        int salary = scanner.nextInt();
        employee.setSalary(salary);

        while (true) {
            scanner.nextLine();
            System.out.println("Куда устроить сотрудника? ");
            System.out.println();
            System.out.println("1) Склад");
            System.out.println("2) Пункт продаж");
            System.out.println("0) Выход");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Выберите ID склада для устройства сотрудника: ");
                    if (!printStorages()) {
                        System.out.println("Нет доступных складов. Нажмите Enter, чтобы вернуться назад:");
                        scanner.nextLine();
                        return;
                    }

                    int storageId = Integer.parseInt(scanner.nextLine());
                    employee.setWorkPlaceId(storageId);

                    employee.setIsActive(true);
                    employeeRepository.add(employee);

                    System.out.println("Сотрудник добавлен. Нажмите Enter, чтобы продолжить:");
                    scanner.nextLine();
                    return;

                case "2":
                    System.out.println("Выберите ID пункта продаж для устройства сотрудника: ");
                    if (!printSalePoints()) {
                        System.out.println("Нет доступных пунктов продаж. Нажмите Enter, чтобы вернуться назад:");
                        scanner.nextLine();
                        return;
                    }

                    int salePointId = Integer.parseInt(scanner.nextLine());
                    employee.setWorkPlaceId(salePointId);

                    employee.setIsActive(true);
                    employeeRepository.add(employee);

                    System.out.println("Сотрудник добавлен. Нажмите Enter, чтобы продолжить:");
                    scanner.nextLine();
                    return;

                case "0":
                    return;

                default:
                    System.out.println("Неправильный выбор, попробуйте заново");
                    System.out.println();
                    break;
            }
        }

    }


    public static void fireEmployee() throws SQLException {
        System.out.println("Выберите ID сотрудника, которого хотите уволить: ");

        if (!printEmployees()) {
            System.out.println("Нет сотрудников, доступных для удаления. Нажмите Enter, чтобы вернуться назад:");
            scanner.nextLine();
            return;
        }

        int firedEmployeeId = Integer.parseInt(scanner.nextLine());
        Employee firedEmployee = employeeRepository.getById(firedEmployeeId);

        firedEmployee.setIsActive(false);
        firedEmployee.setPosition("fired");
        firedEmployee.setSalary(0);
        firedEmployee.setWorkPlaceId(-1);

        SalePoint salePoint = salePointRepository.getByCondition("WHERE admin_id = " + firedEmployeeId);
        if (salePoint != null) {
            salePoint.setAdminId(-1);
            salePointRepository.update(salePoint);
        }

        Storage storage = storageRepository.getByCondition("WHERE admin_id = " + firedEmployeeId);
        if (storage != null) {
            storage.setAdminId(-1);
            storageRepository.update(storage);
        }

        employeeRepository.update(firedEmployee);

        System.out.println("Сотрудник уволен");
    }

    public static boolean printEmployees() throws SQLException {
        ArrayList<Employee> employees = employeeRepository.getAllByCondition("WHERE is_active = true");
        if (employees.isEmpty()) {
            return false;
        }
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        return true;
    }

    public static void main(String[] args) throws SQLException {
        fireEmployee();
    }
}
