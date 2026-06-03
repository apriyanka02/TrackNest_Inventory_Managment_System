package com.zsgs.tracknest.features.order;

import com.zsgs.tracknest.data.dto.Order;
import com.zsgs.tracknest.data.dto.OrderedItem;
import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;

import java.util.List;
import java.util.Scanner;

public class OrderView {

    private final OrderModel orderModel;
    private final User user;
    private final Scanner scanner;

    public OrderView(User user) {
        this.orderModel = new OrderModel();
        this.user = user;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        if (user != null && user.getRole() == User.Role.ADMIN) {
            showOrders();
            return;
        }

        ConsoleView.title("Orders");
        ConsoleView.option("1", "Create order");
        ConsoleView.option("2", "View orders");
        ConsoleView.prompt("Choose an option");
        if ("1".equals(scanner.nextLine().trim())) {
            createOrder();
        } else {
            showOrders();
        }
    }

    public void createOrder() {
        while (true) {
            showProductListing();
            ConsoleView.prompt("Enter product name");
            String productName = scanner.nextLine().trim();
            ConsoleView.prompt("Enter quantity");
            int quantity = readInt();
            Order order = orderModel.createOrder(user == null ? null : user.getUserId(), productName, quantity);
            if (order == null) {
                ConsoleView.error("Could not create order. Check product name and stock quantity.");
            } else {
                ConsoleView.success("Order created with id: " + IdFormatter.orderId(order.getOrderId()));
            }

            ConsoleView.prompt("Do you need to order anything else? (y/n)");
            String next = scanner.nextLine().trim().toLowerCase();
            if (!"y".equals(next) && !"yes".equals(next)) {
                break;
            }
        }
    }

    public void showOrders() {
        boolean allOrders = user != null && user.getRole() == User.Role.ADMIN;
        List<Order> orders = orderModel.getOrders(user == null ? null : user.getUserId(), allOrders);
        printLine(allOrders ? "All Orders" : "Your Orders");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            String header = "+----------------------+--------------------------+----------+----------+--------------+------------+";
            System.out.println(header);
            System.out.printf("| %-20s | %-24s | %-8s | %-10s | %-12s | %-10s |%n",
                    "User", "Product", "Quantity", "Price", "Item Total", "Status");
            System.out.println(header);
            long grandTotal = 0L;
            for (Order order : orders) {
                User buyer = orderModel.getUser(order.getUserId());
                String buyerName = buyer == null ? "Unknown user" : buyer.getUserName();
                List<OrderedItem> items = orderModel.getOrderItems(order.getOrderId());
                long orderTotal = 0L;
                if (items.isEmpty()) {
                    System.out.printf("| %-20s | %-24s | %-8s | %-10s | %-12s | %-10s |%n",
                            buyerName,
                            "No items",
                            "0",
                            "0",
                            "0",
                            order.getStatus());
                } else {
                    for (OrderedItem item : items) {
                        Product product = orderModel.getProductById(item.getProductId());
                        long price = product == null || product.getPrice() == null ? 0L : product.getPrice();
                        long itemTotal = price * item.getQuantity();
                        orderTotal += itemTotal;
                        System.out.printf("| %-20s | %-24s | %-8s | %-10s | %-12s | %-10s |%n",
                                buyerName,
                                product == null ? "Unknown product" : product.getProductName(),
                                item.getQuantity(),
                                price,
                                itemTotal,
                                order.getStatus());
                    }
                }
                grandTotal += orderTotal;
                System.out.println(header);
                System.out.printf("| %-20s | %-24s | %-8s | %-10s | %-12s | %-10s |%n",
                        "", "Order Total", "", "", orderTotal, order.getStatus());
                System.out.println(header);
            }
            System.out.printf("| %-20s | %-24s | %-8s | %-10s | %-12s | %-10s |%n",
                    "", "Grand Total", "", "", grandTotal, "");
            System.out.println(header);
        }
        printLine("End of Orders");
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String formatBuyer(User buyer, Long userId) {
        if (buyer == null) return "Unknown user " + IdFormatter.userId(userId);
        return IdFormatter.userId(buyer.getUserId()) + " " + buyer.getUserName() + " (" + buyer.getEmail() + ")";
    }

    private void showProductListing() {
        ConsoleView.title("Available Products");
        List<Product> products = orderModel.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.printf("%-12s | %-24s | %-10s | %-10s%n", "Product ID", "Name", "Price", "Stock");
        for (Product product : products) {
            System.out.printf("%-12s | %-24s | %-10s | %-10s%n",
                    product.getProductId() == null ? "N/A" : product.getProductId(),
                    product.getProductName(),
                    product.getPrice() == null ? "0" : product.getPrice(),
                    product.getQuantity() == null ? "0" : product.getQuantity());
        }
    }

    private void printLine(String title) {
        ConsoleView.title(title);
    }
}
