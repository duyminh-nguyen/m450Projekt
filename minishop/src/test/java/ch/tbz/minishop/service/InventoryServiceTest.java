package ch.tbz.minishop.service;

import ch.tbz.minishop.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryServiceTest {

    @Test
    void placeOrder_reducesStock() {
        InventoryService service = new InventoryService();
        service.addProduct(new Product("SKU-1", 1000, 5));

        service.placeOrder("ORDER-1", "SKU-1", 2);

        assertEquals(3, service.getProduct("SKU-1").stock());
    }

    @Test
    void placeOrder_throwsIfInsufficientStock() {
        InventoryService service = new InventoryService();
        service.addProduct(new Product("SKU-1", 1000, 1));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                () -> service.placeOrder("ORDER-1", "SKU-1", 2));
    }

    @Test
    void cancelOrder_restoresStock() {
        InventoryService service = new InventoryService();
        service.addProduct(new Product("SKU-1", 1000, 5));

        service.placeOrder("ORDER-1", "SKU-1", 2);
        service.cancelOrder("ORDER-1");

        assertEquals(5, service.getProduct("SKU-1").stock());
    }
}
