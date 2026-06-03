package com.zsgs.tracknest.features.order;

import com.zsgs.tracknest.data.dto.Order;
import com.zsgs.tracknest.data.dto.OrderedItem;
import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.data.repository.TrackNestDB;
import com.zsgs.tracknest.util.ValidationUtil;

import java.util.List;

class OrderModel {

    Product getProduct(String productName) {
        return TrackNestDB.getInstance().getProductByName(productName);
    }

    Order createOrder(Long userId, String productName, int quantity) {
        if (!ValidationUtil.isValidProductName(productName) || quantity <= 0) return null;
        Product product = getProduct(productName);
        if (product == null || product.getPrice() == null || !hasEnoughStock(product, quantity)) return null;

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setStatus(Order.OrderStatus.RECEIVED);
        Order savedOrder = TrackNestDB.getInstance().addOrder(order);
        if (savedOrder == null) return null;

        OrderedItem item = new OrderedItem();
        item.setOrderId(savedOrder.getOrderId());
        item.setProductId(product.getProductId());
        item.setQuantity(quantity);
        TrackNestDB.getInstance().addOrderedItem(item);

        product.setQuantity(product.getQuantity() - quantity);
        TrackNestDB.getInstance().updateProduct(product);
        return savedOrder;
    }

    List<Order> getOrders(Long userId, boolean allOrders) {
        if (allOrders) return TrackNestDB.getInstance().getOrders();
        return TrackNestDB.getInstance().getOrdersByUser(userId);
    }

    User getUser(Long userId) {
        return TrackNestDB.getInstance().getUserById(userId);
    }

    List<Product> getProducts() {
        return TrackNestDB.getInstance().getProducts();
    }

    Product getProductById(Long productId) {
        return TrackNestDB.getInstance().getProductById(productId);
    }

    List<OrderedItem> getOrderItems(Long orderId) {
        return TrackNestDB.getInstance().getOrderedItems(orderId);
    }

    private boolean hasEnoughStock(Product product, int quantity) {
        return product.getQuantity() != null && product.getQuantity() >= quantity;
    }
}
