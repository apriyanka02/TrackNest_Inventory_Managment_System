package com.zsgs.tracknest.data.dto;

public class Product {

    private Long productId;
    private String productName;
    private ProductStatus status;
    private Long price;
    private Long quantity;
    private Long expiryDate;
    private Long manufacturedDate;

    public enum ProductStatus {
        AVAILABLE, OUT_OF_STOCK
    }

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(Long manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }
}
