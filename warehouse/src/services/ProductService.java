package services;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

import models.*;
import db.repositories.*;
import models.storage.Cell;
import models.storage.SalePoint;
import models.storage.Storage;

import static services.SalePointService.printSalePoints;
import static services.SalePointService.salePointRepository;
import static services.StorageService.printStorages;
import static services.StorageService.storageRepository;

public class ProductService {
    private final static Scanner scanner = new Scanner(System.in);

    static ProductRepository productRepository = new ProductRepository();
    static CellRepository cellRepository = new CellRepository();

    public static void addProduct() throws SQLException {
        Product product = new Product();

        System.out.println("Введите название товара");
        String productName = scanner.nextLine();
        product.setName(productName);

        System.out.println("Введите цену закупки товара");
        double price = Double.parseDouble(scanner.nextLine());
        product.setPrice(price);

        System.out.println("Введите цену продажи товара");
        double sellPrice = Double.parseDouble(scanner.nextLine());
        product.setSellPrice(sellPrice);

        System.out.println("Выберите склад для хранения товара");
        if (!printStorages()) {
            System.out.println("Нет доступных складов, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int storageId = Integer.parseInt(scanner.nextLine());

        Cell cell = new Cell(storageId);

        System.out.println("Введите количество товара");
        int amount = Integer.parseInt(scanner.nextLine());
        cell.setProductAmount(amount);

        product.setStatus("available");

        productRepository.add(product);
        cell.setProductId(productRepository.getLastAddedId());
        cellRepository.addToStorage(cell);

        System.out.println("Товар добавлен! Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void printAvailableProducts() throws SQLException {
        ArrayList<Product> products = productRepository.getAllByCondition("WHERE status = 'available'");
        if(products.isEmpty()){
            System.out.println("Доступные для закупки товары отсутствуют");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }

        System.out.println("Введите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void buyProduct() throws SQLException {
        System.out.println("Выберите ID товара для закупки");
        ArrayList<Product> products = productRepository.getAll();

        if (products.isEmpty()) {
            System.out.println("Нет товаров, доступных для закупки. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

        int productId = Integer.parseInt(scanner.nextLine());
        Product product = productRepository.getById(productId);

        System.out.println("Выберите ID склада на который необходимо доставить товар");
        if (!printStorages()) {
            System.out.println("Нет доступных складов, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int storageId = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите количество товара: ");
        int amount = Integer.parseInt(scanner.nextLine());

        Cell cell = cellRepository.getByCondition("WHERE storage_id = " + storageId + " AND product_id = " + productId);
        if (cell != null){
            cell.setProductAmount(amount + cell.productAmount);
            cellRepository.update(cell);
        } else {
            cell = new Cell(storageId);
            cell.setProductAmount(amount);
            cell.setProductId(productId);
            cellRepository.addToStorage(cell);
        }

        System.out.println("Товар закуплен! Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void moveProducts() throws SQLException {
        int storageFromId = 0;
        int storageToId = 0;
        int salePointFromId = 0;
        int salePointToId = 0;

        System.out.println("Откуда хотите переместить товары?");
        System.out.println("1) Склад");
        System.out.println("2) Пункт выдачи");
        System.out.println();

        int firstChoice = Integer.parseInt(scanner.nextLine());
        if (firstChoice == 1) {
            System.out.println("Введите ID склада");
            if (!printStorages()) {
                System.out.println("Нет доступных складов, нажмите Enter, чтобы продолжить");
                scanner.nextLine();
                return;
            }
            storageFromId = Integer.parseInt(scanner.nextLine());
        } else if (firstChoice == 2) {
            System.out.println("Введите ID пункта выдачи");
            if (!printSalePoints()) {
                System.out.println("Нет доступных пунктов выдачи, нажмите Enter, чтобы продолжить");
                scanner.nextLine();
                return;
            }
            salePointFromId = Integer.parseInt(scanner.nextLine());
        }

        System.out.println("Куда хотите переместить товары?");
        System.out.println("1) Склад");
        System.out.println("2) Пункт выдачи");
        System.out.println();

        int secondChoice = Integer.parseInt(scanner.nextLine());
        if (secondChoice == 1) {
            System.out.println("Введите ID склада");
            if (!printStorages()) {
                System.out.println("Нет доступных складов, нажмите Enter, чтобы продолжить");
                scanner.nextLine();
                return;
            }
            storageToId = Integer.parseInt(scanner.nextLine());
        } else if (secondChoice == 2) {
            System.out.println("Введите ID пункта выдачи");
            if (!printSalePoints()) {
                System.out.println("Нет доступных пунктов выдачи, нажмите Enter, чтобы продолжить");
                scanner.nextLine();
                return;
            }
            salePointToId = Integer.parseInt(scanner.nextLine());
        }

        int moveProductId = 0;

        System.out.println("Выберите ID товара, который нужно переместить:");
        ArrayList<Cell> cells = null;
        if (firstChoice == 1) {
            cells = cellRepository.getAllByCondition("WHERE storage_id = " + storageFromId);
        } else if (firstChoice == 2) {
            cells = cellRepository.getAllByCondition("WHERE storage_id = " + salePointFromId);
        }

        ArrayList<Product> products = new ArrayList<>();
        if (cells != null) {
            for (Cell cell : cells) {
                Product product = productRepository.getById(cell.productId);
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            System.out.println("Нет доступных товаров, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }

        moveProductId = Integer.parseInt(scanner.nextLine());
        Product moveProduct = productRepository.getById(moveProductId);

        Cell oldCell = null;
        if (firstChoice == 1) {
            oldCell = cellRepository.getByCondition("WHERE storage_id = " + storageFromId + " AND product_id = " + moveProductId);
        } else if (firstChoice == 2) {
            oldCell = cellRepository.getByCondition("WHERE storage_id = " + salePointFromId + " AND product_id = " + moveProductId);
        }

        System.out.println("Сколько товара нужно переместить? Доступно: " + oldCell.productAmount + " шт.");
        int amount = Integer.parseInt(scanner.nextLine());
        while (amount > oldCell.productAmount) {
            System.out.println("Неправильное количество, попробуйте еще раз");
            amount = Integer.parseInt(scanner.nextLine());
        }

        oldCell.setProductAmount(oldCell.productAmount - amount);
        if (oldCell.productAmount == 0){
            cellRepository.delete(oldCell.id);
        }

        cellRepository.update(oldCell);

        Cell newCell = null;
        if (secondChoice == 1) {
            newCell = cellRepository.getByCondition("WHERE storage_id = " + storageToId + " AND product_id = " + moveProductId);
        } else if (secondChoice == 2) {
            newCell = cellRepository.getByCondition("WHERE storage_id = " + salePointToId + " AND product_id = " + moveProductId);
        }

        if (newCell != null) {
            newCell.setProductAmount(newCell.productAmount + amount);
            cellRepository.update(newCell);
        } else {
            newCell = new Cell();
            if (secondChoice == 1) {
                newCell.setStorageId(storageToId);
            } else if (secondChoice == 2) {
                newCell.setStorageId(salePointToId);
            }
            newCell.setProductAmount(amount);
            newCell.setProductId(moveProduct.id);
            if (secondChoice == 1) {
                cellRepository.addToStorage(newCell);
            } else if (secondChoice == 2) {
                cellRepository.addToSalePoint(newCell);
            }
        }

        System.out.println("Товары перемещены! Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

}

