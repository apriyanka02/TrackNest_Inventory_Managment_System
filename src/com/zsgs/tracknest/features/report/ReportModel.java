package com.zsgs.tracknest.features.report;

import com.zsgs.tracknest.data.dto.Order;
import com.zsgs.tracknest.data.dto.OrderedItem;
import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.dto.StockTransaction;
import com.zsgs.tracknest.data.dto.Supplier;
import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.data.repository.TrackNestDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReportModel {

    private static final long LOW_STOCK_THRESHOLD = 5L;

    int getProductCount() {
        return TrackNestDB.getInstance().getProducts().size();
    }

    int getOrderCount() {
        return TrackNestDB.getInstance().getOrders().size();
    }

    int getSupplierCount() {
        return TrackNestDB.getInstance().getSuppliers().size();
    }

    List<Order> getOrders() {
        return TrackNestDB.getInstance().getOrders();
    }

    List<OrderedItem> getOrderItems(Long orderId) {
        return TrackNestDB.getInstance().getOrderedItems(orderId);
    }

    User getUser(Long userId) {
        return TrackNestDB.getInstance().getUserById(userId);
    }

    Product getProductById(Long productId) {
        return TrackNestDB.getInstance().getProductById(productId);
    }

    List<Product> getLowStockProducts() {
        List<Product> lowStock = new ArrayList<>();
        for (Product product : TrackNestDB.getInstance().getProducts()) {
            Long quantity = product.getQuantity();
            if (quantity == null || quantity <= LOW_STOCK_THRESHOLD) {
                lowStock.add(product);
            }
        }
        return lowStock;
    }

    TopProductInfo getTopPurchasedProductInfo() {
        List<OrderedItem> orderedItems = TrackNestDB.getInstance().getOrderedItems();
        if (orderedItems.isEmpty()) {
            return null;
        }

        Map<Long, Long> productQuantities = new HashMap<>();
        for (OrderedItem item : orderedItems) {
            if (item.getProductId() == null) continue;
            productQuantities.merge(item.getProductId(), (long) item.getQuantity(), Long::sum);
        }

        Long bestProductId = null;
        long maxQuantity = 0L;
        for (Map.Entry<Long, Long> entry : productQuantities.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                bestProductId = entry.getKey();
            }
        }

        if (bestProductId == null) {
            return null;
        }

        Product product = TrackNestDB.getInstance().getProductById(bestProductId);
        Supplier supplier = findSupplierForProduct(bestProductId);
        return new TopProductInfo(product, maxQuantity, supplier);
    }

    private Supplier findSupplierForProduct(Long productId) {
        List<StockTransaction> transactions = TrackNestDB.getInstance().getStockTransactions();
        StockTransaction latest = null;
        for (StockTransaction transaction : transactions) {
            if (productId.equals(transaction.getProductId())
                    && transaction.getTransactionType() == StockTransaction.TransactionType.STOCK_IN
                    && transaction.getDeliveredDate() != null) {
                if (latest == null || transaction.getDeliveredDate() > latest.getDeliveredDate()) {
                    latest = transaction;
                }
            }
        }
        if (latest == null || latest.getSupplierId() == null) {
            return null;
        }
        return TrackNestDB.getInstance().getSupplierById(latest.getSupplierId());
    }

    static class TopProductInfo {
        final Product product;
        final long totalQuantity;
        final Supplier supplier;

        TopProductInfo(Product product, long totalQuantity, Supplier supplier) {
            this.product = product;
            this.totalQuantity = totalQuantity;
            this.supplier = supplier;
        }
    }
}
