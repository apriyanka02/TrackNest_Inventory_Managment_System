package com.zsgs.tracknest.features.product.add;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.repository.TrackNestDB;
import com.zsgs.tracknest.util.ValidationUtil;

class ProductAddModel {

    private final ProductAddView productAddView;

    ProductAddModel(ProductAddView productAddView) {
        this.productAddView = productAddView;
    }

    void addProduct(Product product) {
        if (product == null || !ValidationUtil.isValidProductName(product.getProductName())) {
            productAddView.onProductAddFailed("Product name must be 2-50 letters/numbers.");
            return;
        }
        if (!ValidationUtil.isPositive(product.getPrice())) {
            productAddView.onProductAddFailed("Price must be greater than 0.");
            return;
        }
        if (!ValidationUtil.isZeroOrPositive(product.getQuantity())) {
            productAddView.onProductAddFailed("Quantity cannot be negative.");
            return;
        }
        if (TrackNestDB.getInstance().getProductByName(product.getProductName()) != null) {
            productAddView.onProductAddFailed("This product name already exists.");
            return;
        }
        product.setProductName(product.getProductName().trim());
        Product saved = TrackNestDB.getInstance().addProduct(product);
        if (saved == null) {
            productAddView.onProductAddFailed("Could not add product.");
            return;
        }
        productAddView.onProductAdded(saved);
    }
}
