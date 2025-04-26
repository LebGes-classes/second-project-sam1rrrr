package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import db.repositories.*;

import models.Order;
import models.Product;
import models.person.Client;
import models.storage.Cell;
import models.storage.SalePoint;

import static services.SalePointService.printSalePoints;

public class OrderService {
    private static Scanner scanner = new Scanner(System.in);

    static SalePointRepository salePointRepository = new SalePointRepository();
    static OrderRepository orderRepository = new OrderRepository();
    static ProductRepository productRepository = new ProductRepository();
    static EmployeeRepository buyerRepository = new EmployeeRepository();
    static CellRepository cellRepository = new CellRepository();
    static ClientRepository clientRepository = new ClientRepository();

    public static void addOrder() throws SQLException {
        Order order = new Order();
        System.out.println("Выберите ID пункта продаж: ");
        if (!printSalePoints()) {
            System.out.println("Нет пунктов продаж. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }

        int salePointId = Integer.parseInt(scanner.nextLine());
        SalePoint salePoint = salePointRepository.getById(salePointId);
        order.setSalePointId(salePointId);

        System.out.println("Выберите ID товара для продажи: ");
        ArrayList<Cell> cells = cellRepository.getAllByCondition("WHERE storage_id = " + salePointId);
        ArrayList<Product> products = new ArrayList<>();

        for (Cell cell : cells) {
            Product product = productRepository.getById(cell.productId);
            if (Objects.equals(product.status, "available")) {
                products.add(product);
            }
        }

        if (products.isEmpty()) {
            System.out.println("Нет товаров, доступных для продажи. Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        for (Product product : products) {
            int productAmount = cellRepository.getTotalAmountOfProduct(product.id, salePointId);
            System.out.println(product + "\t|\tКоличество: " + productAmount);
        }

        int productId = Integer.parseInt(scanner.nextLine());
        Product product = productRepository.getById(productId);
        order.setProductId(productId);

        Cell cell = cellRepository.getByCondition("WHERE storage_id = " + salePointId + " AND product_id = " + productId);
        System.out.println("Доступно: " + cell.productAmount);

        System.out.println();
        System.out.println("Введите количеcтво:");
        int amount = Integer.parseInt(scanner.nextLine());
        while(amount > cell.productAmount || amount <= 0){
            System.out.println("Неправильный ввод, попробуйте еще раз");
            amount = Integer.parseInt(scanner.nextLine());
        }

        order.setAmount(amount);
        cell.setProductAmount(cell.productAmount - amount);

        if(cell.productAmount == 0){
            cellRepository.delete(cell.id);
        }

        cellRepository.update(cell);

        double total_price = product.sellPrice * amount;
        order.setTotalPrice(total_price);

        salePoint.increaseProfit(total_price);

        Client client = new Client();
        System.out.println("Введите имя покупателя");
        String name = scanner.nextLine();
        client.setName(name);

        clientRepository.add(client);
        client.setId(clientRepository.getByCondition("WHERE name = '" + client.name + "'"));

        order.setClientId(client.id);
        order.setDate();
        order.setStatus("received");

        orderRepository.add(order);

        productRepository.update(product);
        salePointRepository.update(salePoint);

        System.out.println("Заказ создан, нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void refundOrder() throws SQLException {
        System.out.println("Выберите ID заказа для возврата");

        ArrayList<Order> orders = orderRepository.getOrdersByCondition("WHERE status = 'received'");
        if (orders.isEmpty()) {
            System.out.println("Нет заказов, подходящих для возврата");
            System.out.println("Нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
        }

        int orderId = Integer.parseInt(scanner.nextLine());
        Order order = orderRepository.getById(orderId);
        Product product = productRepository.getById(order.productId);
        product.setStatus("available");

        SalePoint salePoint = salePointRepository.getById(order.salePointId);
        salePoint.reduceProfit(order.totalPrice);

        Cell newCell = cellRepository.getById((cellRepository.getByCondition("WHERE storage_id = " + salePoint.id + " AND product_id = " + product.id)).id);
        if (newCell != null) {
            newCell.setProductAmount(newCell.productAmount + order.amount);
            cellRepository.update(newCell);
        } else {
            newCell = new Cell();
            newCell.setProductAmount(order.amount);
            newCell.setProductId(product.id);
            cellRepository.addToSalePoint(newCell);
        }

        order.setStatus("refunded");
        orderRepository.update(order);

        productRepository.update(product);
        salePointRepository.update(salePoint);

        System.out.println("Товар возвращен, нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }

    public static void printOrdersByClient() throws SQLException {
        System.out.println("Выберите ID покупателя");
        ArrayList<Client> clients = clientRepository.getAll();
        if (clients.isEmpty()) {
            System.out.println("Нет доступных покупателей, нажмите Enter, чтобы продолжить");
            scanner.nextLine();
            return;
        } else {
            for (Client client : clients) {
                System.out.println(client);
            }
        }

        int clientId = Integer.parseInt(scanner.nextLine());

        ArrayList<Order> orders = orderRepository.getOrdersByClient(clientId);
        if (orders.isEmpty()) {
            System.out.println("Подходящих заказов нет");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
        System.out.println("Нажмите Enter, чтобы продолжить");
        scanner.nextLine();
    }
}
