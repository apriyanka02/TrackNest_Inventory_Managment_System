package com.zsgs.tracknest.features.product.add;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;

import java.util.Scanner;

public class ProductAddView {

    private final ProductAddModel productAddModel;
    private final Scanner scanner;

    public ProductAddView() {
        this.productAddModel = new ProductAddModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        ConsoleView.title("Add Product");
        Product product = new Product();
        ConsoleView.prompt("Enter product name");
        product.setProductName(scanner.nextLine().trim());
        ConsoleView.prompt("Enter price");
        product.setPrice(readLong());
        ConsoleView.prompt("Enter quantity");
        product.setQuantity(readLong());
        productAddModel.addProduct(product);
    }

    private Long readLong() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    void onProductAdded(Product product) {
        ConsoleView.success("Product added with id: " + IdFormatter.productId(product.getProductId()));
    }

    void onProductAddFailed(String message) {
        ConsoleView.error(message);
    }
}
