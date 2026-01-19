package ch.tbz.minishop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void createProduct_setsInitialStock() {
        Product p = new Product("SKU-1", 1000, 5);
        assertEquals(5, p.stock());
    }
}