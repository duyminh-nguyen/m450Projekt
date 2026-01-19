package ch.tbz.minishop.service;

import ch.tbz.minishop.domain.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class InventoryService {
    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.sku(), product);
    }

    public Product getProduct(String sku) {
        Product p = products.get(sku);
        if (p == null) throw new NoSuchElementException("Unknown SKU: " + sku);
        return p;
    }

    public void placeOrder(String orderId, String sku, int quantity) {
        Product p = getProduct(sku);
        int newStock = p.stock() - quantity;
        products.put(sku, new Product(p.sku(), p.priceCents(), newStock));
    }
}
