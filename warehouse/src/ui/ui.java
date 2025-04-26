package ui;

import java.sql.SQLException;
import java.util.Scanner;

import static services.EmployeeService.*;
import static services.OrderService.*;
import static services.ProductService.*;
import static services.SalePointService.*;
import static services.StorageService.*;

public class ui {
    private Scanner scanner;

    public ui () {
        this.scanner = new Scanner(System.in);
    }

    public void start() throws SQLException {
        while (true) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: manageProducts(); break;
                case 2: manageEmployees(); break;
                case 3: manageWarehouses(); break;
                case 4: manageSalesPoints(); break;
                case 5: manageOrders(); break;
                case 6: getAllSalePointsProfit(); break;
                case 0: return;
                default:
                    System.out.println("Неправильный выбор, попробуйте еще раз");
            }
        }
    }

    public void printMainMenu() {
        clearConsole();
        System.out.println("== МЕНЮ ==");
        System.out.println();
        System.out.println("1. Товары");
        System.out.println("2. Сотрудники");
        System.out.println("3. Склады");
        System.out.println("4. Пункты продаж");
        System.out.println("5. Заказы");
        System.out.println("6. Общая доходность");
        System.out.println("0. Выход");
    }

    public void manageProducts() throws SQLException {
        while (true) {
            clearConsole();
            System.out.println("== ТОВАРЫ ==");
            System.out.println();
            System.out.println("1. Добавить товар");
            System.out.println("2. Посмотреть доступные для покупки товары");
            System.out.println("3. Закупка товаров");
            System.out.println("4. Переместить товары");
            //System.out.println("5. Товары, доступные к закупке");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addProduct(); break;
                case 2: printAvailableProducts(); break;
                case 3: buyProduct(); break;
                case 4: moveProducts(); break;
                case 0:
                    return;
                default:
                    System.out.println("Неправильный выбор, попробуйте еще раз");
            }
        }
    }

    public void manageEmployees() throws SQLException {
        while (true) {
            clearConsole();
            System.out.println("== СОТРУДНИКИ ==");
            System.out.println();
            System.out.println("1. Нанять сотрудника");
            System.out.println("2. Уволить сотрудника");
            System.out.println("3. Посмотреть всех сотрудников");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: hireEmployee(); break;
                case 2: fireEmployee(); break;
                case 3:
                    if (!printEmployees()) {
                        System.out.println("Нет сотрудников");
                    }
                    scanner.nextLine();
                    System.out.println();
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scanner.nextLine();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неправильный выбор, попробуйте еще раз");
            }
        }
    }

    public void manageWarehouses() throws SQLException {
        while (true) {
            clearConsole();
            System.out.println("== СКЛАДЫ ==");
            System.out.println();
            System.out.println("1. Открыть новый склад");
            System.out.println("2. Закрыть склад");
            System.out.println("3. Получить информацию о складах");
            System.out.println("4. Назначить ответственного за склад");
            System.out.println("5. Информация о товарах на склале");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addStorage(); break;
                case 2: closeStorage(); break;
                case 3:
                    if (!printStorages()) {
                        System.out.println("Нет складов");
                    }
                    System.out.println();
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scanner.nextLine();
                    break;
                case 4:
                    setAdminToStorage(); break;
                case 5: printStoragesProducts(); break;
                case 0:
                    return;
                default:
                    System.out.println("Неправильный выбор, попробуйте еще раз");
            }
        }
    }


    public void manageSalesPoints() throws SQLException {
        while (true) {
            clearConsole();
            System.out.println("== ПУНКТЫ ПРОДАЖ ==");
            System.out.println();
            System.out.println("1. Открыть новый пункт продаж");
            System.out.println("2. Закрыть пункт продаж");
            System.out.println("3. Получить информацию о пунктах продаж");
            System.out.println("4. Назначить ответственного за пункт продаж");
            System.out.println("5. Информация о товарах в пункте продаж");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addSalePoint(); break;
                case 2: closeSalePoint(); break;
                case 3:
                    if (!printSalePoints()) {
                        System.out.println("Нет пунктов продаж");
                    }
                    System.out.println();
                    System.out.println("Нажмите Enter, чтобы продолжить");
                    scanner.nextLine();
                    break;

                case 4: setAdminToSalePoint(); break;
                case 5: printSalePointsProducts(); break;
                case 0:
                    return;
                default:
                    System.out.println("Неправильный выбор, попробуйте еще раз");
            }
        }
    }

    public void manageOrders() throws SQLException {
        while (true) {
            clearConsole();
            System.out.println("== ЗАКАЗЫ ==");
            System.out.println();
            System.out.println("1. Создать новый заказ");
            System.out.println("2. Оформить возврат");
            System.out.println("3. Посмотреть заказы покупателя");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addOrder(); break;
                case 2: refundOrder(); break;
                case 3: printOrdersByClient(); break;
                case 0:
                    return;
                default:
                    System.out.println("Неправильный выбор, попробуйте еще раз");
            }
        }
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
