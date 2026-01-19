package ch.tbz.minishop.service;

import ch.tbz.minishop.domain.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class InventoryService {
    private final Map<String, Product> products = new HashMap<>();

    private final Map<String, OrderLine> orders = new HashMap<>();

    private record OrderLine(String sku, int quantity) {}

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
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        if (newStock < 0) throw new IllegalStateException("Insufficient stock");
        orders.put(orderId, new OrderLine(sku, quantity));
    }
    public void cancelOrder(String orderId) {
        OrderLine line = orders.remove(orderId);
        if (line == null) throw new NoSuchElementException("Unknown order: " + orderId);

        Product p = getProduct(line.sku());
        int newStock = p.stock() + line.quantity();
        products.put(p.sku(), new Product(p.sku(), p.priceCents(), newStock));
    }

}
