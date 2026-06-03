package com.zsgs.tracknest.data.dto;

public class OrderedItem {

    private Long orderedItemId;
    private Long orderId;
    private Long productId;
    private int quantity;

    public OrderedItem() {
    }

    public Long getOrderedItemId() {
        return orderedItemId;
    }

    public void setOrderedItemId(Long orderedItemId) {
        this.orderedItemId = orderedItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
