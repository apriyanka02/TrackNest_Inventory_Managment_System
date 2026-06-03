package com.zsgs.tracknest.features.product.update;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.repository.TrackNestDB;

class ProductUpdateModel {

    Product getProduct(Long productId) {
        return TrackNestDB.getInstance().getProductById(productId);
    }

    Product updateProduct(Product product) {
        return TrackNestDB.getInstance().updateProduct(product);
    }

    boolean deleteProduct(Long productId) {
        return TrackNestDB.getInstance().deleteProduct(productId);
    }
}
