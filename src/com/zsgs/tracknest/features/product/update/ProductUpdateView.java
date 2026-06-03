package com.zsgs.tracknest.features.product.update;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;
import com.zsgs.tracknest.util.ValidationUtil;

import java.util.Scanner;

public class ProductUpdateView {

    private final ProductUpdateModel productUpdateModel;
    private final Scanner scanner;

    public ProductUpdateView() {
        this.productUpdateModel = new ProductUpdateModel();
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        ConsoleView.title("Update Product");
        ConsoleView.prompt("Enter product id number");
        Long productId = readId();
        Product product = productUpdateModel.getProduct(productId);
        if (product == null) {
            ConsoleView.error("Product not found.");
            return;
        }
        System.out.println(IdFormatter.productId(product.getProductId()) + " | " + product.getProductName());
        ConsoleView.option("1", "Update price");
        ConsoleView.option("2", "Update quantity");
        ConsoleView.option("3", "Delete product");
        ConsoleView.prompt("Choose an option");
        String choice = scanner.nextLine().trim();
        if ("1".equals(choice)) {
            ConsoleView.prompt("Enter new price");
            Long price = readLong();
            if (!ValidationUtil.isPositive(price)) {
                ConsoleView.error("Price must be greater than 0.");
                return;
            }
            product.setPrice(price);
            productUpdateModel.updateProduct(product);
            ConsoleView.success("Product price updated.");
        } else if ("2".equals(choice)) {
            ConsoleView.prompt("Enter new quantity");
            Long quantity = readLong();
            if (!ValidationUtil.isZeroOrPositive(quantity)) {
                ConsoleView.error("Quantity cannot be negative.");
                return;
            }
            product.setQuantity(quantity);
            productUpdateModel.updateProduct(product);
            ConsoleView.success("Product quantity updated.");
        } else if ("3".equals(choice)) {
            if (productUpdateModel.deleteProduct(productId)) {
                ConsoleView.success("Product deleted.");
            } else {
                ConsoleView.error("Could not delete product.");
            }
        } else {
            ConsoleView.error("Invalid option.");
        }
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
}
