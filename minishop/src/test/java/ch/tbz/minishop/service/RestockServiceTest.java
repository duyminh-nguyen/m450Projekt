package ch.tbz.minishop.service;

import ch.tbz.minishop.domain.Product;
import ch.tbz.minishop.ports.SupplierGateway;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RestockServiceTest {

    @Test
    void checkAndRestock_callsSupplierWhenBelowThreshold() {
        SupplierGateway supplier = mock(SupplierGateway.class);
        RestockService service = new RestockService(supplier);

        Product p = new Product("SKU-1", 1000, 2);

        service.checkAndRestock(p, 3, 10);

        verify(supplier).orderUnits("SKU-1", 8);
    }

    @Test
    void checkAndRestock_doesNotCallSupplierWhenAboveOrEqualThreshold() {
        SupplierGateway supplier = mock(SupplierGateway.class);
        RestockService service = new RestockService(supplier);

        Product p = new Product("SKU-1", 1000, 3);

        service.checkAndRestock(p, 3, 10);

        verifyNoInteractions(supplier);
    }
}