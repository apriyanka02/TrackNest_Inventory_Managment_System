package com.zsgs.tracknest.data.repository;

import com.zsgs.tracknest.data.dto.Order;
import com.zsgs.tracknest.data.dto.OrderedItem;
import com.zsgs.tracknest.data.dto.Product;
import com.zsgs.tracknest.data.dto.StockTransaction;
import com.zsgs.tracknest.data.dto.Supplier;
import com.zsgs.tracknest.data.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrackNestDB {

    private static TrackNestDB trackNestDB = null;

    private final List<Product> products = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Supplier> suppliers = new ArrayList<>();
    private final List<StockTransaction> stockTransactions = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<OrderedItem> orderedItems = new ArrayList<>();

    private long productPk = 0L;
    private long userPk = 0L;
    private long supplierPk = 0L;
    private long stockTransactionPk = 0L;
    private long orderPk = 0L;
    private long orderedItemPk = 0L;

    private TrackNestDB() {
        seedAdmin();
    }

    public static final TrackNestDB getInstance() {
        if (trackNestDB == null) {
            trackNestDB = new TrackNestDB();
        }
        return trackNestDB;
    }

    private void seedAdmin() {
        User admin = new User();
        admin.setUserName("Admin");
        admin.setEmail("admin@tracknest.com");
        admin.setPassword("admin");
        admin.setRole(User.Role.ADMIN);
        addUser(admin);
    }

    public User addUser(User user) {
        if (user == null || isBlank(user.getEmail())) return null;
        userPk++;
        user.setUserId(userPk);
        if (user.getRole() == null) user.setRole(User.Role.USER);
        users.add(user);
        return user;
    }

    public User authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null) return null;
        if (password == null || !password.equals(user.getPassword())) return null;
        return user;
    }

    public User getUserByEmail(String email) {
        if (isBlank(email)) return null;
        String key = email.trim().toLowerCase(Locale.ROOT);
        for (User current : users) {
            if (current.getEmail() != null
                    && current.getEmail().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return current;
            }
        }
        return null;
    }

    public User getUserById(Long userId) {
        if (userId == null) return null;
        for (User current : users) {
            if (userId.equals(current.getUserId())) return current;
        }
        return null;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public Product addProduct(Product product) {
        if (product == null || isBlank(product.getProductName())) return null;
        productPk++;
        product.setProductId(productPk);
        if (product.getQuantity() == null) product.setQuantity(0L);
        if (product.getStatus() == null) updateAvailability(product);
        products.add(product);
        return product;
    }

    public Product updateProduct(Product product) {
        if (product == null || product.getProductId() == null) return null;
        for (int i = 0; i < products.size(); i++) {
            if (product.getProductId().equals(products.get(i).getProductId())) {
                updateAvailability(product);
                products.set(i, product);
                return product;
            }
        }
        return null;
    }

    public boolean deleteProduct(Long productId) {
        Product product = getProductById(productId);
        return product != null && products.remove(product);
    }

    public Product getProductById(Long productId) {
        if (productId == null) return null;
        for (Product current : products) {
            if (productId.equals(current.getProductId())) return current;
        }
        return null;
    }

    public Product getProductByName(String productName) {
        if (isBlank(productName)) return null;
        String key = productName.trim().toLowerCase(Locale.ROOT);
        for (Product current : products) {
            if (current.getProductName() != null
                    && current.getProductName().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return current;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();
        String key = keyword == null ? "" : keyword.trim().toLowerCase(Locale.ROOT);
        for (Product current : products) {
            if (key.isEmpty()
                    || current.getProductName().toLowerCase(Locale.ROOT).contains(key)) {
                result.add(current);
            }
        }
        return result;
    }

    public Supplier addSupplier(Supplier supplier) {
        if (supplier == null || isBlank(supplier.getSupplierName())) return null;
        supplierPk++;
        supplier.setSupplierId(supplierPk);
        suppliers.add(supplier);
        return supplier;
    }

    public List<Supplier> getSuppliers() {
        return new ArrayList<>(suppliers);
    }

    public Supplier getSupplierById(Long supplierId) {
        if (supplierId == null) return null;
        for (Supplier current : suppliers) {
            if (supplierId.equals(current.getSupplierId())) return current;
        }
        return null;
    }

    public StockTransaction addStockTransaction(StockTransaction transaction) {
        if (transaction == null || transaction.getProductId() == null) return null;
        stockTransactionPk++;
        transaction.setTransactionId(stockTransactionPk);
        if (transaction.getDeliveredDate() == null) {
            transaction.setDeliveredDate(System.currentTimeMillis());
        }
        stockTransactions.add(transaction);
        applyStockChange(transaction);
        return transaction;
    }

    public List<StockTransaction> getStockTransactions() {
        return new ArrayList<>(stockTransactions);
    }

    public List<StockTransaction> getStockTransactionsBySupplierId(Long supplierId) {
        List<StockTransaction> result = new ArrayList<>();
        if (supplierId == null) return result;
        for (StockTransaction current : stockTransactions) {
            if (supplierId.equals(current.getSupplierId())) {
                result.add(current);
            }
        }
        return result;
    }

    public Order addOrder(Order order) {
        if (order == null || order.getUserId() == null) return null;
        orderPk++;
        order.setOrderId(orderPk);
        if (order.getStatus() == null) order.setStatus(Order.OrderStatus.PENDING);
        if (order.getCreatedDate() == null) order.setCreatedDate(System.currentTimeMillis());
        orders.add(order);
        return order;
    }

    public OrderedItem addOrderedItem(OrderedItem orderedItem) {
        if (orderedItem == null || orderedItem.getOrderId() == null || orderedItem.getProductId() == null) {
            return null;
        }
        orderedItemPk++;
        orderedItem.setOrderedItemId(orderedItemPk);
        orderedItems.add(orderedItem);
        return orderedItem;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public List<Order> getOrdersByUser(Long userId) {
        List<Order> result = new ArrayList<>();
        if (userId == null) return result;
        for (Order current : orders) {
            if (userId.equals(current.getUserId())) result.add(current);
        }
        return result;
    }

    public List<OrderedItem> getOrderedItems(Long orderId) {
        List<OrderedItem> result = new ArrayList<>();
        if (orderId == null) return result;
        for (OrderedItem current : orderedItems) {
            if (orderId.equals(current.getOrderId())) result.add(current);
        }
        return result;
    }

    public List<OrderedItem> getOrderedItems() {
        return new ArrayList<>(orderedItems);
    }

    private void applyStockChange(StockTransaction transaction) {
        Product product = getProductById(transaction.getProductId());
        if (product == null || transaction.getQuantity() == null) return;
        long quantity = product.getQuantity() == null ? 0L : product.getQuantity();
        if (transaction.getTransactionType() == StockTransaction.TransactionType.STOCK_OUT) {
            product.setQuantity(Math.max(0L, quantity - transaction.getQuantity()));
        } else {
            product.setQuantity(quantity + transaction.getQuantity());
        }
        updateAvailability(product);
    }

    private void updateAvailability(Product product) {
        long quantity = product.getQuantity() == null ? 0L : product.getQuantity();
        product.setStatus(quantity > 0L ? Product.ProductStatus.AVAILABLE : Product.ProductStatus.OUT_OF_STOCK);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
