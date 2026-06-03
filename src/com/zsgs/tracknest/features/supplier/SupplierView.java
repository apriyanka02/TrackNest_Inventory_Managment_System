package com.zsgs.tracknest.features.supplier;

import com.zsgs.tracknest.data.dto.Supplier;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;

import java.util.List;
import java.util.Scanner;

public class SupplierView {

    private final SupplierModel supplierModel;
    private final Scanner scanner;

    public SupplierView() {
        this.supplierModel = new SupplierModel();
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        ConsoleView.title("Supplier Management");
        ConsoleView.option("1", "Add supplier");
        ConsoleView.option("2", "View all suppliers");
        ConsoleView.option("3", "View supplier");
        ConsoleView.prompt("Choose an option");
        switch (scanner.nextLine().trim()) {
            case "1":
                addSupplier();
                break;
            case "3":
                showSupplier();
                break;
            default:
                showSuppliers();
                break;
        }
    }

    public void addSupplier() {
        ConsoleView.title("Add Supplier");
        Supplier supplier = new Supplier();
        ConsoleView.prompt("Enter supplier name");
        supplier.setSupplierName(scanner.nextLine().trim());
        ConsoleView.prompt("Enter address");
        supplier.setAddress(scanner.nextLine().trim());
        Supplier saved = supplierModel.addSupplier(supplier);
        if (saved == null) {
            ConsoleView.error("Supplier name/address is invalid.");
        } else {
            ConsoleView.success("Supplier added with id: " + IdFormatter.supplierId(saved.getSupplierId()));
        }
    }

    public void showSuppliers() {
        List<Supplier> suppliers = supplierModel.getSuppliers();
        printLine("All Suppliers");
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found.");
        } else {
            for (Supplier supplier : suppliers) {
                printSupplier(supplier);
            }
        }
        printLine("End of Suppliers");
    }

    public void showSupplier() {
        ConsoleView.prompt("Enter supplier id number");
        Supplier supplier = supplierModel.getSupplier(readLong());
        printLine("Supplier Details");
        if (supplier == null) {
            System.out.println("Supplier not found.");
        } else {
            printSupplier(supplier);
        }
        printLine("End of Supplier Details");
    }

    private Long readLong() {
        return IdFormatter.parseIdNumber(scanner.nextLine());
    }

    private void printSupplier(Supplier supplier) {
        System.out.println(IdFormatter.supplierId(supplier.getSupplierId()) + " | " + supplier.getSupplierName()
                + " | " + supplier.getAddress());
    }

    private void printLine(String title) {
        ConsoleView.title(title);
    }
}
