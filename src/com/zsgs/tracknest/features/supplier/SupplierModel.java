package com.zsgs.tracknest.features.supplier;

import com.zsgs.tracknest.data.dto.Supplier;
import com.zsgs.tracknest.data.repository.TrackNestDB;
import com.zsgs.tracknest.util.ValidationUtil;

import java.util.List;

class SupplierModel {

    Supplier addSupplier(Supplier supplier) {
        if (supplier == null
                || !ValidationUtil.isValidName(supplier.getSupplierName())
                || !ValidationUtil.isValidAddress(supplier.getAddress())) {
            return null;
        }
        supplier.setSupplierName(supplier.getSupplierName().trim());
        supplier.setAddress(supplier.getAddress().trim());
        return TrackNestDB.getInstance().addSupplier(supplier);
    }

    List<Supplier> getSuppliers() {
        return TrackNestDB.getInstance().getSuppliers();
    }

    Supplier getSupplier(Long supplierId) {
        return TrackNestDB.getInstance().getSupplierById(supplierId);
    }
}
