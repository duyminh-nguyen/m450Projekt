package ch.tbz.minishop.service;

import ch.tbz.minishop.domain.Product;
import ch.tbz.minishop.ports.SupplierGateway;

public class RestockService {
    private final SupplierGateway supplier;

    public RestockService(SupplierGateway supplier) {
        this.supplier = supplier;
    }

    public void checkAndRestock(Product product, int threshold, int restockTo) {
        if (product.stock() < threshold) {
            int amount = restockTo - product.stock();
            if (amount > 0) supplier.orderUnits(product.sku(), amount);
        }
    }
}
