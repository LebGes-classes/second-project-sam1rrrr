package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import db.repositories.*;
import models.Product;
import models.person.Employee;
import models.storage.Cell;
import models.storage.SalePoint;
import models.storage.Storage;

import static services.EmployeeService.printEmployees;

public class SalePointService {
    private static Scanner scanner = new Scanner(System.in);

    static SalePointRepository salePointRepository = new SalePointRepository();
    static ProductRepository productRepository = new ProductRepository();
    static EmployeeRepository employeeRepository = new EmployeeRepository();
    static CellRepository cellRepository = new CellRepository();

    public static void addSalePoint() throws SQLException {
        SalePoint salePoint = new SalePoint();

        System.out.println("Введите адрес нового пункта продаж:");
        String location = scanner.nextLine();
        salePoint.setLocation(location);
        salePoint.setAdminId(-1);
        salePoint.setProfit(0);

        salePointRepository.add(salePoint);

        System.out.println("Пункт продаж открыт");
    }

    public static void closeSalePoint() throws SQLException {
        System.out.println("Введите ID пункта продаж для закрытия");
        if (!printSalePoints()) {
            System.out.println("Нет пунктов продаж, доступных для удаления. Нажмите Enter, чтобы вернуться назад");
            scanner.nextLine();
            return;
        }

        int closedSalePointId = Integer.parseInt(scanner.nextLine());
        salePointRepository.delete(closedSalePointId);

        System.out.println("Склад закрыт. Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void setAdminToSalePoint() throws SQLException {
        System.out.println("Введите ID ответственного сотрудника: ");
        if (!printEmployees()) {
            System.out.println("Нет доступных сотрудников. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int adminId = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите ID пункта продаж для назначения ответственного сотрудника");
        if (!printSalePoints()) {
            System.out.println("Нет доступных складов. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int salePointId = Integer.parseInt(scanner.nextLine());

        SalePoint salePoint = salePointRepository.getById(salePointId);
        salePoint.setAdminId(adminId);
        salePointRepository.update(salePoint);

        Employee admin = employeeRepository.getById(adminId);
        admin.setWorkPlaceId(salePointId);
        admin.setPosition("Админ пункта продаж");
        employeeRepository.update(admin);

        System.out.println("Сотрудник назначен ответственным за пункт продаж");
    }

    public static void getAllSalePointsProfit() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointRepository.getAll();
        double profit = 0;
        for (SalePoint salePoint : salePoints) {
            profit += salePoint.profit;
        }

        System.out.println("Общая доходность: " + profit);
        System.out.println("Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static boolean printSalePoints() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointRepository.getAll();
        if (salePoints.isEmpty()) {
            return false;
        }
        for (SalePoint salepoint : salePoints) {
            System.out.println(salepoint);
        }
        return true;
    }

    public static void printSalePointsProducts() throws SQLException {
        System.out.println("Введите ID склада");
        if (!printSalePoints()) {
            System.out.println("Нет пунктов продаж, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        int salePointId = Integer.parseInt(scanner.nextLine());
        SalePoint salePoint = salePointRepository.getById(salePointId);

        ArrayList<Cell> cells = cellRepository.getAllByCondition("WHERE storage_id = " + salePointId);
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
