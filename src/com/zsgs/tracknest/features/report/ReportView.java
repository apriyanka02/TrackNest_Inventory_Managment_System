package com.zsgs.tracknest.features.report;

import com.zsgs.tracknest.data.dto.Order;
import com.zsgs.tracknest.data.dto.OrderedItem;
import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;

import java.util.List;
import java.util.Scanner;

public class ReportView {

    private final ReportModel reportModel;
    private final Scanner scanner;

    public ReportView() {
        this.reportModel = new ReportModel();
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        while (true) {
            ConsoleView.title("Inventory Reports");
            ConsoleView.option("1", "Summary");
            ConsoleView.option("2", "Low stock details");
            ConsoleView.option("3", "Most purchased product with supplier");
            ConsoleView.option("4", "Order report");
            ConsoleView.option("5", "Back");
            ConsoleView.prompt("Choose an option");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showSummary();
                case "2" -> showLowStockDetails();
                case "3" -> showMostPurchasedProduct();
                case "4" -> showOrderReport();
                case "5" -> {
                    return;
                }
                default -> ConsoleView.error("Invalid choice. Please select 1-5.");
            }
        }
    }

    private void showSummary() {
        ConsoleView.title("Summary Report");
        String border = "+----------------------+----------------+";
        System.out.println(border);
        System.out.printf("| %-20s | %14d |%n", "Total Products", reportModel.getProductCount());
        System.out.printf("| %-20s | %14d |%n", "Total Suppliers", reportModel.getSupplierCount());
        System.out.printf("| %-20s | %14d |%n", "Total Orders", reportModel.getOrderCount());
        System.out.println(border);
    }

    private void showLowStockDetails() {
        ConsoleView.title("Low Stock Products");
        List<Product> lowStockProducts = reportModel.getLowStockProducts();
        if (lowStockProducts.isEmpty()) {
            System.out.println("No low stock products found.");
            return;
        }

        System.out.printf("%-12s | %-24s | %-10s | %-14s%n",
                "Product", "Name", "Quantity", "Status");
        for (Product product : lowStockProducts) {
            System.out.printf("%-12s | %-24s | %-10s | %-14s%n",
                    product.getProductId() == null ? "N/A" : String.valueOf(product.getProductId()),
                    product.getProductName(),
                    product.getQuantity() == null ? "0" : product.getQuantity(),
                    product.getStatus() == null ? "UNKNOWN" : product.getStatus().name());
        }
    }

    private void showMostPurchasedProduct() {
        ConsoleView.title("Most Purchased Product");
        ReportModel.TopProductInfo topProduct = reportModel.getTopPurchasedProductInfo();
        if (topProduct == null || topProduct.product == null) {
            System.out.println("No purchase data available.");
            return;
        }

        String border = "+---------------------------+----------------------+";
        System.out.println(border);
        System.out.printf("| %-25s | %-20s |%n", "Product", topProduct.product.getProductName());
        System.out.printf("| %-25s | %-20s |%n", "Product ID", topProduct.product.getProductId() == null ? "N/A" : topProduct.product.getProductId());
        System.out.printf("| %-25s | %-20d |%n", "Total Purchased", topProduct.totalQuantity);
        System.out.printf("| %-25s | %-20s |%n", "Supplier", topProduct.supplier == null ? "Unknown supplier" : topProduct.supplier.getSupplierName());
        System.out.println(border);
    }

    private void showOrderReport() {
        ConsoleView.title("Order Report");
        List<Order> orders = reportModel.getOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders to display.");
            return;
        }

        String header = "+----------------------+--------------------------+----------+----------+--------------+------------+";
        System.out.println(header);
        System.out.printf("| %-20s | %-24s | %-8s | %-8s | %-12s | %-10s |%n",
                "User", "Product", "Quantity", "Price", "Total", "Status");
        System.out.println(header);
        for (Order order : orders) {
            User buyer = reportModel.getUser(order.getUserId());
            String buyerName = buyer == null ? "Unknown user" : buyer.getUserName();
            List<OrderedItem> items = reportModel.getOrderItems(order.getOrderId());
            if (items.isEmpty()) {
                System.out.printf("| %-20s | %-24s | %-8s | %-8s | %-12s | %-10s |%n",
                        buyerName,
                        "No items",
                        "0",
                        "0",
                        order.getTotalPrice() == null ? "0" : order.getTotalPrice(),
                        order.getStatus());
            } else {
                for (OrderedItem item : items) {
                    String productName = "Unknown product";
                    Product product = reportModel.getProductById(item.getProductId());
                    if (product != null) {
                        productName = product.getProductName();
                    }
                    System.out.printf("| %-20s | %-24s | %-8s | %-8s | %-12s | %-10s |%n",
                            buyerName,
                            productName,
                            item.getQuantity(),
                            product == null || product.getPrice() == null ? "0" : product.getPrice(),
                            order.getTotalPrice() == null ? "0" : order.getTotalPrice(),
                            order.getStatus());
                }
            }
        }
        System.out.println(header);
    }
}
