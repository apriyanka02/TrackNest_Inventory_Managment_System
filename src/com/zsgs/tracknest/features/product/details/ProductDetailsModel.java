package com.zsgs.tracknest.features.product.details;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.repository.TrackNestDB;

class ProductDetailsModel {

    Product getProduct(Long productId) {
        return TrackNestDB.getInstance().getProductById(productId);
    }
}
