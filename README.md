# TrackNest - Inventory Management System
TrackNest is a Java-based Inventory Management System developed to streamline inventory operations, supplier management, stock tracking, customer orders, and profit analysis. The system follows a role-based architecture where Administrators manage inventory and suppliers, while Customers can browse products and place orders.

The primary objective of TrackNest is to provide a centralized solution for maintaining inventory records, monitoring stock movements, tracking product expiry dates, and managing customer orders efficiently.

---

##  Features

### 🔐 Authentication & Authorization
- Secure Login System
- Role-Based Access Control
  - Admin
  - Customer

---

### 📦 Product Management
- Add New Product
- View Product Details
- Update Product Information
- Delete Product
- Search Products
- Product Availability Management
- Product Pricing Management
- Manufactured Date Tracking
- Expiry Date Tracking

---

### 📊 Inventory Management
- Stock IN Operations
- Stock OUT Operations
- Automatic Stock Quantity Updates
- Stock Transaction Tracking
- Product Availability Monitoring

---

### 🏢 Supplier Management
- Add Suppliers
- Update Supplier Information
- View Supplier Details
- Supplier Stock Tracking

---

### 🛒 Order Management
- Create Customer Orders
- View Order History
- Order Status Tracking

Order Status Flow:

```text
PLACED → CONFIRMED → DELIVERED
           │
           └── CANCELLED
```

- Automatic Order Total Calculation
- Product-wise Order Tracking

---

### 💰 Profit & Expense Analysis
- Product Profit Calculation
- Expense Tracking
- Profit Reports
- Sales Monitoring

---

## 🏗️ System Architecture

The project follows a layered architecture:

```text
Presentation Layer
        │
        ▼
Service Layer
        │
        ▼
DAO Layer
        │
        ▼
Database Layer
```

---

## 👥 User Roles

### Admin

Admin has complete control over the system.

#### Responsibilities
- Manage Products
- Manage Suppliers
- Manage Inventory
- Manage Pricing
- Manage Expiry Information
- View Customers
- View Orders
- View Profit Reports

---

### Customer

Customers can interact with the inventory through ordering features.

#### Responsibilities
- Create Account
- Login
- Browse Products
- Search Products
- Place Orders
- View Order History

---

## 📂 Project Structure

```text
TrackNest
│
├── controller
│   ├── AdminController
│   └── CustomerController
│
├── service
│   ├── ProductService
│   ├── SupplierService
│   ├── OrderService
│   └── StockService
│
├── dao
│   ├── ProductDAO
│   ├── SupplierDAO
│   ├── CustomerDAO
│   ├── OrderDAO
│   └── StockTransactionDAO
│
├── model
│   ├── Customer
│   ├── Product
│   ├── Supplier
│   ├── Order
│   ├── OrderedItem
│   └── StockTransaction
│
├── util
│   ├── DBConnection
│   ├── ValidationUtil
│   └── DateUtil
│
└── Main.java
```

## 🔄 Workflow

### Product Flow

```text
Add Product
     │
     ▼
Store Product
     │
     ▼
Manage Stock
     │
     ▼
Update Availability
```

---

### Order Flow

```text
Customer Login
      │
      ▼
Browse Products
      │
      ▼
Place Order
      │
      ▼
Order Created
      │
      ▼
Order Confirmed
      │
      ▼
Delivered / Cancelled
```

---

### Inventory Flow

```text
Supplier
    │
    ▼
Stock IN
    │
    ▼
Inventory Updated
    │
    ▼
Customer Order
    │
    ▼
Stock OUT
    │
    ▼
Inventory Updated
```

---

## 🎯 Key Functionalities

### Inventory Tracking
- Real-time stock quantity updates
- Stock movement monitoring
- Availability management

### Supplier Management
- Supplier registration
- Supplier-product tracking
- Supplier transaction history

### Order Processing
- Customer ordering system
- Order status tracking
- Order history management

### Profit Calculation
- Product-wise profit tracking
- Expense monitoring
- Revenue analysis

---
