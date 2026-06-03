package com.zsgs.tracknest.features.stock;

import com.zsgs.tracknest.data.dto.StockTransaction;
import com.zsgs.tracknest.data.repository.TrackNestDB;

import java.util.List;

class StockModel {

    private final StockView stockView;

    StockModel(StockView stockView) {
        this.stockView = stockView;
    }

    void recordTransaction(StockTransaction transaction) {
        StockTransaction saved = TrackNestDB.getInstance().addStockTransaction(transaction);
        if (saved == null) {
            stockView.onStockTransactionFailed("Could not record stock transaction.");
            return;
        }
        stockView.onStockTransactionRecorded(saved);
    }

    List<StockTransaction> getSupplierTransactions(Long supplierId) {
        return TrackNestDB.getInstance().getStockTransactionsBySupplierId(supplierId);
    }
}
