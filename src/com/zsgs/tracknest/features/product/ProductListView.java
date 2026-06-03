package com.zsgs.tracknest.features.product;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;

import java.util.List;
import java.util.Scanner;

public class ProductListView {

    private final ProductListModel productListModel;
    private final Scanner scanner;

    public ProductListView() {
        this.productListModel = new ProductListModel();
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        ConsoleView.title("Products");
        ConsoleView.prompt("Search product name (press Enter for all)");
        List<Product> products = productListModel.searchProducts(scanner.nextLine());
        if (products.isEmpty()) {
            ConsoleView.error("No products found.");
            return;
        }
        for (Product product : products) {
            System.out.println(IdFormatter.productId(product.getProductId()) + " | " + product.getProductName()
                    + " | Qty: " + product.getQuantity()
                    + " | Price: " + product.getPrice()
                    + " | Status: " + product.getStatus());
        }
    }
}
