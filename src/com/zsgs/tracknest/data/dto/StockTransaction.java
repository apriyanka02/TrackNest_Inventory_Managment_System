package com.zsgs.tracknest.data.dto;

public class StockTransaction {

    private Long transactionId;
    private Long productId;
    private Long supplierId;
    private Long quantity;
    private Long price;
    private TransactionType transactionType;
    private Long deliveredDate;

    public enum TransactionType {
        STOCK_IN, STOCK_OUT
    }

    public StockTransaction() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Long deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}
