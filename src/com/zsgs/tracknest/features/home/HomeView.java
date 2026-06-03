package com.zsgs.tracknest.features.home;

import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.features.order.OrderView;
import com.zsgs.tracknest.features.product.ProductListView;
import com.zsgs.tracknest.features.product.add.ProductAddView;
import com.zsgs.tracknest.features.product.update.ProductUpdateView;
import com.zsgs.tracknest.features.report.ReportView;
import com.zsgs.tracknest.features.stock.StockView;
import com.zsgs.tracknest.features.supplier.SupplierView;
import com.zsgs.tracknest.features.user.UserListView;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;

import java.util.Scanner;

public class HomeView {

    private final HomeModel homeModel;
    private final User user;
    private final Scanner scanner;

    public HomeView(User user) {
        this.homeModel = new HomeModel(this);
        this.user = user;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        homeModel.init(user);
    }

    void showUnauthorized() {
        ConsoleView.error("Your account role is not set. Contact your administrator.");
    }

    void showAdminMenu() {
        while (true) {
            ConsoleView.title("Admin Home");
            ConsoleView.option("1", "Add supplier");
            ConsoleView.option("2", "View suppliers");
            ConsoleView.option("3", "Add new product");
            ConsoleView.option("4", "View products");
            ConsoleView.option("5", "Update product details");
            ConsoleView.option("6", "Stock management");
            ConsoleView.option("7", "Supplier transactions");
            ConsoleView.option("8", "Order management");
            ConsoleView.option("9", "Reports");
            ConsoleView.option("10", "Sign out");
            ConsoleView.prompt("Choose an option");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new SupplierView().addSupplier();
                    break;
                case "2":
                    new SupplierView().showSuppliers();
                    break;
                case "3":
                    new ProductAddView().init();
                    break;
                case "4":
                    new ProductListView().init();
                    break;
                case "5":
                    new ProductUpdateView().init();
                    break;
                case "6":
                    new StockView().init();
                    break;
                case "7":
                    new StockView().showSupplierTransactions();
                    break;
                case "8":
                    new OrderView(user).init();
                    break;
                case "9":
                    new ReportView().init();
                    break;
                case "10":
                    ConsoleView.success("You have been signed out.");
                    return;
                default:
                    ConsoleView.error("Invalid option. Please try again.");
            }
        }
    }

    void showUserMenu() {
        while (true) {
            ConsoleView.title("User Home");
            ConsoleView.option("1", "View/search products");
            ConsoleView.option("2", "Create order");
            ConsoleView.option("3", "View order history");
            ConsoleView.option("4", "Sign out");
            ConsoleView.prompt("Choose an option");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new ProductListView().init();
                    break;
                case "2":
                    new OrderView(user).createOrder();
                    break;
                case "3":
                    new OrderView(user).showOrders();
                    break;
                case "4":
                    ConsoleView.success("You have been signed out.");
                    return;
                default:
                    ConsoleView.error("Invalid option. Please try again.");
            }
        }
    }
}
