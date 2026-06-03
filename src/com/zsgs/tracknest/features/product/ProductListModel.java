package com.zsgs.tracknest.features.product;

import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.repository.TrackNestDB;

import java.util.List;

class ProductListModel {

    List<Product> searchProducts(String keyword) {
        return TrackNestDB.getInstance().searchProducts(keyword);
    }
}
