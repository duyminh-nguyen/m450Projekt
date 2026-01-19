package ch.tbz.minishop.ports;

public interface SupplierGateway {
    void orderUnits(String sku, int quantity);
}
