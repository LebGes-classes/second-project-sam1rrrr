package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import db.repositories.*;
import models.Product;
import models.person.Employee;
import models.storage.Cell;
import models.storage.Storage;

import static services.EmployeeService.printEmployees;

public class StorageService {
    private static Scanner scanner = new Scanner(System.in);

    static StorageRepository storageRepository = new StorageRepository();
    static ProductRepository productRepository = new ProductRepository();
    static EmployeeRepository employeeRepository = new EmployeeRepository();
    static CellRepository cellRepository = new CellRepository();

    public static void addStorage() throws SQLException {
        Storage storage = new Storage();

        System.out.println("Введите адрес нового склада:");
        String location = scanner.nextLine();
        storage.setLocation(location);
        storage.setAdminId(-1);

        storageRepository.add(storage);

        System.out.println("Склад открыт");
    }

    public static void closeStorage() throws SQLException {
        System.out.println("Введите ID склада для закрытия");
        if (!printStorages()) {
            System.out.println("Нет складов, доступных для удаления. Нажмите Enter, чтобы вернуться назад");
            scanner.nextLine();
            return;
        }

        int closedStorageId = Integer.parseInt(scanner.nextLine());
        storageRepository.delete(closedStorageId);

        System.out.println("Склад закрыт. Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void setAdminToStorage() throws SQLException {
        System.out.println("Введите ID ответственного сотрудника: ");
        if (!printEmployees()) {
            System.out.println("Нет доступных сотрудников. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int adminId = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите ID склада для назначения ответственного сотрудника");
        if (!printStorages()) {
            System.out.println("Нет доступных складов. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int storageId = Integer.parseInt(scanner.nextLine());

        Storage storage = storageRepository.getById(storageId);
        storage.setAdminId(adminId);
        storageRepository.update(storage);

        Employee admin = employeeRepository.getById(adminId);
        admin.setWorkPlaceId(storageId);
        admin.setPosition("Админ склада");
        employeeRepository.update(admin);

        System.out.println("Сотрудник назначен ответственным за склад");
    }

    public static boolean printStorages() throws SQLException {
        ArrayList<Storage> storages = storageRepository.getAll();
        if (storages.isEmpty()) {
            return false;
        }
        for (Storage storage : storages) {
            System.out.println(storage);
        }
        return true;
    }

    public static void printStoragesProducts() throws SQLException {
        System.out.println("Введите ID склада");
        if (!printStorages()) {
            System.out.println("Нет складов, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        int storageId = Integer.parseInt(scanner.nextLine());
        Storage storage = storageRepository.getById(storageId);

        ArrayList<Cell> cells = cellRepository.getAllByCondition("WHERE storage_id = " + storageId);
        ArrayList<Product> products = new ArrayList<Product>();
        for (Cell cell : cells) {
            Product product = productRepository.getById(cell.productId);
            products.add(product);
        }

        if (products.isEmpty()) {
            System.out.println("Товаров нет, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

}
