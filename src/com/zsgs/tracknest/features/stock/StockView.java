package com.zsgs.tracknest.features.stock;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.dto.StockTransaction;
import com.zsgs.tracknest.data.repository.TrackNestDB;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StockView {

    private final StockModel stockModel;
    private final Scanner scanner;

    public StockView() {
        this.stockModel = new StockModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        StockTransaction transaction = new StockTransaction();
        System.out.print("Enter product name: ");
        Long productId = readProductIdByName();
        if (productId == null) {
            ConsoleView.error("Product not found.");
            return;
        }
        transaction.setProductId(productId);
        System.out.print("Enter supplier id (optional, press Enter for none): ");
        transaction.setSupplierId(readOptionalId());
        System.out.print("Enter quantity: ");
        transaction.setQuantity(readLong());
        System.out.print("Enter price: ");
        transaction.setPrice(readLong());
        System.out.println("1. Stock In");
        System.out.println("2. Stock Out");
        System.out.print("Choose transaction type: ");
        transaction.setTransactionType("2".equals(scanner.nextLine().trim())
                ? StockTransaction.TransactionType.STOCK_OUT
                : StockTransaction.TransactionType.STOCK_IN);
        stockModel.recordTransaction(transaction);
    }

    private Long readLong() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long readId() {
        return IdFormatter.parseIdNumber(scanner.nextLine());
    }

    private Long readOptionalId() {
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) return null;
        return IdFormatter.parseIdNumber(value);
    }

    void onStockTransactionRecorded(StockTransaction transaction) {
        System.out.println("Stock transaction recorded with id: " + transaction.getTransactionId());
    }

    void onStockTransactionFailed(String message) {
        System.out.println(message);
    }

    public void showSupplierTransactions() {
        ConsoleView.title("Supplier Transactions");
        // Print list of suppliers and their products first
        List<com.zsgs.tracknest.data.dto.Supplier> suppliers = TrackNestDB.getInstance().getSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found.");
        } else {
            ConsoleView.title("Suppliers and their products");
            for (com.zsgs.tracknest.data.dto.Supplier supplier : suppliers) {
                System.out.println(IdFormatter.supplierId(supplier.getSupplierId()) + " | " + supplier.getSupplierName());
                printSupplierProducts(supplier.getSupplierId());
                System.out.println();
            }
        }

        ConsoleView.prompt("Enter supplier id number (press Enter to view all)");
        String input = scanner.nextLine().trim();
        Long supplierId = input.isEmpty() ? null : IdFormatter.parseIdNumber(input);

        List<StockTransaction> transactions;
        if (supplierId == null) {
            transactions = TrackNestDB.getInstance().getStockTransactions();
        } else {
            if (TrackNestDB.getInstance().getSupplierById(supplierId) == null) {
                ConsoleView.error("Supplier not found.");
                return;
            }
            // Print full supplier product list before showing transactions
            printSupplierProducts(supplierId);
            transactions = stockModel.getSupplierTransactions(supplierId);
        }
        if (transactions.isEmpty()) {
            System.out.println("No supplier transactions found.");
            return;
        }

        System.out.printf("%-12s | %-24s | %-24s | %-8s | %-18s | %-9s%n",
                "Supplier", "Supplier Name", "Product Name", "Quantity", "Delivered Date", "Transaction");
        for (StockTransaction transaction : transactions) {
            String supplierCode = transaction.getSupplierId() == null ? "N/A" : IdFormatter.supplierId(transaction.getSupplierId());
            String supplierName = getSupplierName(transaction.getSupplierId());
            String productName = getProductName(transaction.getProductId());
            String deliveredDate = formatDate(transaction.getDeliveredDate());
            String status = transaction.getTransactionType() == StockTransaction.TransactionType.STOCK_OUT
                    ? "STOCK OUT"
                    : "STOCK IN";
            System.out.printf("%-12s | %-24s | %-24s | %-8s | %-18s | %-9s%n",
                    supplierCode,
                    supplierName,
                    productName,
                    transaction.getQuantity() == null ? "0" : transaction.getQuantity(),
                    deliveredDate,
                    status);
        }
        // After listing transactions, ask whether user wants to view supplier by id or press Enter
        if (supplierId == null) {
            ConsoleView.prompt("Press Enter to continue or enter a supplier id to view its products");
            String postInput = scanner.nextLine().trim();
            if (!postInput.isEmpty()) {
                Long id = IdFormatter.parseIdNumber(postInput);
                if (id == null || TrackNestDB.getInstance().getSupplierById(id) == null) {
                    ConsoleView.error("Supplier not found.");
                } else {
                    printSupplierProducts(id);
                }
            }
        }
    }

    private void printSupplierProducts(Long supplierId) {
        if (supplierId == null) return;
        List<Product> products = TrackNestDB.getInstance().getProducts();
        ConsoleView.title("Products for Supplier " + IdFormatter.supplierId(supplierId));
        boolean found = false;
        for (Product product : products) {
            // If supplier association exists via stock transactions, show products delivered by this supplier
            List<StockTransaction> txs = TrackNestDB.getInstance().getStockTransactionsBySupplierId(supplierId);
            for (StockTransaction tx : txs) {
                if (product.getProductId() != null && product.getProductId().equals(tx.getProductId())) {
                    System.out.println(IdFormatter.productId(product.getProductId()) + " | " + product.getProductName());
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("No products found for this supplier.");
        }
    }

    private String getProductName(Long productId) {
        Product product = TrackNestDB.getInstance().getProductById(productId);
        return product == null ? "Unknown product" : product.getProductName();
    }

    private String getSupplierName(Long supplierId) {
        if (supplierId == null) return "N/A";
        return TrackNestDB.getInstance().getSupplierById(supplierId) == null
                ? "Unknown supplier"
                : TrackNestDB.getInstance().getSupplierById(supplierId).getSupplierName();
    }

    private Long readProductIdByName() {
        String productName = scanner.nextLine().trim();
        if (productName.isEmpty()) return null;
        Product product = TrackNestDB.getInstance().getProductByName(productName);
        return product == null ? null : product.getProductId();
    }

    private String formatDate(Long timestamp) {
        if (timestamp == null) return "N/A";
        return DATE_FORMAT.format(new Date(timestamp));
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
}
